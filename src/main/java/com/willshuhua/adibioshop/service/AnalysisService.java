package com.willshuhua.adibioshop.service;

import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.order.Order;

import java.util.Map;

public interface AnalysisService {

    Map<String, Object> getAnalysisInfo(String analysisId);

    Map<String, Object> getAnalysisInfoByOrderInfo(String orderInfoid);

    Customer selectCustomerByOrderInfoId(String orderInfoid);

    Order selectOrderByAnalysisId(String analysisId);
}
