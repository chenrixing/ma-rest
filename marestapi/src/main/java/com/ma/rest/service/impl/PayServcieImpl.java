package com.ma.rest.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.ma.common.model.Result;
import com.ma.common.utils.ResultUtils;
import com.ma.rest.service.PayService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Service
public class PayServcieImpl implements PayService {


    // 6.请求网关地址
    public static String URL = "https://openapi.alipaydev.com/gateway.do";

    public static String service = "mobile.securitypay.pay";//固定值

    //appId
    public static String appId = "2016091600526479";

    //private key
    public static String appPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCRBxfdt3jGxeIPEkovohXoHyrQkxzY38TbgA0q/5yUUEpENLRkwHcN4iWO1fWoOEmEcI+Ty7YdcqjvMePk63JtDa9X+Wsu4L96xjCCwKQ8pgSYkqA1kR54IffeJ3X+mhGOxQWzOxohPYwliKZmYYcJH9R/r1KiabOW+I93m0DQEE/6pDX7DgsLrhFv+jAkThpEVWod0B5RP3SANWQi6BmgFzmF5OcfqtvZqxyNxJyOqY6fklLf2ln1n0yLiiN78SfCnaoImHx8DT4h1K6eIY5xRrQ7m2O3SCixPw37ZRYPHiqESsoiWqmqlQST2FXhf0JCw/76QaICaJklzg5GuBfXAgMBAAECggEAN6gwOY+S0z3X/o63V8Ztf9KOPNNoa7acttxSsWC9d5HEnp5XaJ8QUYyre0CTcUaAuEn2X/L65hw9xuYvnofZNw7cbveycaAg+/4ZWeSSYxAXjXIf987Ekbf/hws/1kDMYi0sfdc9tOoI4dKqgXoGN7M2evYO0XOdT1/+txkm63HytZ4MKOn6OtZpBNwxpMoqQ870u9MdZ/mdp1IF15IhO/PppZY6wwWTPizeKX/iCmp5sVHdBZQ0w1+h52uA5zYdGfcg+xZL7pcYX4MU2Cn2mmEdfg/rUABttTEvuk7HvHaRkmExQWAr3YbmDxZw+kdC0ZWVEjRyJ894srT6rxwSeQKBgQDSkJ2cUihb7EabJft4pUA1X4MoaTA3FN/yfaHaBholwoEZsaRcqP5CRG8WclP7Rtk1/IMxGmVNtOxtc1Acamrk4KDuEIxFXhNDSKUzxGvOu5GwdGHz3chkdaXRISeokOOKalijDtSFgGWw6EdybipEIhy7EgUdt1vsJ+koXbbclQKBgQCwUkWGr/hJ+GyQHo+iKYvkuzL3q1KvyEbRi7HJ1Y4dadhPJNtp6PxbK7kIdGZYHUCQNrkzjHchh7XSBl6QsfVJD3Eyi+iE3znCRR0nqKv61/C5KF0uGN+KCS95N15F+kLpDSTLZH1ioilYmMLaq1eL59+d+AnS9dlR5wGEH8xbuwKBgQCPWpmTNXP4sTGYqxE7n/XI4I/g0UxcE8ArzVCFqKXgkz1lisWFuB/yabjfpVJ7vk5fLNz8byYM82ILWfhugBzqWev2aBm3mEaxBiJCzjU/d//GDFYruxCVJGuRp2rinlHriF90E8KoaTPSbzFFPK9V14uL6MbG5nCUpM9eBf9ayQKBgD6nfX022ouucyVjNrbw/Hzy6FGJ14APMtsTQMHv2OgqzGqTNkSUPdhH2SBf/F4jRuX4DCd9KngHuVxAVyojkn27twW3IZDVobiexpVpInOoeCnTZgkTXZu3V99DBYToQQ5/VSvRMZzy8q5A2jzJ1fopExUXJqaIJgkshSVmB0AxAoGAG/AOXoZR0MJ2bIl/IK9ECVQrUSZMHsfwmdAjbOfIL3j9pgeJf41i94dSTDL7KL55955+Iw78TmkaZEY7cbff3OE1NdAZ7CWn5U+qjAMk0eMxwFKP8k9sDCX/zvd5H4xxSIL61C3Ul6cciNyvPwb/raopQ62MHNnzMfutfoOZpEE=";

    //public key  支付宝的公钥，无需修改该值（不要删除也不要修改，在接收通知的时候需要进行签名认证）
    public static String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4Xzp7bnzKgzL2KCzAuJyCcCUzI8X1YJdsHV1rs4avD1fLue+6d6V7nBRY4P8lBjETU6rl7ywWVofUjBVygEvXe5FyZRJTjYA0F93vLWi8fsORxhD19tfknysnucchp2eREf6HNfr990wkhnMvM4SAqpZjKigqIxk1RMQEbKeMSZts+T/JJCTp6DGo7+/8deV+RXoiOC3Dx/HfZhYZ8FxnVoJo0K4+acrOltp5eIAXG6EbAXC84UOJe0XUm8y82BZsX9aUdPDtWONZTz+7+2arb4PJN8CSeaN8nueHQWdd9wyCmyw80YWGesYudJgdbARwrnKed2b8F+si8ES+byWuQIDAQAB";

    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String input_charset = "utf-8";
    // 签名方式 不需修改
    public static String sign_type = "RSA2";

    //支付宝回调地址这个地址目前访问不到 是外网地址才可以
    public static String notify_url = "http://120.77.214.155:8080/ma-rest/pay/order-pay-notify-controller";

    @Override
    @Transactional
    public String sendCodeUnifiedOrderForBalance(String orderNumber, Double money, String allOrderNumber) {
        //签名方式
        String sign_type = "RSA2";
        //编码格式
        String CHARSET = "utf-8";
        //正式环境支付宝网关，如果是沙箱环境需更改成https://openapi.alipaydev.com/gateway.do
        String url = "https://openapi.alipay.com/gateway.do";
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, appPrivateKey, "json", CHARSET, alipayPublicKey, sign_type);
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(orderNumber);
        model.setSubject(orderNumber);
        //请保证OutTradeNo值每次保证唯一
        model.setOutTradeNo(getRandomFileName());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(String.valueOf(money));
        model.setProductCode("QUICK_MSECURITY_PAY");
        model.setOutTradeNo(orderNumber);
        if (allOrderNumber != null) {
            model.setBody(allOrderNumber);
        }
        request.setBizModel(model);
        request.setNotifyUrl(notify_url);
        //这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse response = null;
        try {
            response = alipayClient.sdkExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //获得app支付生成的签名参数并赋值用于生成二维码
        String content = response.getBody();
        content = content.replaceAll("\\+", "%20");
        return content;
    }

    /**
     * 生成随机数
     *
     * @return
     */
    public static String getRandomFileName() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return rannum + str;
    }
}
