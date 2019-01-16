package com.ma.rest.controller;


import com.ma.common.utils.AESUtils;
import com.ma.common.utils.Constants;
import com.ma.common.utils.StringUtils;
import com.ma.common.utils.WebJsonResult;
import com.ma.rest.base.model.AfterSale;
import com.ma.rest.base.model.Order;
import com.ma.rest.pojo.MaUserInfo;
import com.ma.rest.pojo.Reply;
import com.ma.rest.pojo.ReplyDTO;
import com.ma.rest.service.AfterSaleService;
import com.ma.rest.service.OrderService;
import com.ma.rest.service.ReplyService;
import com.ma.rest.service.UserInfoService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author 施龙飞
 * @Title: 退货/售后
 * @date 2018/8/30 9:09
 */
@Controller
@RequestMapping(value = "/aftersale")
public class AfterSaleController extends BaseController {

   @Autowired
   private AfterSaleService afterSaleService;
   @Autowired
   private OrderService orderService;

    private static Logger logger = Logger
            .getLogger(AfterSaleController.class);

    @Resource
    private UserInfoService userInfoService;

    @Value("${MA_FILE_BASE_URL}")
    private String MA_FILE_BASE_URL;

    /**
     * 获取订单的
     * @param request
     * @return
     */
    @ApiOperation(value = "获取订单退货售后信息")
    @ResponseBody
    @RequestMapping(value = "/getaftersale",method = RequestMethod.GET)
    public WebJsonResult getRepliesList(HttpServletRequest request, @ApiParam(name = "orderId", value = "订单Id", required = true)@RequestParam(value = "orderId", required = true)Integer orderId)
    {
        WebJsonResult result = success();
        AfterSale afterSale = afterSaleService.selectAfterSaleById(orderId);
        if(StringUtils.isNotEmpty(afterSale.getPictureUrl())){
            String[] split = afterSale.getPictureUrl().split(",");
            if(CollectionUtils.isNotEmpty(Arrays.asList(split))){
              afterSale.setAftersaleImgArr(Arrays.asList(split));
            }
        }else{
            afterSale.setAftersaleImgArr(new ArrayList<>());
        }
        result.setData(afterSale);
        return result;
    }

    @ApiOperation(value = "添加退货售后")
    @ResponseBody
    @RequestMapping(value = "/addAfterSale", method = RequestMethod.POST)
    public WebJsonResult addComment(@ApiParam(name = "meetQuestion",value = "遇见问题",required = true)@RequestParam(value = "meetQuestion", required = true) String meetQuestion,
                                    @ApiParam(name = "describeQuestion", value = "问题描述", required = false)@RequestParam(value = "content", required = false) String describeQuestion,
                                    @ApiParam(name = "pictureUrl", value = "图片路径", required = false)@RequestParam(value = "pictureUrl", required = false) String pictureUrl,
                                    @ApiParam(name = "orderId", value = "订单Id", required = true)@RequestParam(value = "orderId", required = true) Integer orderId) {
        WebJsonResult result = success();
        try {
            AfterSale afterSale=new AfterSale();
            afterSale.setMeetQuestion(meetQuestion);
            afterSale.setDescribeQuestion(describeQuestion);
            afterSale.setPictureUrl(pictureUrl);
            afterSale.setOrderId(orderId);
            int addResult = afterSaleService.insertAfterSale(afterSale);
            if (addResult > 0) {
                Order order=new Order();
                order.setId(orderId);
                order.setStatus("2");
                Integer integer = orderService.updateOrder(order);
                logger.info("add comment success;");
                result.setMessage("添加成功");
                return result;
            } else {
                result = error("添加失败");
            }
        } catch (Exception ex) {
            logger.error("add comment Exception {}", ex);
            result = error("添加失败");
        }
        return result;
    }

    @ApiOperation(value = "更新退货售后")
    @ResponseBody
    @RequestMapping(value = "/updateAfterSale", method = RequestMethod.POST)
    public WebJsonResult updateComment(@ApiParam(name = "afterSaleId", value = "退货售后Id", required = false)@RequestParam(value = "afterSaleId", required = true) Integer afterSaleId,
                                    @ApiParam(name = "meetQuestion",value = "遇见问题",required = true)@RequestParam(value = "meetQuestion", required = true) String meetQuestion,
                                    @ApiParam(name = "describeQuestion", value = "问题描述", required = false)@RequestParam(value = "content", required = false) String describeQuestion,
                                    @ApiParam(name = "pictureUrl", value = "图片路径", required = false)@RequestParam(value = "pictureUrl", required = false) String pictureUrl,
                                    @ApiParam(name = "orderId", value = "订单Id", required = true)@RequestParam(value = "orderId", required = true) Integer orderId) {
        WebJsonResult result = success();
        Order order=new Order();
        order.setId(orderId);
        order.setStatus("3");
        Integer integer = orderService.updateOrder(order);
        try {
            AfterSale afterSale=new AfterSale();
            afterSale.setId(afterSaleId);
            afterSale.setMeetQuestion(meetQuestion);
            afterSale.setDescribeQuestion(describeQuestion);
            afterSale.setPictureUrl(pictureUrl);
            afterSale.setOrderId(orderId);
            int addResult = afterSaleService.UpdateAfterSale(afterSale);
            if (addResult > 0&&integer>0) {
                logger.info("add comment success;");
                result.setMessage("更新成功");
                return result;
            } else {
                result = error("更新失败");
            }
        } catch (Exception ex) {
            logger.error("add comment Exception {}", ex);
            result = error("更新失败");
        }
        return result;
    }

