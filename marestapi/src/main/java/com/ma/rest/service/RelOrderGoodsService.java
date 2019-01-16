package com.ma.rest.service;

import java.util.Map;

/**
 * Descriptions
 *
 * @Author施龙飞
 * @date: Created in10:16 2018/7/20 0020
 **/
public interface RelOrderGoodsService {

    Map<String,Object> insertRelOrderGoods(String goodsIds, String goodsNumbers, Integer userId, Integer addressId);

}
