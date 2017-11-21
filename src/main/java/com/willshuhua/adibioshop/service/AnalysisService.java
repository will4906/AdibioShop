package com.willshuhua.adibioshop.service;

import com.willshuhua.adibioshop.entity.Customer;

import java.util.Map;

public interface AnalysisService {

    Map<String, Object> getAnalysisInfo(String analysisId);

    Map<String, Object> getAnalysisInfoByOrderInfo(String orderInfoid);

    Customer selectCustomerByOrderInfoId(String orderInfoid);
}