    @ApiOperation(value = "取消退货售后")
    @ResponseBody
    @RequestMapping(value = "/cancelAfterSale", method = RequestMethod.POST)
    public WebJsonResult updateComment(@ApiParam(name = "orderId", value = "订单Id", required = true)@RequestParam(value = "orderId", required = true) Integer orderId) {
        WebJsonResult result = success();
        try {
            int addResult = afterSaleService.deleteByOrderId(orderId);
            if (addResult > 0) {
                Order order=new Order();
                order.setId(orderId);
                order.setStatus("3");
                Integer integer = orderService.updateOrder(order);
                logger.info("add comment success;");
                return result;
            } else {
                result = error("更新失败");
            }
        } catch (Exception ex) {
            logger.error("add comment Exception {}", ex);
            result = error("更新失败");
        }
        return result;
    }

    @ApiOperation(value = "售后反馈问题列表")
    @ResponseBody
    @RequestMapping(value = "/getConfigList", method = RequestMethod.GET)
    public WebJsonResult getConfigList(@ApiParam(name = "type", value = "类型：0 问题列表", required = true)@RequestParam(value = "type", required = true) Integer type) {
        WebJsonResult result = success();
        try {
            List configList = afterSaleService.selectConfigList(type);
            result.setData(configList);
            return result;

        } catch (Exception ex) {
            logger.error("query config Exception {}", ex);
            result = error("查询失败");
        }
        return result;
    }

    @ApiOperation(value = "用户咨询类信息")
    @ResponseBody
    @RequestMapping(value = "/getSystemMsgList", method = RequestMethod.GET)
    public WebJsonResult getSystemMsgList(@ApiParam(name = "type", value = "类型：0 须知 1：隐私政策", required = true)@RequestParam(value = "type", required = true) Integer type) {
        WebJsonResult result = success();
        try {
            List configList = afterSaleService.selectSystemMsgList(type);
            result.setData(configList);
            return result;

        } catch (Exception ex) {
            logger.error("query system message Exception {}", ex);
            result = error("查询失败");
        }
        return result;
    }

    @ApiOperation(value = "统计接口")
    @ResponseBody
    @RequestMapping(value = "/getStatisticsInfo", method = RequestMethod.GET)
    public WebJsonResult getStatisticsInfo(@ApiParam(name = "type", value = "类型：0 按日 1：按月 3：全部统计", required = true)@RequestParam(value = "type", required = true) Integer type,
        @ApiParam(name = "date", value = "统计日期", required = true)@RequestParam(value = "date", required = true) String date,
        @ApiParam(name = "userToken", value = "用户token", required = true)@RequestParam(value = "userToken", required = true) String userToken) {
        WebJsonResult result = success();
        try {
            userToken= AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
            MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
            if(maUserInfo==null){
                result.setMessage("用户Token失效，请重新登录");
                result.setReturnCode(Constants.RESULT_CODE_1406);
                return result;
            }

            Map<String,Object> paramMap=new HashMap<>();
            paramMap.put("type",type);
            paramMap.put("userId",maUserInfo.getUserId());
            paramMap.put("date",date);

            Map<String,Object> resultMap=new HashMap<>();
            List statList = afterSaleService.selectStatisticsInfo(paramMap);
            if(statList!=null && statList.size()>0){
                Map stateMap=(Map) statList.get(0);
                resultMap.put("totalPrice",stateMap.get("totalPrice"));
                resultMap.put("withdraw",stateMap.get("withdraw"));
            }

            //查询商品详情
            List goodsList = afterSaleService.selectGoodsList(paramMap);
            resultMap.put("goodsList",goodsList);
            result.setData(resultMap);
            return result;

        } catch (Exception ex) {
            logger.error("query system message Exception {}", ex);
            result = error("查询失败");
        }
        return result;
    }
}
