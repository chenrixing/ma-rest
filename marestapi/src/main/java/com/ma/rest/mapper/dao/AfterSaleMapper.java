package com.ma.rest.mapper.dao;

import com.ma.rest.base.model.AfterSale;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AfterSaleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ma_aftersale
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(@Param("orderId") Integer orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ma_aftersale
     *
     * @mbggenerated
     */
    int insert(AfterSale record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ma_aftersale
     *
     * @mbggenerated
     */
    int insertSelective(AfterSale record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ma_aftersale
     *
     * @mbggenerated
     */
    AfterSale selectByPrimaryKey(@Param("orderId") Integer orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ma_aftersale
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AfterSale record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ma_aftersale
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(AfterSale record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ma_aftersale
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AfterSale record);

    List selectConfigList(@Param("type")Integer type);

    List selectSystemMsgList(Integer type);

    List selectStatisticsInfo(Map<String,Object> paramMap);

    List selectGoodsList(Map<String,Object> paramMap);
}