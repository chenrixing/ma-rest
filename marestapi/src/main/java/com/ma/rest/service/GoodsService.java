package com.ma.rest.service;

import com.ma.rest.base.model.Goods;
import com.ma.rest.base.model.GoodsCategory;

import java.util.List;
import java.util.Map;

/**
 * @author 李光明
 * @Title: 查询商品信息
 * @date 2018/6/21 7:26
 */
public interface GoodsService {
    /**
     * 查询一级分类
     * @return
     */

    List<GoodsCategory> selectFirstCategoryList();

    /**
     * 查询子分类
     * @param pid
     * @return
     */
    List<GoodsCategory> selectSubCategoryList(Integer pid);

    /**
     * 根据分类查询商品
     * @param params
     * @return
     */
    List<Goods> selectGoodsByCategoryId(Map<String,Object> params);
    /**
     * 根据分类查询商品数量
     * @param params
     * @return
     */
    Integer selectCountGoodsByCategoryId(Map<String,Object> params);

    /**
     * 查询商品详情
     * @param goodsId
     * @return
     */
    Goods selectgoodsInfoById(Integer goodsId);
    Goods selectgoodsById(Integer goodsId);
    /**
     * 查询订单下面的商品详情
     */
    List<Goods> selectOrderGoodsList(Integer orderId);
    Integer updateGoods(Goods goods);

    /**
     * 全局搜索
     * @param params
     * @return
     */
    List selectListByQueryCon(Map<String,Object> params);
}
