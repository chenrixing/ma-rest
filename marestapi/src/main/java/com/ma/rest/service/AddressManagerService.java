package com.ma.rest.service;

import com.ma.rest.base.model.Area;
import com.ma.rest.base.model.City;
import com.ma.rest.base.model.Province;

import java.util.List;
import java.util.Map;

/**
 * @author 李光明
 * @Title: 地址管理
 * @date 2018/6/21 7:25
 */
public interface AddressManagerService{
    /**
     * 获取所有省份
     * @return
     * @Author 施龙飞
     * @date 2018/6/4
     */
    List<Province> getProvinceList();

    /**
     * 获取省份下的城市信息
     * @Author施龙飞
     * @param provinceCode
     * @return
     */
    List<City> getCityList(String provinceCode);
    /**
     * 获取城市下的区域信息
     * @Author施龙飞
     * @param cityCode
     * @return
     */
    List<Area> getAreaList(String cityCode);

    /**
     * 添加收获地址
     * @param params
     * @return
     */
    int addAddress(Map<String,Object> params);

    /**
     * 修改收貨地址
     * @param params
     * @return
     */
    int updateAddress(Map<String,Object> params);

    List getAddressList(Integer userId,String isDefault);
    Integer deleteAddress(Map<String,Object> params);


}
