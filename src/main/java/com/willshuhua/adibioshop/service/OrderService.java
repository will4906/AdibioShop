/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service;

import com.willshuhua.adibioshop.entity.order.*;

import java.util.List;
import java.util.Map;

public interface OrderService {

    void createOrder(Order order, OrderInfo orderInfo, OrderEvent orderEvent, OrderItem orderItem);

    void createOrder(Order order, List<OrderInfo> orderInfoList, OrderEvent orderEvent, List<OrderItem> orderItemList);

    void changeOrderStatus(OrderEvent orderEvent);

    List<Map<String,Object>> queryOrderInfoByOrderId(String orderId);

    void setOrderPreviewsToMyOrders(List<MyOrder> myOrderList);

    void cancelOrders(String customer_id);

    /**
     * 获取头几个订单
     * @param orderQuery
     * @param type
     * @return
     */
    List<MyOrder> getTopServeralOrders(OrderQuery orderQuery, String type);

    List<MyOrder> getPartServeralOrders(OrderQuery orderQuery, String type);


}
