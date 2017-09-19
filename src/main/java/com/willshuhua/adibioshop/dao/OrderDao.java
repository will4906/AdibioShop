/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.dao;

import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.order.*;
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

    List<OrderEvent> getTimeToCanceledOrders(@Param("customer_id")String customer_id);

    List<OrderPreview> getOrderPreviews(@Param("order_id") String order_id);

    Order getOrderByOrderId(@Param("order_id")String order_id);

    List<OrderEvent> getOrderEventList(Order order);

    List<Map<String, Object>> getOrderInfoDetailList(Map<String, Object> itemMap);

    List<Map<String, Object>> getItemMapList(Order order);

    /**
     * 查询顶部几个全类型订单
     * @param orderQuery
     * @return
     */
    List<MyOrder> getTopServeralOrdersAll(OrderQuery orderQuery);

    /**
     * 根据状态查询顶部几个订单
     * @param orderQuery
     * @return
     */
    List<MyOrder> getTopServeralOrdersByStatus(OrderQuery orderQuery);

    /**
     * 获取正在处理中的头几个订单
     * @param orderQuery
     * @return
     */
    List<MyOrder> getTopServeralOrdersProcessing(OrderQuery orderQuery);

    /**
     * 获取具有起始位的部分全部类型订单
     * @param orderQuery
     * @return
     */
    List<MyOrder> getPartServeralOrdersAll(OrderQuery orderQuery);

    /**
     * 根据状态查询具有起始位的部分订单
     * @param orderQuery
     * @return
     */
    List<MyOrder> getPartServeralOrdersByStatus(OrderQuery orderQuery);

    /**
     * 获取处理中的具有起始位的部分订单
     * @param orderQuery
     * @return
     */
    List<MyOrder> getPartServeralOrdersProcessing(OrderQuery orderQuery);
}
