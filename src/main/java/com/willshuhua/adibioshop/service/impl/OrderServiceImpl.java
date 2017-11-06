/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service.impl;

import com.willshuhua.adibioshop.dao.OrderDao;
import com.willshuhua.adibioshop.define.order.OrderStatus;
import com.willshuhua.adibioshop.define.order.OrderType;
import com.willshuhua.adibioshop.dto.order.ItemDetail;
import com.willshuhua.adibioshop.dto.order.OrderDetail;
import com.willshuhua.adibioshop.entity.order.*;
import com.willshuhua.adibioshop.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    private Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Transactional
    @Override
    public void createOrder(Order order, OrderInfo orderInfo, OrderEvent orderEvent, OrderItem orderItem, OrderPatientInfo orderPatientInfo) {
        orderDao.createOrder(order);
        orderDao.createOrderItem(orderItem);
        orderDao.createOrderEvent(orderEvent);
        orderDao.createOrderInfo(orderInfo);
        orderDao.createOrderPatientInfo(orderPatientInfo);
    }

    @Transactional
    @Override
    public void createOrder(Order order, List<OrderInfo> orderInfoList, OrderEvent orderEvent, List<OrderItem> orderItemList, List<OrderPatientInfo> orderPatientInfoList) {
        orderDao.createOrder(order);
        for (OrderItem orderItem : orderItemList) {
            orderDao.createOrderItem(orderItem);
        }
        orderDao.createOrderEvent(orderEvent);
        for (OrderInfo orderInfo : orderInfoList) {
            orderDao.createOrderInfo(orderInfo);
        }
        for (OrderPatientInfo orderPatientInfo : orderPatientInfoList){
            orderDao.createOrderPatientInfo(orderPatientInfo);
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
    public Order getCustomerOrder(String customerId, String orderId) {
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setCustomer_id(customerId);
        orderQuery.setOrder_id(orderId);
        return orderDao.getCustomerOrder(orderQuery);
    }

    @Override
    public void setOrderPreviewsToMyOrders(List<MyOrder> myOrderList) {
        for (MyOrder myOrder : myOrderList) {
            myOrder.setOrderPreviewList(orderDao.getOrderPreviews(myOrder.getOrder_id()));
        }
    }

    @Override
    public void cancelOrders(String customer_id) {
        List<OrderEvent> orderEventList = orderDao.getTimeToCanceledOrders(customer_id);
        for (OrderEvent orderEvent : orderEventList) {
            OrderEvent orderEvent1 = new OrderEvent(UUID.randomUUID().toString(), orderEvent.getOrder_id(),
                    new Date(orderEvent.getEvent_time().getTime() + 15 * 60 * 1000L), OrderStatus.CANCELED, customer_id, "过时自动取消");
            changeOrderStatus(orderEvent1);
        }
    }

    @Transactional
    @Override
    public OrderDetail getOrderDetail(String order_id) {
        OrderDetail orderDetail = new OrderDetail();
        logger.info(order_id);
        Order order = orderDao.getOrderByOrderId(order_id);
        List<OrderEvent> orderEventList = orderDao.getOrderEventList(order);
        List<Map<String, Object>> itemMapList = orderDao.getItemMapList(order);
        List<ItemDetail> itemDetailList = new ArrayList<>();
        for (Map<String, Object> itemMap : itemMapList){
            ItemDetail itemDetail = new ItemDetail();
            itemDetail.setItemMap(itemMap);
            List<Map<String, Object>> infoMapList = orderDao.getOrderInfoDetailList(itemMap);
            itemDetail.setInfoMapList(infoMapList);
            itemDetailList.add(itemDetail);
        }
        orderDetail.setItemDetailList(itemDetailList);
        orderDetail.setOrder(order);
        orderDetail.setOrderEventList(orderEventList);
        logger.info(orderDetail);
        return orderDetail;
    }

    @Transactional
    @Override
    public List<MyOrder> getTopServeralOrders(OrderQuery orderQuery, String type) {
        switch (type) {
            case OrderType.ALL: {
                cancelOrders(orderQuery.getCustomer_id());
                List<MyOrder> myOrderList = orderDao.getTopServeralOrdersAll(orderQuery);
                setOrderPreviewsToMyOrders(myOrderList);
                return myOrderList;
            }
            case OrderType.UNPAID: {
                orderQuery.setStatus(OrderStatus.CREATION);
                cancelOrders(orderQuery.getCustomer_id());
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
                cancelOrders(orderQuery.getCustomer_id());
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
        switch (type) {
            case OrderType.ALL: {
                cancelOrders(orderQuery.getCustomer_id());
                List<MyOrder> myOrderList = orderDao.getPartServeralOrdersAll(orderQuery);
                setOrderPreviewsToMyOrders(myOrderList);
                return myOrderList;
            }
            case OrderType.UNPAID: {
                orderQuery.setStatus(OrderStatus.CREATION);
                cancelOrders(orderQuery.getCustomer_id());
                List<MyOrder> myOrderList = orderDao.getPartServeralOrdersByStatus(orderQuery);
                setOrderPreviewsToMyOrders(myOrderList);
                return myOrderList;
            }
            case OrderType.PROCESSING:{
                List<MyOrder> myOrderList = orderDao.getPartServeralOrdersProcessing(orderQuery);
                setOrderPreviewsToMyOrders(myOrderList);
                return myOrderList;
            }
            case OrderType.FINISHED:
                break;
            case OrderType.CANCELED:{
                orderQuery.setStatus(OrderStatus.CANCELED);
                cancelOrders(orderQuery.getCustomer_id());
                List<MyOrder> myOrderList = orderDao.getPartServeralOrdersByStatus(orderQuery);
                setOrderPreviewsToMyOrders(myOrderList);
                return myOrderList;
            }
            default:
                return null;
        }
        return null;
    }

    @Override
    public void cancelOrderByOrderId(String customer_id, String orderId, String whyCancel) throws Exception {
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setCustomer_id(customer_id);
        orderQuery.setOrder_id(orderId);
        Order order = orderDao.getCustomerOrder(orderQuery);
        if (order == null){
            throw new Exception("Can't bind the order!");
        }
        changeOrderStatus(new OrderEvent(UUID.randomUUID().toString(), orderId, new Date(), "CANCELED", order.getCustomer_id(), whyCancel));
    }
}
