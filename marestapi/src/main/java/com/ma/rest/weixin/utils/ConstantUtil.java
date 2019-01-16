package com.ma.rest.weixin.utils;

public class ConstantUtil {
    /**
     * 微信开发平台应用ID
     */
    public static final String APP_ID="wxd930ea5d5a258f4f";
    /**
     * 应用对应的凭证
     */
    public static final String APP_SECRET="1d29d733ewrewrfwed1b58968cce";
    /**
     * 应用对应的密钥
     */
    public static final String APP_KEY="dfsfdvdfvgk32423423oGdfsfdsvBO66";
    /**
     * 微信支付商户号
     */
    public static final String MCH_ID="10000100";
    /**
     * 商品描述
     */
    public static final String BODY="电商支付";
    /**
     * 商户号对应的密钥
     */
    public static final String PARTNER_key="123466";

    /**
     * 商户id
     */
    public static final String PARTNER_ID="14698402402";
    /**
     * 常量固定值
     */
    public static final String GRANT_TYPE="client_credential";
    /**
     * 获取预支付id的接口url
     */
    public static String GATEURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 微信服务器回调通知url
     */
    public static String NOTIFY_URL="http://www.bairuoheng.cn/app/tenpay/notify";
}