/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service;

import com.willshuhua.adibioshop.dto.order.PatientDetail;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.PatientInfo;

/**
 * Created by will on 2017/8/19.
 */
public interface CustomerService {

    void createCustomerAccount(Customer customer);

    Customer queryCustomerByOpenId(String openId);

    PatientInfo hasPatientInfo(PatientInfo patientInfo);

    void createPatientInfo(PatientInfo patientInfo);
}
