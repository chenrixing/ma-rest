package com.ma.rest.service.impl;



import com.ma.rest.base.model.Order;
import com.ma.rest.mapper.dao.OrderMapper;
import com.ma.rest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Descriptions
 *
 * @Author施龙飞
 * @date: Created in15:06 2018/7/7 0007
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public Integer addOrder(Order order) {
        return orderMapper.insertSelective(order);
    }

    @Override
    public Integer updateOrder(Order order) {
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Integer cancelOrder(Integer orderId) {
        return orderMapper.deleteByKey(orderId);
    }

    @Override
    public List<Order> getAllOrder(Integer userId, Integer start, Integer rows,String status) {
        return orderMapper.selectOrderList(userId,start,rows,status);
    }

    @Override
    public List<Order> getSellerAllOrder(Integer sellerUid, Integer start, Integer rows, String status,String beginTime,String endTime) {
        return orderMapper.selectSellerOrderList(sellerUid,start,rows,status,beginTime,endTime);
    }

    @Override
    public Integer queryAllRows(Integer userId,String status) {
        return orderMapper.queryAllRows(userId,status);
    }

    @Override
    public Integer deleteBatch(String orderIds) {
        return orderMapper.deleteBatch(orderIds);
    }

    @Override
    public Order selectByOrderNumber(String orderNumber) {
        return orderMapper.selectByOrderNumber(orderNumber);
    }

    @Override
    public Order selectOrderDetailById(Integer userId, Integer orderId) {
        return orderMapper.selectOrderDetailById(userId,orderId);
    }
}
