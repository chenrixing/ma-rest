package com.ma.rest.controller;

import com.ma.common.utils.*;
import com.ma.rest.dao.JedisClient;
import com.ma.rest.pojo.MaUserInfo;
import com.ma.rest.service.UserInfoService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录业务处理类
 */
@RestController
@RequestMapping(path = "/login", produces = "text/plain;charset=UTF-8")
public class LoginController extends BaseController {

    @Resource
    private JedisClient jedis;
    @Resource
    private UserInfoService userInfoService;

    private static Logger logger = Logger.getLogger(LoginController.class);

    @Value("${MA_FILE_BASE_URL}")
    private String MA_FILE_BASE_URL;

    /**
     * 获取注册验证码
     *
     * @param phone
     * @return WebJsonResult
     */
    @ApiOperation(value="获取用户注册验证码")
    @RequestMapping(value = "/getVcode", method = RequestMethod.POST)
    public String getVcode(HttpServletResponse respons,
                           @ApiParam(value = "手机号", required = true) @RequestParam(value = "phone", required = true) String phone) {
        // 参数解密
        try {

            if (StringUtils.isBlank(phone)) {
                logger.info("解密失败");
                return JsonUtils.objectToJson(error("非法请求"));
            }
            // 验证用户是否已经注册
            MaUserInfo userInfo = userInfoService.queryByPhone(phone);
            if (userInfo != null) {
                return JsonUtils.objectToJson(error(Constants.RESULT_CODE_1401, "手机号码已被注册"));
            }
            // 验证用户验证码
            String vCodeTime = jedis.get(Constants.REGISTER_VCODE_TIME_KEY + phone);
            if (StringUtils.isBlank(vCodeTime)) {
                String vCode = AlibabaMessage.createRandomVcode();
                // 发送验证码
                AlibabaMessage.sendSms(phone, vCode, Constants.SMS_REG);

                jedis.set(Constants.REGISTER_VCODE_TIME_KEY + phone, Constants.VCODE_EXPIRE);
                jedis.expire(Constants.REGISTER_VCODE_TIME_KEY + phone, Constants.VCODE_REQUEST_EXPIRE);
                jedis.set(Constants.REGISTER_KEY + phone, vCode);
                jedis.expire(Constants.REGISTER_KEY + phone, Constants.VCODE_EXPIRE_TIME);
                WebJsonResult result = success();
                logger.info("send registerCode success;");
                return JsonUtils.objectToJson(result);
            } else {
                return JsonUtils.objectToJson(error("非法请求"));
            }
        } catch (Exception e) {
            logger.info("getVcode fail;message:" + e.getMessage());
            return JsonUtils.objectToJson(error("非法请求"));
        }
    }

