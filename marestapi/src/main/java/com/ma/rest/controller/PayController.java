package com.ma.rest.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.ma.common.utils.StringUtils;
import com.ma.common.utils.WebJsonResult;
import com.ma.rest.alipay.AlipayRefund;
import com.ma.rest.base.model.Order;
import com.ma.rest.dao.JedisClient;
import com.ma.rest.service.OrderService;
import com.ma.rest.service.PayService;
import com.ma.rest.service.impl.PayServcieImpl;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 支付业务处理类
 */
@RestController
@RequestMapping(path = "/pay", produces = "text/plain;charset=UTF-8")
public class PayController extends BaseController {

    @Autowired
    private JedisClient jedis;
    @Autowired
    private PayService payService;
    @Autowired
    private OrderService orderService;
    private static Logger logger = Logger.getLogger(PayController.class);
    @ApiOperation(value="支付宝回调接口")
    @RequestMapping(value = "/order-pay-notify-controller", method = {RequestMethod.POST})
    public void notify(@RequestBody(required = false) String body, HttpServletRequest requests, HttpServletResponse httpServletResponse)
        throws IOException {
        // 1.从支付宝回调的request域中取值
        Map<String, String[]> requestParams = requests.getParameterMap();
        Map<String,String> params=new HashMap<String, String>();
        for(Map.Entry<String,String[]> entry:requestParams.entrySet()){
            System.out.println(entry.getKey());
            String[] value = entry.getValue();
            for (String str:value) {
                System.out.println(str);
            }
        }
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        // 2.封装必须参数
        String out_trade_no = requests.getParameter("out_trade_no"); // 商户订单号
        String orderType = requests.getParameter("body"); // 订单内容
        String tradeStatus = requests.getParameter("trade_status"); // 交易状态
        String allOrderNumber = requests.getParameter("body"); // 附加信息 allOrderNumber
        // 3.签名验证(对支付宝返回的数据验证，确定是支付宝返回的)
        boolean signVerified = false;
        try {

            // 3.1调用SDK验证签名
            signVerified = AlipaySignature.rsaCheckV1(params, PayServcieImpl.alipayPublicKey, PayServcieImpl.input_charset,PayServcieImpl.sign_type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 4.对验签进行处理
        if(signVerified){ // 验签通过
            if (tradeStatus.equals("TRADE_SUCCESS")) {
                // 只处理支付成功的订单:修改交易表状态,支付成功
                //填写自己的业务代码
                if(allOrderNumber==null){
                    Order order1 = orderService.selectByOrderNumber(out_trade_no);
                    Order order=new Order();
                    order.setId(order1.getId());
                    order.setStatus("7");
                    order.setPayTime(new Date());
                    Integer integer = orderService.updateOrder(order);
                }else{
                    String[] split = allOrderNumber.split(",");
                    List<String> strings = Arrays.asList(split);
                    for(int i=0;i<strings.size();i++){
                        Order order1 = orderService.selectByOrderNumber(strings.get(i));
                        Order order=new Order();
                        order.setId(order1.getId());
                        order.setStatus("7");
                        order.setPayTime(new Date());
                        Integer integer = orderService.updateOrder(order);
                    }
                }
            }
        }
    }

    @ApiOperation(value="支付宝支付接口")
    @RequestMapping(value = "/tryPay", method = {RequestMethod.POST} , produces = "application/json;charset=UTF-8")
    public WebJsonResult tryPay(@ApiParam(value = "附加信息订单编号", required = false) @RequestParam(value = "allOrderNumber", required = false)String allOrderNumber,
                                @ApiParam(value = "订单编号", required = true) @RequestParam(value = "orderNumber", required = true)String orderNumber
    )
        throws IOException {
        BigDecimal sumPrice=BigDecimal.ZERO;
        if(StringUtils.isNotEmpty(allOrderNumber)){
            String[] split = allOrderNumber.split(",");
            List<String> strings = Arrays.asList(split);
            for(int i=0;i<strings.size();i++){
                Order order = orderService.selectByOrderNumber(strings.get(i));
                sumPrice=sumPrice.add(order.getPayPrice());
            }
        }else{
            Order order = orderService.selectByOrderNumber(orderNumber);
            sumPrice=sumPrice.add(order.getPayPrice());
        }
        String payResult=null;
        if (allOrderNumber != null) {
            payResult = payService.sendCodeUnifiedOrderForBalance(orderNumber.toString(),Double.valueOf(sumPrice.toString()),allOrderNumber.toString());
        }else{
            payResult = payService.sendCodeUnifiedOrderForBalance(orderNumber.toString(),Double.valueOf(sumPrice.toString()),null);
        }
        WebJsonResult result=success();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("content",payResult);
        result.setData(map);
        return result;
    }

}