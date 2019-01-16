package com.ma.rest.mapper.dao;

import com.ma.rest.base.model.Area;
import com.ma.rest.base.model.City;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  获取某个城市下的所有区域Mapper
 * @Author 施龙飞
 * @date 2018/6/4
 */
public interface AreaMapperExtra {
    /**
     * 获取某个城市下的所有区域信息
     * @return
     */
    List<Area> selectAreaByCityCode(@Param("cityCode") String cityCode);
}
