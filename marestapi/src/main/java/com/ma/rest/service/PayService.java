package com.ma.rest.service;

import com.ma.common.model.Result;

/**
 * 支付业务接口
 */
public interface PayService {
    public String sendCodeUnifiedOrderForBalance(String orderId, Double money,String allOrderNumber);
}
