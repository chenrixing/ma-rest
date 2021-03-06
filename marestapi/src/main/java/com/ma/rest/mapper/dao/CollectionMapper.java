package com.ma.rest.mapper.dao;

import com.ma.rest.base.model.Collection;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ma_order
     *删除关注信息
     * @mbggenerated
     */
    int deleteByKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ma_order
     *添加关注信息
     * @mbggenerated
     */
    int insert(Collection collection);
    /**
     * 查询用户的收藏信息列表
     * @return
     */
    List<Collection> selectCollectionList(@Param("userId") Long userId);
    /**
     * 查询用户的收藏信息列表
     * @return
     */
    List<Collection> selectFollowList(@Param("userId") Long userId);


}