/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service.impl;

import com.willshuhua.adibioshop.dao.OrderDao;
import com.willshuhua.adibioshop.define.order.OrderStatus;
import com.willshuhua.adibioshop.define.order.OrderType;
import com.willshuhua.adibioshop.entity.order.*;
import com.willshuhua.adibioshop.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    private Logger logger = Logger.getLogger(OrderServiceImpl.class);

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
    public void createOrder(Order order, List<OrderInfo> orderInfoList, OrderEvent orderEvent, List<OrderItem> orderItemList) {
        orderDao.createOrder(order);
        for (OrderItem orderItem : orderItemList) {
            orderDao.createOrderItem(orderItem);
        }
        orderDao.createOrderEvent(orderEvent);
        for (OrderInfo orderInfo : orderInfoList) {
            orderDao.createOrderInfo(orderInfo);
        }
    }

    @Transactional
    @Override
    public void changeOrderStatus(OrderEvent orderEvent) {
        orderDao.createOrderEvent(orderEvent);
        orderDao.updateOrderStatus(orderEvent.getOrder_id(), orderEvent.getEvent_title());
    }

    @Override
    public List<Map<String, Object>> queryOrderInfoByOrderId(String orderId) {
        return orderDao.queryOrderInfoByOrderId(orderId);
    }

    @Override
    public void setOrderPreviewsToMyOrders(List<MyOrder> myOrderList) {
        for (MyOrder myOrder : myOrderList) {
            myOrder.setOrderPreviewList(orderDao.getOrderPreviews(myOrder.getOrder_id()));
        }
    }

    @Transactional
    @Override
    public List<MyOrder> getTopServeralOrders(OrderQuery orderQuery, String type) {
        switch (type) {
            case OrderType.ALL: {
                List<MyOrder> myOrderList = orderDao.getTopServeralOrdersAll(orderQuery);
                setOrderPreviewsToMyOrders(myOrderList);
                return myOrderList;
            }
            case OrderType.UNPAID: {
                orderQuery.setStatus(OrderStatus.CREATION);
                List<OrderEvent> orderEventList = orderDao.getTimeToCanceledOrders(orderQuery.getCustomer_id());
                for (OrderEvent orderEvent : orderEventList) {
                    OrderEvent orderEvent1 = new OrderEvent(UUID.randomUUID().toString(), orderEvent.getOrder_id(),
                            new Date(orderEvent.getEvent_time().getTime() + 15 * 60 * 1000L), OrderStatus.CANCELED, orderQuery.getCustomer_id(), null);
                    changeOrderStatus(orderEvent1);
                }
                List<MyOrder> myOrderList = orderDao.getTopServeralOrdersByStatus(orderQuery);
                setOrderPreviewsToMyOrders(myOrderList);
                return myOrderList;
            }
            case OrderType.PROCESSING:{
                List<MyOrder> myOrderList = orderDao.getTopServeralOrdersProcessing(orderQuery);
                setOrderPreviewsToMyOrders(myOrderList);
                return myOrderList;
            }
            case OrderType.FINISHED:
                break;
            case OrderType.CANCELED:{
                orderQuery.setStatus(OrderStatus.CANCELED);
                List<OrderEvent> orderEventList = orderDao.getTimeToCanceledOrders(orderQuery.getCustomer_id());
                logger.info("orderEventList==-==" + orderEventList);
                for (OrderEvent orderEvent : orderEventList) {
                    OrderEvent orderEvent1 = new OrderEvent(UUID.randomUUID().toString(), orderEvent.getOrder_id(),
                            new Date(orderEvent.getEvent_time().getTime() + 15 * 60 * 1000L), OrderStatus.CANCELED, orderQuery.getCustomer_id(), null);
                    changeOrderStatus(orderEvent1);
                }
                List<MyOrder> myOrderList = orderDao.getTopServeralOrdersByStatus(orderQuery);
                setOrderPreviewsToMyOrders(myOrderList);
                return myOrderList;
            }
            default:
                return null;
        }
        return null;
    }

    @Override
    public List<MyOrder> getPartServeralOrders(OrderQuery orderQuery, String type) {
        return null;
    }
}
