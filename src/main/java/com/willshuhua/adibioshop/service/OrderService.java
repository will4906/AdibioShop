/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service;

import com.willshuhua.adibioshop.entity.order.Order;
import com.willshuhua.adibioshop.entity.order.OrderEvent;
import com.willshuhua.adibioshop.entity.order.OrderInfo;
import com.willshuhua.adibioshop.entity.order.OrderItem;

import java.util.List;
import java.util.Map;

public interface OrderService {

    void createOrder(Order order, OrderInfo orderInfo, OrderEvent orderEvent, OrderItem orderItem);

    void changeOrderStatus(OrderEvent orderEvent);

    List<Map<String,Object>> queryOrderInfoByOrderId(String orderId);

}
