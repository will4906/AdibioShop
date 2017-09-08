/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.dao;


import com.willshuhua.adibioshop.dto.order.PatientDetail;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.PatientInfo;
import org.apache.ibatis.annotations.Param;

public interface CustomerDao{

    void createCustomerAccount(Customer customer);

    Customer queryCustomerByOpenId(@Param("openId") String openId);

    PatientInfo hasPatientInfo(PatientInfo patientInfo);

    void createPatientInfo(PatientInfo patientInfo);
}
