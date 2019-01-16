package com.ma.rest.service.impl;


import com.ma.rest.base.model.Goods;
import com.ma.rest.base.model.Order;
import com.ma.rest.base.model.RelOderGoods;
import com.ma.rest.base.model.Serial;
import com.ma.rest.mapper.dao.GoodsShopCarMapper;
import com.ma.rest.mapper.dao.RelOderGoodsMapper;
import com.ma.rest.service.GoodsService;
import com.ma.rest.service.OrderService;
import com.ma.rest.service.RelOrderGoodsService;
import com.ma.rest.service.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Descriptions
 *
 * @Author施龙飞
 * @date: Created in10:21 2018/7/20 0020
 **/
@Service
@Transactional
public class RelOrderGoodsServiceImpl implements RelOrderGoodsService {
    @Autowired
    private RelOderGoodsMapper relOderGoodsMapper;

    @Autowired
    private SerialService serialService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsShopCarMapper goodsShopCarMapper;


    @Override
    public Map<String,Object> insertRelOrderGoods(String goodsIds, String goodsNumbers, Integer userId, Integer addressId){
        Map<String,Object> map=new HashMap<String, Object>();
        Order order=new Order();
        String[] goodIds = goodsIds.split(",");
        String[] goodNumbers=goodsNumbers.split(",");
        List<String> strings = Arrays.asList(goodIds);
        List<String> strings1 = Arrays.asList(goodNumbers);
        Integer integer=0;
        int result=0;
        StringBuilder sb=new StringBuilder();
        BigDecimal sumPrice1=BigDecimal.ZERO;
        for(int i=0;i<strings.size();i++){
            BigDecimal sumPrice=BigDecimal.ZERO;
            Goods goods = goodsService.selectgoodsInfoById(Integer.parseInt(strings.get(i)));
            sumPrice=sumPrice.add(goods.getPrice().multiply(new BigDecimal(strings1.get(i))));
            sumPrice1=sumPrice1.add(goods.getPrice().multiply(new BigDecimal(strings1.get(i))));
            //保留两位小数
            order.setPayPrice(sumPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
            order.setUid(userId);
            order.setStatus("0");
            order.setCreateTime(new Date());
            order.setAddressId(addressId);
            Serial serial = serialService.selectSerial();
            int maxNum=0;
            if(serial!=null) {
                maxNum=serial.getSerialNumber();
            }
            String serialNumber = serialService.getSerialNumber(maxNum);
            if(i==0){
                sb.append(serialNumber).append(",");
            }else if(i!=strings.size()-1){
                sb.append(serialNumber).append(",");
            }else{
                sb.append(serialNumber);
            }
            order.setSellerUid(goods.getUserId());
            order.setOrderNumber(serialNumber);
            order.setInvoiceInfo("暂无发票信息");
            order.setGoodsOtherInfo("此商品妥善包管");
            order.setExpressStatus("0");
            order.setSellerUid(goods.getUserId());
            integer = orderService.addOrder(order);
            if(integer>0){
                //为了获取订单Id
                Order order1 = orderService.selectByOrderNumber(serialNumber);
                RelOderGoods relOderGoods=new RelOderGoods();
                relOderGoods.setOrderId(order1.getId());
                relOderGoods.setGoodsId(Integer.parseInt(strings.get(i)));
                relOderGoods.setGoodsNumber(Integer.parseInt(strings1.get(i)));
                result=relOderGoodsMapper.insertSelective(relOderGoods);
                if(result==0){
                    throw new RuntimeException("生成订单失败！");
                }else{
                    goodsShopCarMapper.deleteBatch(goodsIds,userId);
                }
            }else{
                result=0;
            }
        }
        Serial serial1 = serialService.selectSerial();
        int maxNum1=0;
        if(serial1!=null) {
            maxNum1=serial1.getSerialNumber();
        }
        String serialNumber = serialService.getSerialNumber(maxNum1);
        map.put("orderNumber",serialNumber);
        map.put("allOrderNumber",sb.toString());
        map.put("toralPrice",sumPrice1);
        map.put("result",result);
        return map;


    }
}
