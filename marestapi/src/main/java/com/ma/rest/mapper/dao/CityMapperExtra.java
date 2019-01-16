package com.ma.rest.mapper.dao;

import com.ma.rest.base.model.City;
import com.ma.rest.base.model.Province;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  获取某个省份下的所有城市Mapper
 * @Author 施龙飞
 * @date 2018/6/4
 */
public interface CityMapperExtra {
    /**
     * 获取某个省份下的所有城市
     * @return
     */
    List<City> selectCityListByProvinceId(@Param("provinceCode") String provinceCode);
}
