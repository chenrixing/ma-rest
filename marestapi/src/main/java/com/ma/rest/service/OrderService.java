package com.ma.rest.service;



import com.ma.rest.base.model.Order;
import org.aspectj.weaver.ast.Or;

import java.util.List;

/**
 * Descriptions
 *订单业务
 * @Author施龙飞
 * @date: Created in14:58 2018/7/7 0007
 **/
public interface OrderService {
    /**
     * 生成订单
     * @param order
     * @return
     */
    Integer addOrder(Order order);

    /**
     * 修改订单
     * @param order
     * @return
     */
    Integer updateOrder(Order order);

    /**
     *取消订单
     * @param orderId
     * @return
     */
    Integer cancelOrder(Integer orderId);

    /**
     * 订单列表
     * @param userId
     * @return
     */
    List<Order> getAllOrder(Integer userId, Integer start, Integer rows,String status);

    /**
     * 商家消费列表
     * @param sellerUid
     * @param start
     * @param rows
     * @param status
     * @return
     */
    List<Order> getSellerAllOrder(Integer sellerUid, Integer start, Integer rows,String status,String beginTime,String endTime);

    /**
     * 总条数
     * @return
     */
    Integer queryAllRows(Integer userId,String status);
    Integer deleteBatch(String orderIds);
    Order selectByOrderNumber(String orderNumber);
    Order selectOrderDetailById(Integer userId,Integer orderId);
}
