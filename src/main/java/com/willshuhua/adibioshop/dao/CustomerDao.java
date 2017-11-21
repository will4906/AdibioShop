/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.dao;


import com.willshuhua.adibioshop.dto.order.PatientDetail;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.CustomerWechat;
import com.willshuhua.adibioshop.entity.PatientInfo;
import com.willshuhua.adibioshop.entity.order.OrderPatientInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDao{

    void createCustomerAccount(Customer customer);

    Customer queryCustomerByOpenId(@Param("openId") String openId);

    PatientInfo hasPatientInfo(PatientInfo patientInfo);

    PatientInfo hasPatientInfoId(String patient_infoid);

    void createPatientInfo(PatientInfo patientInfo);

    List<PatientInfo> queryAllCustomerPatientInfos(@Param("customer_id") String customer_id);

    void deletePatientInfo(@Param("patient_infoid")String patient_infoid);

    void createCustomerWechat(CustomerWechat customerWechat);

    void updateCustomerWechat(CustomerWechat customerWechat);

    CustomerWechat getCustomerWechat(@Param("customer_id")String customer_id);

    void updatePatientInfo(PatientInfo patientInfo);

    void updateCashbackInfo(Customer customer);

    OrderPatientInfo selectOrderPatientInfo(@Param("order_infoid")String orderInfoid);
}
