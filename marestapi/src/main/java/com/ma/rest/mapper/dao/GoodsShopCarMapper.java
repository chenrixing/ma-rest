package com.ma.rest.mapper.dao;

import com.ma.rest.base.model.Goods;
import com.ma.rest.base.model.GoodsShopCar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsShopCarMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_shopcar
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_shopcar
     *
     * @mbggenerated
     */
    int insert(GoodsShopCar record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_shopcar
     *
     * @mbggenerated
     */
    int insertSelective(GoodsShopCar record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_shopcar
     *
     * @mbggenerated
     */
    GoodsShopCar selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_shopcar
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(GoodsShopCar record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_shopcar
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(GoodsShopCar record);

    /**
     * 根据userId查询购物车列表
     * @param userId
     * @return
     */
    List<GoodsShopCar> selectAllGoodsShopCarByUserId(@Param("userId")Integer userId,@Param("goodsId")Integer goodsId);

    /**
     * 删除或者清空购物车
     * @param goodIds
     * @return
     */
    Integer deleteBatch(@Param("goodIds")String goodIds,@Param("userId")Integer userId);

    /**
     * 获取购物车中的商品列表
     * @param userId
     * @return
     */
    List<GoodsShopCar> queryShopCarGoodsList(@Param("userId")Integer userId,@Param("goodsId")Integer goodsId);
}