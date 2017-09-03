/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service;

import com.willshuhua.adibioshop.entity.order.Order;
import com.willshuhua.adibioshop.entity.order.OrderEvent;
import com.willshuhua.adibioshop.entity.order.OrderInfo;
import com.willshuhua.adibioshop.entity.order.OrderItem;

public interface OrderService {

    void createOrder(Order order, OrderInfo orderInfo, OrderEvent orderEvent, OrderItem orderItem);

    void changeOrderStatus(OrderEvent orderEvent);

}