    /**
     * 用户登录
     * @param phone,password
     * @return WebJsonResult
     */
    @ApiOperation(value="用户登录",notes="用户手机号AES加密")
    @RequestMapping(value = "/userInfoLogin", method = RequestMethod.POST)
    public String userInfoLogin(@RequestParam(value = "phone", required = true) String phone,
                            @RequestParam(value = "password", required = true) String password,
                            @RequestParam(name = "appVersion", required = false) String appVersion,
                                @ApiParam(value = "用户类型: 0 :普通用户 1:商家", required = false) @RequestParam(name = "type", required = false) String type
                                ) {
        // 对参数解密
        try {

            if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
                return JsonUtils.objectToJson(error("非法请求"));
            }

            MaUserInfo userInfoCustom=new MaUserInfo();
            userInfoCustom.setPhone(phone);
            userInfoCustom.setPassword(password);
            userInfoCustom.setType(type);
            userInfoCustom = userInfoService.loginUserInfo(userInfoCustom);

            if (userInfoCustom != null) {
                if (Constants.MA_UserInfo_STATUS_1 == userInfoCustom.getStatus()) {
                    return JsonUtils.objectToJson(error(Constants.RESULT_CODE_1409, "获取用户信息失败，请重试！"));
                }

                    setAppVersion(userInfoCustom, appVersion); // 更新appVersion
                    // 验证用户登录
                    if (userInfoCustom != null) {
                        WebJsonResult result = success();
                        result.setData(userInfoCustom);
                        logger.info("UserInfoLogin success;");
                        return JsonUtils.objectToJson(result);
                    } else {
                        logger.info("UserInfoLogin fail;");
                        return JsonUtils.objectToJson(error("获取用户信息失败，请重试！"));
                    }

            }else {
                logger.info("resetPassword fail！errorCode=" + Constants.RESULT_CODE_1404);
                return JsonUtils.objectToJson(error(Constants.RESULT_CODE_1404, "用户名或密码不正确，请重试！"));
            }

        } catch (Exception e) {
            logger.info("UserInfoLogin fail！message:" + e.getMessage());
            return JsonUtils.objectToJson(error("非法请求"));
        }
    }

    /**
     * 注册用户
     *
     * @param phone,password,vCode
     * @return WebJsonResult
     */
    @ApiOperation(value="用户注册",notes="用户手机号AES加密")
    @RequestMapping(value = "/registerUserInfo", method = RequestMethod.POST)
    public String registerUserInfo(@RequestParam(value = "phone", required = true) String phone,
                               @RequestParam(value = "password", required = true) String password,
                               @RequestParam(value = "vCode", required = true, defaultValue = "0") String vCode,
                               @ApiParam(value = "用户类型: 0 :普通用户 1:商家", required = false) @RequestParam(name = "type", required = false) String type,
                               @RequestParam(name = "appVersion", required = false) String appVersion ) {
        // 参数解密
        try {


            if(phone.startsWith("170")||phone.startsWith("171")){
                return JsonUtils.objectToJson(error("虚拟号段"));
            }

            // 获取用户注册码
            String json = jedis.get(Constants.REGISTER_KEY + phone);
            if (StringUtils.isBlank(json)) {
                return JsonUtils.objectToJson(error(Constants.RESULT_CODE_1400, "注册码失效"));
            }
            // 验证用户是否已经注册
            MaUserInfo UserInfo = userInfoService.queryByPhone(phone);
            if (UserInfo != null) {
                return JsonUtils.objectToJson(error(Constants.RESULT_CODE_1401, "手机号码已被注册"));
            }
            // 判断用户验证码是否正确
            if (vCode.equals(json)) {

                MaUserInfo userInfo = new MaUserInfo();
                userInfo.setPassword(password);
                userInfo.setPhone(phone);
                userInfo.setType(type);
                setAppVersion(userInfo, appVersion);
                userInfo.setRegTime(new Date());
                //插入用户信息
                Integer resCount = userInfoService.insertUserInfo(userInfo);
                if (resCount >0) {
                    WebJsonResult result = success();
                    result.setMessage("注册成功");
                    logger.info("registerUserInfo success;");
                    return JsonUtils.objectToJson(result);
                } else {
                    logger.info("registerUserInfo fail;");
                    return JsonUtils.objectToJson(error("注册用户信息失败，请重试！"));
                }

            } else {
                return JsonUtils.objectToJson(error(Constants.RESULT_CODE_1402, "验证码不正确"));
            }
        } catch (Exception e) {
            logger.info("registerUserInfo fail;message:" + e.getMessage());
            return JsonUtils.objectToJson(error("非法请求"));
        }
    }

    /**
     * 获取密码重置验证码
     *
     * @param phone
     * @return WebJsonResult
     */
    @ApiOperation(value="获取密码重置验证码",notes="用户手机号AES加密")
    @RequestMapping(value = "/getPasswordVcode", method = RequestMethod.POST)
    public String getPasswordVcode(@RequestParam(value = "phone", required = true) String phone) {
        // 参数解密
        try {

            if (StringUtils.isBlank(phone)) {
                logger.info("解密失败");
                return JsonUtils.objectToJson(error("非法请求"));
            }
            Map<String, Object> pMap = new HashMap<>();
            pMap.put("phone", phone);
            MaUserInfo userInfo = userInfoService.queryByPhone(phone);
            if (userInfo == null) {
                return JsonUtils.objectToJson(error(Constants.RESULT_CODE_1403, "手机号码未注册"));
            }
            String vCodeTime = jedis.get(Constants.RESET_VCODE_TIME_KEY + phone);
            if (StringUtils.isBlank(vCodeTime)) {
                String vCode = AlibabaMessage.createRandomVcode();
                // 发送验证码
                //SendMsgUtil.sendMsg(phone, "");
                AlibabaMessage.sendSms(phone, vCode,Constants.SMS_RESET);
                jedis.set(Constants.RESET_VCODE_TIME_KEY + phone, Constants.VCODE_EXPIRE);
                jedis.expire(Constants.RESET_VCODE_TIME_KEY + phone, Constants.VCODE_REQUEST_EXPIRE);
                jedis.set(Constants.RESET_PASSWORD_KEY + phone, vCode);
                jedis.expire(Constants.RESET_PASSWORD_KEY + phone, Constants.VCODE_EXPIRE_TIME);
                WebJsonResult result = success();
                logger.info("getPasswordVcode success;");
                return JsonUtils.objectToJson(result);
            } else {
                logger.info("getPasswordVcode fail;");
                return JsonUtils.objectToJson(error("非法请求"));
            }
        } catch (Exception e) {
            logger.info("getPasswordVcode fail;message:" + e.getMessage());
            return JsonUtils.objectToJson(error("非法请求"));
        }
    }
    /**
     * 重置密码
     *
     * @param phone,password,vCode
     * @return WebJsonResult
     */
    @ApiOperation(value="重置密码",notes="用户手机号AES加密")
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String resetPassword(@RequestParam(value = "phone", required = true) String phone,
                                @RequestParam(value = "password", required = true) String password,
                                @RequestParam(value = "vCode", required = true) String vCode) {
        // 对参数解密
        try {

            if (null == phone || null == password || null == vCode) {
                return JsonUtils.objectToJson(error("非法请求"));
            }

            // 获取用户重置验证码
            String json = jedis.get(Constants.RESET_PASSWORD_KEY + phone);
            if (StringUtils.isBlank(json)) {
                logger.info("resetPassword fail！errorCode=" + Constants.RESULT_CODE_1400);
                return JsonUtils.objectToJson(error(Constants.RESULT_CODE_1400, "注册码失效"));
            }
            // 查询用户是否存在
            MaUserInfo userInfo = userInfoService.queryByPhone(phone);
            if (userInfo == null) {
                logger.info("resetPassword fail！errorCode=" + Constants.RESULT_CODE_1403);
                return JsonUtils.objectToJson(error(Constants.RESULT_CODE_1403, "手机号码未注册"));
            }
            // 判断用户验证码是否正确
            if (vCode.equals(json)) {
                userInfo.setPassword(password);
                userInfo.setPhone(phone);
                Integer count = userInfoService.updatetUserInfo(userInfo);
                if (count>0) {
                    //删除验证码
                    jedis.del(Constants.RESET_PASSWORD_KEY + phone);
                    WebJsonResult result = success();
                    result.setMessage("修改成功");
                    logger.info("resetPassword success;");
                    return JsonUtils.objectToJson(result);
                } else {
                    logger.info("resetPassword fail;");
                    return JsonUtils.objectToJson(error("获取用户信息失败，请重试！"));
                }
            } else {
                logger.info("resetPassword fail！errorCode=" + Constants.RESULT_CODE_1402);
                return JsonUtils.objectToJson(error(Constants.RESULT_CODE_1402, "验证码不正确"));
            }
        } catch (Exception e) {
            logger.info("resetPassword fail！message:" + e.getMessage());
            return JsonUtils.objectToJson(error("非法请求"));
        }
    }
    /**
     * 封装用户参数
     * @param userInfo
     * @param appVersion
     */
    private void setAppVersion(MaUserInfo userInfo, String appVersion) {
        if (StringUtils.isNotEmpty(appVersion)) {
            if (appVersion.length() > 100) // app_version最长100个字符
                appVersion = appVersion.substring(0, 100);
            userInfo.setAppVersion(appVersion); // 将app_version更新为本次登录信息
        }
    }

    /**
     * 修改用户信息
     * @return WebJsonResult
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public String updateUserInfo(@ApiParam(name = "headImg", value = "头像", required = false)@RequestParam(value = "headImg", required = false) MultipartFile headImg,
                                 @ApiParam(name = "backImg", value = "背景图", required =false) @RequestParam(value = "backImg", required = false) MultipartFile backImg,
                                 @ApiParam(name = "userToken", value = "用户ID", required = false) @RequestParam(value = "userToken", required = true) String userToken,
                                 @ApiParam(name = "sign", value = "签名", required = false) @RequestParam(value = "sign", required = false) String sign,
                                 @ApiParam(name = "nickname", value = "用户名称", required = false) @RequestParam(value = "nickname", required= false) String nickname,
                                 HttpServletRequest request) {
        MaUserInfo userInfo = new MaUserInfo();
        try {
        userToken= AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
        } catch (Exception ex) {
            logger.error("query user Deatils Exception {}", ex);
        }
        userInfo.setUserToken(userToken);
        if (headImg != null) {

            String results = userInfoService.uploadFile(headImg);
            if (results == null) {
                logger.info("upload headlogo fail;");
                WebJsonResult result = error("头像上传失败！");
                return JsonUtils.objectToJson(result);
            }
            results = MA_FILE_BASE_URL + results;
            userInfo.setHeadImg(results);
        }

        if (backImg != null) {

            String results = userInfoService.uploadFile(backImg);
            if (results == null) {
                logger.info("upload headlogo fail;");
                WebJsonResult result = error("背景图上传失败！");
                return JsonUtils.objectToJson(result);
            }
            results = MA_FILE_BASE_URL + results;
            userInfo.setBackImg(results);
        }

        if (StringUtils.isNotEmpty(sign)) {
            userInfo.setSign(sign);
        }


        if (StringUtils.isNotEmpty(nickname)) {
            userInfo.setNickName(nickname);

        }
        int updateResult=userInfoService.updatetUserInfo(userInfo);
        if(updateResult>0){
            WebJsonResult result = success();
            return JsonUtils.objectToJson(result);
        }
        WebJsonResult result = error("用户信息修改失败！");
        return JsonUtils.objectToJson(result);
    }
}
