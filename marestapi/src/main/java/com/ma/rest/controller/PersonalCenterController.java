package com.ma.rest.controller;

import com.ma.common.utils.AESUtils;
import com.ma.common.utils.Constants;
import com.ma.common.utils.WebJsonResult;
import com.ma.rest.pojo.MaUserInfo;
import com.ma.rest.service.UserInfoService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李光明
 * @Title: 个人中心
 * @date 2018/6/216:39
 */
@RestController
@RequestMapping(value = "/personalCenter")
public class PersonalCenterController extends BaseController {

    private static Logger logger = Logger.getLogger(PersonalCenterController.class);
    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "查询我的订单列表")
    @RequestMapping(value = "/getMyOrderList", method = RequestMethod.GET)
    public WebJsonResult getMyOrderList(@RequestParam(value = "userToken", required = true) String userToken) {
        WebJsonResult result = success();
        try {
            //result.setData(map);
            logger.info("queryMyOrderList success;");
            return result;
        } catch (Exception ex) {
            logger.error("query order List Cart Exception {}", ex);
        }
        return result;
    }

    @ApiOperation(value = "查询订单详情")
    @RequestMapping(value = "/getMyOrderInfo", method = RequestMethod.GET)
    public WebJsonResult getMyOrderInfo(@RequestParam(value = "orderId", required = true) String orderId, @RequestParam(value = "userToken", required = true) String userToken) {
        WebJsonResult result = success();
        try {
            //result.setData(map);
            logger.info("queryMyOrderInfo success;");
            return result;
        } catch (Exception ex) {
            logger.error("query order Deatils Exception {}", ex);
        }
        return result;
    }

    @ApiOperation(value = "UserToken查询用户基本信息")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public WebJsonResult getUserInfo(@ApiParam(value = "用户Token", required = true) @RequestParam(value = "userToken", required = true) String userToken) {
        WebJsonResult result = success();
        try {

            userToken = AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
            MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
            if (maUserInfo == null) {
                result.setMessage("用户Token失效，请重新登录");
                result.setReturnCode(Constants.RESULT_CODE_1406);
                return result;
            } else {
                result.setData(maUserInfo);
                logger.info("queryUserInfo success;");
                return result;
            }
        } catch (Exception ex) {
            logger.error("query user Deatils Exception {}", ex);
        }
        return error("UserToken查询用户基本信息出现异常");
    }

    @ApiOperation(value = "根据UserId查询用户基本信息")
    @RequestMapping(value = "/getUserInfoByUserId", method = RequestMethod.GET)
    public WebJsonResult getUserInfoByUserId(@ApiParam(value = "用户Id", required = true) @RequestParam(value = "userId", required = true) Integer userId) {
        WebJsonResult result = success();
        try {
            MaUserInfo maUserInfo = userInfoService.queryByUserId(userId);
            result.setData(maUserInfo);
            logger.info("queryUserInfo success;");
            return result;
        } catch (Exception ex) {
            logger.error("query user Deatils Exception {}", ex);
        }
        return result;
    }
}
