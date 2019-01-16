package com.ma.rest.service;

import com.ma.rest.base.model.Goods;
import com.ma.rest.base.model.GoodsShopCar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 李光明
 * @Title: 购物车service
 * @date 2018/6/21 7:28
 */
public interface ShoppingCartService {
    /**
     * 添加购物车
     * @param goodsShopCar
     * @return
     */
    Integer addShopCar(GoodsShopCar goodsShopCar);

    /**
     * 根据用户信息取查询购物车列表
     * @param userId
     * @return
     */
    List<GoodsShopCar> getAllShopCar(Integer userId,Integer goodsId);

    /**
     * 批量删除购物车 清控商品
     */
    Integer deleteBatch(String goodIds,Integer userId);

   List<GoodsShopCar> queryShopCarGoodsList(Integer userId,Integer goodsId);
    Integer updateShopCar(GoodsShopCar goodsShopCar);
}
