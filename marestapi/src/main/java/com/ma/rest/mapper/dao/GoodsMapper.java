package com.ma.rest.mapper.dao;


import com.ma.rest.base.model.Goods;
import com.ma.rest.base.model.GoodsCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 李光明
 * @Title: 商品处理类
 * @date 2018/7/18 17:04
 */
public interface GoodsMapper {
    List<Goods> selectGoodsList(Map<String, Object> paramMap);

    Integer selectGoodsCount(Map<String, Object> paramMap);


    List<GoodsCategory> selectSubCategoryList(Integer pid);

    List<GoodsCategory> selectFirstCategoryList();

    List<Goods> selectGoodsByCategoryId(Map<String,Object> params);
    Integer selectCountGoodsByCategoryId(Map<String,Object> params);
    Goods selectgoodsInfoById(Integer goodsId);
    Goods selectgoodsById(Integer goodsId);
    List<Goods> queryOrderGoodsList(@Param("orderId")Integer orderId);

    int updateGoods(Goods goods);

    List selectListByQueryCon(Map<String,Object> params);
}
