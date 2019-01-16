package com.ma.rest.service.impl;

import com.ma.common.utils.StringUtils;
import com.ma.rest.pojo.WxRefund;
import com.ma.rest.service.WeixinService;
import com.ma.rest.weixin.clienthandler.PrepayIdRequestHandler;
import com.ma.rest.weixin.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
@Service
public class WeixinServiceImpl implements WeixinService {
    private final static Logger logger = LoggerFactory.getLogger(Utils.class);
    @Override
    public String refund(WxRefund wxRefund, HttpServletRequest request, HttpServletResponse response) {
        String out_trade_no="";
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
       /* packageParams.put("appid",  ConstantUtil.APP_ID);//应用id
        packageParams.put("mch_id",  ConstantUtil.MCH_ID);//商户号
        packageParams.put("nonce_str",String.valueOf(UUID.next()));//随机字符串
        packageParams.put("out_trade_no", wxRefund.getOut_trade_no());//订单号
        packageParams.put("out_refund_no", "refund" + "");//退款单号目前为空
        packageParams.put("total_fee","1");//订单总金额Utils.getMoney()
        packageParams.put("refund_fee","1");//退款总金额*/
       // packageParams.put("op_user_id", merchantId);//商户号
        PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
        // String totalFee = request.getParameter("total_fee");
        int total_fee=(int) (Float.valueOf("0.01")*100);
        System.out.println("total:"+total_fee);
        System.out.println("total_fee:" + total_fee);
        prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
        packageParams.put("appid",ConstantUtil.APP_ID);

        prepayReqHandler.setParameter("body", ConstantUtil.BODY);
        packageParams.put("body",ConstantUtil.BODY);

        prepayReqHandler.setParameter("mch_id", ConstantUtil.MCH_ID);
        packageParams.put("mch_id",ConstantUtil.MCH_ID);

        String nonce_str = WXUtil.getNonceStr();
        prepayReqHandler.setParameter("nonce_str", nonce_str);
        packageParams.put("nonce_str",nonce_str);

        prepayReqHandler.setParameter("notify_url", ConstantUtil.NOTIFY_URL);
        packageParams.put("notify_url",ConstantUtil.NOTIFY_URL);

        out_trade_no = String.valueOf(UUID.next());
        prepayReqHandler.setParameter("out_trade_no", out_trade_no);
        packageParams.put("out_trade_no",out_trade_no);

        prepayReqHandler.setParameter("spbill_create_ip", request.getRemoteAddr());
        packageParams.put("spbill_create_ip",request.getRemoteAddr());

        String timestamp = WXUtil.getTimeStamp();
        prepayReqHandler.setParameter("time_start", timestamp);
        packageParams.put("time_start",timestamp);

        System.out.println(String.valueOf(total_fee));
        prepayReqHandler.setParameter("total_fee", String.valueOf(total_fee));
        packageParams.put("total_fee",String.valueOf(total_fee));

        prepayReqHandler.setParameter("trade_type", "APP");
        packageParams.put("trade_type","APP");

        String sign = prepayReqHandler.createMD5Sign();
        String result = "FAIL";
        String msg = "";
        logger.debug("--sign--="+sign);

        String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
        String xml = null;
        try {
            xml = Utils.createXML(packageParams,sign.toUpperCase());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.debug("--xml-="+xml);
        String retur=null;
        try {
            retur = ClientCustomSSL.doRefund(createOrderURL, xml);

            System.out.print(retur);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(retur)) {
            Map map = JSONUtil.parseXmlToMap(retur);

            String returnCode = (String) map.get("return_code");
            if(returnCode.equals("SUCCESS")){
                result = "SUCCESS";
                msg = "OK";
                int status = -1;
                String resultCode = (String)map.get("result_code");
                if(resultCode.equals("SUCCESS")){
                    status = 1;
                }
                if(status == 1) {
                    String outtradeno = (String) map.get("out_trade_no"); // 订单号

//业务操作
                }
            }  if (result.equals("FAIL")) {
                msg = (String)map.get("return_msg");
                logger.info(" 微信退款失败 refundfail msg="+msg);
            }


        }
        return msg;
    }
}
