package com.ma.rest.service;

import com.ma.rest.pojo.WxRefund;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信退款业务
 */
public interface WeixinService {
    public String refund(WxRefund wxRefund, HttpServletRequest request, HttpServletResponse response);
}
