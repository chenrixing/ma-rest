package com.ma.rest.service;

import com.ma.rest.base.model.AfterSale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Descriptions
 *
 * @Author施龙飞
 * @date: Created in10:13 2018/8/30 0030
 **/
public interface AfterSaleService {
    Integer insertAfterSale(AfterSale afterSale);
    Integer UpdateAfterSale(AfterSale afterSale);
    AfterSale selectAfterSaleById(Integer orderId);
    Integer deleteByOrderId(Integer orderId);

    /**
     * 查询系统配置
     * @param type
     * @return
     */
    List selectConfigList(Integer type);
    /**
     * 查询系统消息
     * @param type
     * @return
     */
    List selectSystemMsgList(Integer type);

    /**
     * 收入统计
     * @param paramMap
     * @return
     */
    List selectStatisticsInfo(Map<String,Object> paramMap);

    /**
     * 查询商品详情
     * @param paramMap
     * @return
     */
    List selectGoodsList(Map<String,Object> paramMap);
}
