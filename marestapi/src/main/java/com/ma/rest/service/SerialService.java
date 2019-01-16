package com.ma.rest.service;


import com.ma.rest.base.model.Serial;

/**
 * Descriptions
 *
 * @Author施龙飞
 * @date: Created in10:26 2018/7/19 0019
 **/
public interface SerialService {
    String getSerialNumber(Integer maxNum);
    Serial selectSerial();
}
