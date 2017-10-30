/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service;

import com.willshuhua.adibioshop.dto.order.PatientDetail;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.CustomerWechat;
import com.willshuhua.adibioshop.entity.PatientInfo;

import java.util.List;

/**
 * Created by will on 2017/8/19.
 */
public interface CustomerService {

    void createCustomerAccount(Customer customer);

    Customer queryCustomerByOpenId(String openId);

    PatientInfo hasPatientInfo(PatientInfo patientInfo);

    PatientInfo hasPatientInfoId(String patientInfoId);

    boolean createPatientInfo(PatientInfo patientInfo);

    List<PatientInfo> queryAllCustomerPatientInfos(String customer_id);

    void deletePatientInfo(String patient_infoid);

    void addOrUpdateWechatInfo(CustomerWechat customerWechat);

    CustomerWechat getCustomerWechat(String customer_id);

    void updatePatientInfo(PatientInfo patientInfo);

    void updateCashbackInfo(Customer customer);
}
