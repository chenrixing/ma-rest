package com.ma.rest.service.impl;

import com.ma.rest.base.model.Area;
import com.ma.rest.base.model.City;
import com.ma.rest.base.model.Province;
import com.ma.rest.export.ExportUtil;
import com.ma.rest.mapper.dao.AreaMapperExtra;
import com.ma.rest.mapper.dao.CityMapperExtra;
import com.ma.rest.mapper.dao.ProvinceMapperExtra;
import com.ma.rest.service.AddressManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 李光明
 * @Title: 地址管理
 * @date 2018/6/2 17:30
 */
@Service
public class AddressManagerServiceImpl implements AddressManagerService {
    @Resource
    private ProvinceMapperExtra provinceMapperExtra;
    @Autowired
    private CityMapperExtra cityMapperExtra;
    @Autowired
    private AreaMapperExtra areaMapperExtra;
    @Override
    /**
     * @Author施龙飞
     * 获取所有省份信息
     */
    public List<Province> getProvinceList() {
        return provinceMapperExtra.selectProvinceList();
    }

    /**
     * 获取某个省份下的所有城市
     * @Author施龙飞
     * @param provinceCode
     * @return
     */
    @Override
    public List<City> getCityList(String provinceCode) {
        return cityMapperExtra.selectCityListByProvinceId(provinceCode);
    }

    /**
     * 获取某个城市下的所有区域
     * @Author施龙飞
     * @param cityCode
     * @return
     */
    @Override
    public List<Area> getAreaList(String cityCode) {
        return areaMapperExtra.selectAreaByCityCode(cityCode);
    }

    @Override
    public int addAddress(Map<String, Object> params) {
        return provinceMapperExtra.addAddress(params);
    }

    @Override
    public int updateAddress(Map<String, Object> params) {
        return provinceMapperExtra.updateAddress(params);
    }

    @Override
    public List getAddressList(Integer userId,String isDefault) {
        return provinceMapperExtra.selectAddressList(userId,isDefault);
    }

    @Override
    public Integer deleteAddress(Map<String, Object> params) {
        return provinceMapperExtra.deleteAddress(params);
    }
}
