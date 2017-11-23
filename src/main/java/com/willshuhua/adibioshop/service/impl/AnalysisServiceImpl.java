package com.willshuhua.adibioshop.service.impl;

import com.willshuhua.adibioshop.dao.AnalysisDao;
import com.willshuhua.adibioshop.dao.CustomerDao;
import com.willshuhua.adibioshop.dao.OrderDao;
import com.willshuhua.adibioshop.dao.ProductDao;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.analysis.Analysis;
import com.willshuhua.adibioshop.entity.order.Order;
import com.willshuhua.adibioshop.entity.order.OrderInfo;
import com.willshuhua.adibioshop.entity.order.OrderPatientInfo;
import com.willshuhua.adibioshop.entity.product.Product;
import com.willshuhua.adibioshop.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class AnalysisServiceImpl implements AnalysisService{

    @Autowired
    AnalysisDao analysisDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    CustomerDao customerDao;

    @Transactional
    @Override
    public Map<String, Object> getAnalysisInfo(String analysisId) {
        Analysis analysis = analysisDao.selectAnalysis(analysisId);
        if (analysis == null){
            return null;
        }
        Map<String, Object> analysisInfo = new HashMap<>();
        analysisInfo.put("analysis", analysis);
        OrderInfo orderInfo = orderDao.selectOrderInfo(analysis.getOrder_infoid());
        Product product = productDao.queryProductByProductId(orderInfo.getProduct_id());
        analysisInfo.put("product_name", product.getProduct_name());
        OrderPatientInfo orderPatientInfo = customerDao.selectOrderPatientInfo(orderInfo.getOrder_infoid());
        analysisInfo.put("patient_info", orderPatientInfo);
        Customer customer = orderDao.selectCustomerByOrderInfo(orderInfo.getOrder_infoid());
        analysisInfo.put("customer", customer);
        return analysisInfo;
    }

    @Override
    public Customer selectCustomerByOrderInfoId(String orderInfoid) {
        return analysisDao.selectCustomerByOrderInfoId(orderInfoid);
    }

    @Override
    public Order selectOrderByAnalysisId(String analysisId) {
        return analysisDao.selectOrderByAnalysisId(analysisId);
    }

    @Transactional
    @Override
    public Map<String, Object> getAnalysisInfoByOrderInfo(String orderInfoid) {
        Analysis analysis = analysisDao.selectAnalysisByOrderInfoId(orderInfoid);
        if (analysis == null){
            return null;
        }
        Map<String, Object> analysisInfo = new HashMap<>();
        analysisInfo.put("analysis", analysis);
        OrderInfo orderInfo = orderDao.selectOrderInfo(orderInfoid);

        Product product = productDao.queryProductByProductId(orderInfo.getProduct_id());
        analysisInfo.put("product_name", product.getProduct_name());
        OrderPatientInfo orderPatientInfo = customerDao.selectOrderPatientInfo(orderInfoid);
        analysisInfo.put("patient_info", orderPatientInfo);

        return analysisInfo;
    }
}
