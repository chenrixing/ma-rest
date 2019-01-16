package com.ma.rest.service.impl;

import com.ma.rest.base.model.AfterSale;
import com.ma.rest.mapper.dao.AfterSaleMapper;
import com.ma.rest.service.AfterSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Descriptions
 *
 * @Author施龙飞
 * @date: Created in10:14 2018/8/30 0030
 **/
@Service
@Transactional
public class AfterSaleServiceImpl implements AfterSaleService {
    @Autowired
    private AfterSaleMapper afterSaleMapper;
    @Override
    public Integer insertAfterSale(AfterSale afterSale) {
        return afterSaleMapper.insertSelective(afterSale);
    }

    @Override
    public Integer UpdateAfterSale(AfterSale afterSale) {
        return afterSaleMapper.updateByPrimaryKeySelective(afterSale);
    }

    @Override
    public AfterSale selectAfterSaleById(Integer orderId) {
        return afterSaleMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public Integer deleteByOrderId(Integer orderId) {
        return afterSaleMapper.deleteByPrimaryKey(orderId);
    }

    @Override
    public List selectConfigList(Integer type) {
          return afterSaleMapper.selectConfigList(type);
    }

    @Override
    public List selectSystemMsgList(Integer type) {
        return afterSaleMapper.selectSystemMsgList(type);
    }

    @Override
    public List selectStatisticsInfo(Map<String,Object> paramMap) {
        return afterSaleMapper.selectStatisticsInfo(paramMap);
    }

    @Override
    public List selectGoodsList(Map<String, Object> paramMap) {
        return afterSaleMapper.selectGoodsList(paramMap);
    }
}
