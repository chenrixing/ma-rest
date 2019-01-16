package com.ma.rest.mapper.dao;

import com.ma.rest.base.model.Province;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 获取省份信息Mapper
 * @Author 施龙飞
 * @date 2018/6/4
 */
public interface ProvinceMapperExtra{
    /**
     * 获取所有省份
     * @return
     */
    List<Province> selectProvinceList();

    int addAddress(Map<String,Object> params);

    int updateAddress(Map<String,Object> params);

    List selectAddressList(@Param("userId") Integer userId, @Param("isDefault") String isDefault);
    Integer deleteAddress(Map<String,Object> params);
}
