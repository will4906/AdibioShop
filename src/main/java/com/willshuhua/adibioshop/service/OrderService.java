/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service;

import com.willshuhua.adibioshop.dto.order.OrderDetail;
import com.willshuhua.adibioshop.entity.order.*;

import java.util.List;
import java.util.Map;

public interface OrderService {

    void createOrder(Order order, OrderInfo orderInfo, OrderEvent orderEvent, OrderItem orderItem, OrderPatientInfo orderPatientInfo);

    void createOrder(Order order, List<OrderInfo> orderInfoList, OrderEvent orderEvent, List<OrderItem> orderItemList, List<OrderPatientInfo> orderPatientInfoList);

    void changeOrderStatus(OrderEvent orderEvent);

    List<Map<String,Object>> queryOrderInfoByOrderId(String orderId);

    public Order getCustomerOrder(String customerId, String orderId);

    void setOrderPreviewsToMyOrders(List<MyOrder> myOrderList);

    void cancelOrders(String customer_id);

    OrderDetail getOrderDetail(String order_id);
    /**
     * 获取头几个订单
     * @param orderQuery
     * @param type
     * @return
     */
    List<MyOrder> getTopServeralOrders(OrderQuery orderQuery, String type);

    /**
     * 获取部分订单，和以上函数结合形成分页
     * @param orderQuery
     * @param type
     * @return
     */
    List<MyOrder> getPartServeralOrders(OrderQuery orderQuery, String type);

    void cancelOrderByOrderId(String customer_id, String orderId, String whyCancel) throws Exception;
}
