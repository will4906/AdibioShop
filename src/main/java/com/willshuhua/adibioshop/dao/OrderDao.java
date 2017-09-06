/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.dao;

import com.willshuhua.adibioshop.entity.order.Order;
import com.willshuhua.adibioshop.entity.order.OrderEvent;
import com.willshuhua.adibioshop.entity.order.OrderInfo;
import com.willshuhua.adibioshop.entity.order.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderDao {

    void createOrder(Order order);

    void createOrderInfo(OrderInfo orderInfo);

    void createOrderEvent(OrderEvent orderEvent);

    void createOrderItem(OrderItem orderItem);

    void updateOrderStatus(@Param("orderId") String orderId, @Param("status") String status);

    List<Map<String,Object>> queryOrderInfoByOrderId(@Param("orderId") String orderId);
}
