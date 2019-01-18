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
    public static String appId = "2088231437167811";

    //private key
    public static String appPrivateKey = "MIICXAIBAAKBgQCodmnHmWYXc88KbFcvo0kMOBENkem3su207ZH8CZq8cb5ZixVzVNwWPMjBMCqHFPpwvywPLIFysNcImZFawHnVMcQ04juTwb40b3hfKviXyXlHjnrYvCy0iBzwQIxIHl5MXUTgmOxp9xorHZlO8YnSddVrXvnf8QJIJwt43GWviwIDAQABAoGAVUVi88KE97gKA7elRhq7vfCfSSQjjnWGdv+N1mqyJVLZzA9nszIwGR15bLhJvohy4CEUVzXq7DfxEIxTFAG7aMlBVvtaEYfqE2IPrAICOmNE3i7KDlrvHymMoNDq/pIP14fNViOKWW3Bn0w4kJkJ7fn3wVmamVLVhofqS+0URdkCQQDaUqG73RfQaz1uPjDsKXYhRC4gTcOIFCE+Eh8iEr9bgp6fGKHK0EE8VAOvI9/wcBYjdpwNvkQJZisX+iNjUkGlAkEAxYj8mVadFIJsP/uCZyr2TmDQNoavaDS9+aOi28EFl8UvuZzwWpS5URR5gxCCsZg+BDajyEMYXPMk9TGL3jcFbwJAdMFMDROn8KXpLFPGBgYGTrQPlub/cn6RaIh4bdhozZ+FBMA/hh7pREIJ1lfxIrxOmpiLzC28ZOpmkWQMGMmcoQJBAKkngm/ozMC4YuxzexrPnnZFz2cx9Nk+eQRijV6zrw9la1X+P5DB1OPKrbd/lvtvzIOdKqmeUymdiSNy1zxmT38CQGVHWtKUTnHG0S1Xs7DN84PVMDVEooS68ssfzeO8eWaTsjRJz4LGOpdTix1bn5drChKjyFVwtffseyXWveuDcwA=";

    //public key  支付宝的公钥，无需修改该值（不要删除也不要修改，在接收通知的时候需要进行签名认证）
    public static String alipayPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCodmnHmWYXc88KbFcvo0kMOBENkem3su207ZH8CZq8cb5ZixVzVNwWPMjBMCqHFPpwvywPLIFysNcImZFawHnVMcQ04juTwb40b3hfKviXyXlHjnrYvCy0iBzwQIxIHl5MXUTgmOxp9xorHZlO8YnSddVrXvnf8QJIJwt43GWviwIDAQAB";

    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String input_charset = "utf-8";
    // 签名方式 不需修改
    public static String sign_type = "RSA2";

    //支付宝回调地址这个地址目前访问不到 是外网地址才可以
    public static String notify_url = "http://47.110.133.248:8081/ma-rest/pay/order-pay-notify-controller";

    @Override
    @Transactional
    public String sendCodeUnifiedOrderForBalance(String orderNumber, Double money, String allOrderNumber) {
        //签名方式
        String sign_type = "RSA2";
        //编码格式
        String CHARSET = "utf-8";
        //正式环境支付宝网关，如果是沙箱环境需更改成https://openapi.alipaydev.com/gateway.do
        String url = "https://openapi.alipaydev.com/gateway.do";
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
