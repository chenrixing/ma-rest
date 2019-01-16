package com.ma.rest.service.impl;

import com.ma.rest.base.model.Goods;
import com.ma.rest.base.model.GoodsShopCar;
import com.ma.rest.mapper.dao.GoodsShopCarMapper;
import com.ma.rest.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李光明
 * @Title: 购物车
 * @date 2018/6/217:31
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private GoodsShopCarMapper goodsShopCarMapper;

    @Override
    public Integer addShopCar(GoodsShopCar goodsShopCar) {
        return goodsShopCarMapper.insertSelective(goodsShopCar);
    }

    @Override
    public List<GoodsShopCar> getAllShopCar(Integer userId,Integer goodsId) {
        return goodsShopCarMapper.selectAllGoodsShopCarByUserId(userId,goodsId);
    }

    @Override
    public Integer deleteBatch(String goodIds,Integer userId) {
        return goodsShopCarMapper.deleteBatch(goodIds,userId);
    }

    @Override
    public List<GoodsShopCar> queryShopCarGoodsList(Integer userId,Integer goodsId) {
        return goodsShopCarMapper.queryShopCarGoodsList(userId,goodsId);
    }

    @Override
    public Integer updateShopCar(GoodsShopCar goodsShopCar) {
        return goodsShopCarMapper.updateByPrimaryKeySelective(goodsShopCar);
    }
}
