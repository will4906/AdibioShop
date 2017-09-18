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
    /**
     * 查询顶部几个全类型订单
     * @param orderQuery
     * @return
     */
    List<MyOrder> getTopServeralOrdersAll(OrderQuery orderQuery);

    /**
     * 查询顶部几个未支付类型订单
     * @param orderQuery
     * @return
     */
    List<MyOrder> getTopServeralOrdersUnpaid(OrderQuery orderQuery);
}
