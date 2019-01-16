package com.ma.common.utils;

/**
 *
 */
public abstract class Constants {
    
	


    /**
     * 请求成功
     */
    public final static String RESULT_CODE_200 = "200";

    /**
     * 请求失败
     */
    public final static String RESULT_CODE_0 = "-1";

    /**
     * 验证码失效
     */
    public final static String RESULT_CODE_1400 = "1400";
    /**
     * 手机号码已经被注册
     */
    public final static String RESULT_CODE_1401 = "1401";
    /**
     * 手机验证码不正确
     */
    public final static String RESULT_CODE_1402 = "1402";
    /**
     * 手机号码未注册
     */
    public final static String RESULT_CODE_1403 = "1403";
    /**
     * 密码不正确
     */
    public final static String RESULT_CODE_1404 = "1404";

    /**
     * 用户token不正确
     */
    public final static String RESULT_CODE_1406 = "1406";


    /**
     * 用户未生效
     */
    public final static String RESULT_CODE_1409 = "1409";
    

    /**
     * 查询数据异常
     */
    public final static String RESULT_CODE_2001 = "2001";

    /**
     *access key
     */
    public final static  String accessKeyId = "LTAIThzRcb6XaiTZ";
    /**
     *秘钥
     */
    public final  static String accessKeySecret = "dtAc09z70l6k9LQP7mEVPnCHyQ2qHr";
    /**
     * 签名
     */
    public final  static String signName ="云天楼";
    /**
     * 用户注册模板编码
     */
    public final static String SMS_REG = "SMS_134125160";

    /**
     * 用户密码重置模板编码
     */
    public final static String SMS_RESET = "SMS_134125161";

    /**
     * UserInfoToken参数加密串
     */
    public final static String IOS_UserInfo_TOKEN_PWD = "3_aa1a8de@95d088";


    /**
     * 注册验证码有效期key
     */
    public final static String REGISTER_VCODE_TIME_KEY = "MA_REDIS_REGISTER_VCODE_TIME";
    /**
     * 请求有效时间值
     */
    public final static String VCODE_EXPIRE = "120";

    /**
     * 验证码请求间隔（秒）
     */
    public final static int VCODE_REQUEST_EXPIRE = 120;

    /**
     * 注册key
     */
    public final static String REGISTER_KEY = "MA_REDIS_REGISTER_KEY";

    /**
     * 验证码有效时间（秒）
     */
    public final static int VCODE_EXPIRE_TIME = 300;

    /**
     * 用户状态1、禁用
     */
    public final static Byte MA_UserInfo_STATUS_1 = 1;
    /**
     * 找回密码验证码有效期key
     */
    public final static String RESET_VCODE_TIME_KEY = "RESET_VCODE_TIME_KEY";

    /**
     * 找回密码key
     */
    public final static String RESET_PASSWORD_KEY = "MA_REDIS_RESET_PASSWORD_KEY";

    /**
     * 产品名称:云通信短信API产品
     */

    public final static  String product = "Dysmsapi";
    /**
     * 产品域名
     */

    public  final static  String domain = "dysmsapi.aliyuncs.com";


    /**
     * 购物车商品数量增加
     */
    public final static String SHOPCAR_ADD = "zeng";
    /**
     * 购物车商品数量减少
     */
    public final static String SHOPCAR_JIAN = "jian";


    /**
     * 全部日期
     */
    public final static String ALLTIME = "0";
    /**
     * 当天
     */
    public final static String TODAY = "1";
    /**
     * 一星期
     */
    public final static String WEEK = "2";


}
