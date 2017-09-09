/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.dao;


import com.willshuhua.adibioshop.dto.order.PatientDetail;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.PatientInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerDao{

    void createCustomerAccount(Customer customer);

    Customer queryCustomerByOpenId(@Param("openId") String openId);

    PatientInfo hasPatientInfo(PatientInfo patientInfo);

    PatientInfo hasPatientInfoId(String patient_infoid);

    void createPatientInfo(PatientInfo patientInfo);

    List<PatientInfo> queryAllCustomerPatientInfos(@Param("customer_id") String customer_id);

    void deletePatientInfo(@Param("patient_infoid")String patient_infoid);
}
