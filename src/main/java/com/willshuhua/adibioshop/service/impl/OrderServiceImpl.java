/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service.impl;

import com.willshuhua.adibioshop.dao.OrderDao;
import com.willshuhua.adibioshop.entity.order.Order;
import com.willshuhua.adibioshop.entity.order.OrderEvent;
import com.willshuhua.adibioshop.entity.order.OrderInfo;
import com.willshuhua.adibioshop.entity.order.OrderItem;
import com.willshuhua.adibioshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Transactional
    @Override
    public void createOrder(Order order, OrderInfo orderInfo, OrderEvent orderEvent, OrderItem orderItem) {
        orderDao.createOrder(order);
        orderDao.createOrderItem(orderItem);
        orderDao.createOrderEvent(orderEvent);
        orderDao.createOrderInfo(orderInfo);
    }

    @Transactional
    @Override
    public void changeOrderStatus(OrderEvent orderEvent) {
        orderDao.createOrderEvent(orderEvent);
        orderDao.updateOrderStatus(orderEvent.getOrder_id(), orderEvent.getEvent_title());
    }

    @Override
    public List<Map<String,Object>> queryOrderInfoByOrderId(String orderId) {
        return orderDao.queryOrderInfoByOrderId(orderId);
    }
}
