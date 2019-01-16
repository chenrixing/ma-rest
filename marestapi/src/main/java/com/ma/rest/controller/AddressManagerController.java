package com.ma.rest.controller;

import com.ma.common.utils.AESUtils;
import com.ma.common.utils.Constants;
import com.ma.common.utils.StringUtils;
import com.ma.common.utils.WebJsonResult;
import com.ma.rest.base.model.Area;
import com.ma.rest.base.model.City;
import com.ma.rest.base.model.Province;
import com.ma.rest.dao.JedisClient;
import com.ma.rest.export.ExportUtil;
import com.ma.rest.pojo.MaUserInfo;
import com.ma.rest.service.AddressManagerService;
import com.ma.rest.service.UserInfoService;
import com.ma.rest.utils.OrderNumberUtil;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author 李光明
 * @Title: 个人中心
 * @date 2018/6/216:39
 */
@RestController
@RequestMapping(value = "/addressManager")
public class AddressManagerController extends BaseController {


    private static Logger logger = Logger.getLogger(ShoppingCartController.class);
    @Autowired
    private AddressManagerService addressManagerService;
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JedisClient jedisClient;

    @ApiOperation(value = "查询地址列表")
    @RequestMapping(value = "/getAddressList", method = RequestMethod.GET)
    public WebJsonResult getAddressList( @ApiParam(value = "用户Token", required = true) @RequestParam(value = "userToken", required = true)String userToken,
                                         @ApiParam(value = "是否获取默认地址", required = false) @RequestParam(value = "isDefault", required = false)String isDefault) {
        WebJsonResult result = success();
        try {
            userToken= AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);

            MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
            if(maUserInfo==null){
                result.setMessage("用户Token失效，请重新登录");
                result.setReturnCode(Constants.RESULT_CODE_1406);
                return result;
            }else{
                    List addressList=addressManagerService.getAddressList(maUserInfo.getUserId(),isDefault);
                    result.setData(addressList);
                    return result;

            }
        }catch (Exception ex) {
                logger.error("query address  Deatils Exception {}", ex);
        }
        return error("查询地址列表异常");
    }


    @ApiOperation(value = "删除地址")
    @RequestMapping(value = "/deleteAddress", method = RequestMethod.GET)
    public WebJsonResult deleteAddress( @ApiParam(value = "用户Token", required = true) @RequestParam(value = "userToken", required = true)String userToken,
                                        @ApiParam(value = "地址Id", required = true) @RequestParam(value = "addressId", required = true)Integer addressId
                                        ) {
        WebJsonResult result = success();
        Map<String, Object> params = new HashMap<>();
        Integer integer=0;
        try {
            userToken= AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
        MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
        if(maUserInfo==null){
            result.setMessage("用户Token失效，请重新登录");
            result.setReturnCode(Constants.RESULT_CODE_1406);
            return result;
        }else{
             params.put("userId",maUserInfo.getUserId());
             params.put("addressId", addressId);
             integer = addressManagerService.deleteAddress(params);}

            List isHaveList=addressManagerService.getAddressList(maUserInfo.getUserId(),"1");
            if(isHaveList==null ||isHaveList.size()==0){
                params.put("isDefault","1");
                List newDefaultList=addressManagerService.getAddressList(maUserInfo.getUserId(),"0");
                if(newDefaultList!=null && newDefaultList.size()>0){
                    Map<String,Object> isDefautlAdd=(Map<String,Object>)newDefaultList.get(0);
                    params.put("addressId", isDefautlAdd.get("addressId").toString());
                    addressManagerService.updateAddress(params);
                }
            }else{
                params.put("isDefault","0");
            }
        } catch (Exception ex) {
            logger.error("delete address Exception {}", ex);
        }
        if(integer>0){
            return result;
        }else{
            return error("删除地址失败");
        }
    }



    @ApiOperation(value = "获取所有省份信息")
    @RequestMapping(value = "/getProvince", method = RequestMethod.GET)
    public WebJsonResult getProvince(@RequestParam(value = "code", required = true) String code) {
        WebJsonResult result = success();
        try {
            if(StringUtils.equals(code,"province")){
                List<Province> provinceList = addressManagerService.getProvinceList();
                result.setData(provinceList);
            }
            logger.info("query Province success;");
            return result;
        } catch (Exception ex) {
            logger.error("query Province Exception {}", ex);
        }
        return result;
    }

    @ApiOperation(value = "获取所有城市信息")
    @RequestMapping(value = "/getCity", method = RequestMethod.GET)
    public WebJsonResult getCity(@RequestParam(value = "provinceCode", required = true) String provinceCode) {
        WebJsonResult result = success();
        try {
                List<City> cityList = addressManagerService.getCityList(provinceCode);
                result.setData(cityList);
            logger.info("query City success;");
            return result;
        } catch (Exception ex) {
            logger.error("query City Exception {}", ex);
        }
        return result;
    }

    @ApiOperation(value = "获取所有地区信息")
    @RequestMapping(value = "/getArea", method = RequestMethod.GET)
    public WebJsonResult getArea(@RequestParam(value = "cityCode", required = true) String cityCode) {
        WebJsonResult result = success();
        try {
                List<Area> areaList = addressManagerService.getAreaList(cityCode);
                result.setData(areaList);
            logger.info("query Area success;");
            return result;
        } catch (Exception ex) {
            logger.error("query Area Exception {}", ex);
        }
        return result;
    }

    @ApiOperation(value = "添加地址列表")
    @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
    public WebJsonResult addAddress(@ApiParam(name = "provinceCode", value = "省code", required = false)@RequestParam(value = "provinceCode", required = true) String provinceCode,
                                    @ApiParam(name = "cityCode", value = "市code", required = false)@RequestParam(value = "cityCode", required = true) String cityCode,
                                    @ApiParam(name = "areaCode", value = "区code", required = false)@RequestParam(value = "areaCode", required = true) String areaCode,
                                    @ApiParam(value = "用户Token", required = true) @RequestParam(value = "userToken", required = true)String userToken,
                                    @ApiParam(name = "addressDetails", value = "详细地址", required = false)@RequestParam(value = "addressDetails", required = true) String addressDetails,
                                    @ApiParam(name = "isDefault", value = "是否是默认地址 0：否 1 ：是", required = false)@RequestParam(value = "isDefault", required = false) String isDefault,
                                    @ApiParam(name = "phone", value = "手机号", required = false)@RequestParam(value = "phone", required = true) String phone,
                                    @ApiParam(name = "linkman", value = "联系人姓名", required = false)@RequestParam(value = "linkman", required = true) String linkman) {
        try {
            userToken= AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);

        WebJsonResult result = success();
        MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
        if(maUserInfo==null){
            result.setMessage("用户Token失效，请重新登录");
            result.setReturnCode(Constants.RESULT_CODE_1406);
            return result;
        }else{
            Map<String,Object> params= new HashMap<>();
            params.put("provinceCode",provinceCode);
            params.put("cityCode",cityCode);
            params.put("areaCode",areaCode);
            params.put("addressDetails",addressDetails);
            params.put("userId",maUserInfo.getUserId());

            params.put("phone",phone);
            params.put("linkman",linkman);

            List isHaveList=addressManagerService.getAddressList(maUserInfo.getUserId(),"1");
            if(isHaveList==null ||isHaveList.size()==0){
                params.put("isDefault","1");
            }else{
                params.put("isDefault","0");
            }

            int addCount=addressManagerService.addAddress(params);
            if(addCount==0){
                result=error("新增地址失败!");
            }
            return result;}
        } catch (Exception ex) {
                logger.error("add address Deatils Exception {}", ex);
            }
        return error("新增地址出现异常");
    }


    @ApiOperation(value = "修改地址信息")
    @RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
    public WebJsonResult updateAddress(@ApiParam(name = "addressId", value = "主鍵ID", required = false)@RequestParam(value = "addressId", required = true) String addressId,
                                       @ApiParam(name = "provinceCode", value = "省code", required = false)@RequestParam(value = "provinceCode", required = true) String provinceCode,
                                       @ApiParam(name = "cityCode", value = "市code", required = false)@RequestParam(value = "cityCode", required = true) String cityCode,
                                       @ApiParam(name = "areaCode", value = "区code", required = false)@RequestParam(value = "areaCode", required = true) String areaCode,
                                       @ApiParam(value = "用户Token", required = true) @RequestParam(value = "userToken", required = true)String userToken,
                                       @ApiParam(name = "addressDetails", value = "详细地址", required = false)@RequestParam(value = "addressDetails", required = true) String addressDetails,
                                       @ApiParam(name = "isDefault", value = "是否是默认地址 0：否 1 ：是", required = false)@RequestParam(value = "isDefault", required = false) String isDefault,
                                       @ApiParam(name = "phone", value = "手机号", required = false)@RequestParam(value = "phone", required = true) String phone,
                                       @ApiParam(name = "linkman", value = "联系人姓名", required = false)@RequestParam(value = "linkman", required = true) String linkman) {
        WebJsonResult result = success();
        try {
            userToken= AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
        MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
        if(maUserInfo==null){
            result.setMessage("用户Token失效，请重新登录");
            result.setReturnCode(Constants.RESULT_CODE_1406);
            return result;
        }else{
        Map<String,Object> params= new HashMap<>();
        params.put("provinceCode",provinceCode);
        params.put("cityCode",cityCode);
        params.put("areaCode",areaCode);
        params.put("addressDetails",addressDetails);
        params.put("userId",maUserInfo.getUserId());
        params.put("isDefault",isDefault);
        params.put("phone",phone);
        params.put("linkman",linkman);
        params.put("addressId",addressId);

            List isHaveList=addressManagerService.getAddressList(maUserInfo.getUserId(),"1");
            if(isHaveList==null ||isHaveList.size()==0){
                params.put("isDefault","1");
            }else{
                params.put("isDefault","0");
            }
            int addCount=addressManagerService.updateAddress(params);
            if(addCount==0){
                result=error("更新地址失败!");
            }
            return result;}
        } catch (Exception ex) {
            result=error("更新地址失败!");
            logger.error("add address Exception {}", ex);
        }
        return result;
    }

    @ApiOperation(value = "导出省份信息信息")
    @RequestMapping(value = "/exportProvince", method = RequestMethod.GET)
    public WebJsonResult exportAddress() {
        WebJsonResult result = success();
        try {
            String sheetName = "所有省份统计表单";
            String titleName = "全国所有省份信息表";
            String fileName = "省份表";
            List<Province> provinceList = addressManagerService.getProvinceList();
            String[] columnName = {  "省份ID", "省份名称" };
            int columnNumber = columnName.length;
            List<Integer> widths=new ArrayList<Integer>();
            for (int i=0;i<columnName.length;i++){
                widths.add(30);
            }
            Integer[] columnWidth = widths.toArray(new Integer[widths.size()]);
            String[][] dataList=new String[provinceList.size()][2];
            for(int i=0;i<provinceList.size();i++){
                Province province = provinceList.get(i);
                dataList[i][0]=province.getProvinceid();
                dataList[i][1]=province.getProvince();
            }
            try {
                ExportUtil.ExportNoResponse(sheetName, titleName, fileName,
                    columnNumber, columnWidth, columnName, dataList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        } catch (Exception ex) {
            logger.error("add address Exception {}", ex);
        }
        return result;
    }
}
