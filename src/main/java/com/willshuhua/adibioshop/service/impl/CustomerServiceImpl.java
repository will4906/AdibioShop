/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service.impl;

import com.willshuhua.adibioshop.dao.CustomerDao;
import com.willshuhua.adibioshop.dto.order.PatientDetail;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.PatientInfo;
import com.willshuhua.adibioshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by will on 2017/8/19.
 */
@Service
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    private CustomerDao customerDao;

    @Override
    public void createCustomerAccount(Customer customer) {
        customerDao.createCustomerAccount(customer);
    }

    @Override
    public Customer queryCustomerByOpenId(String openId) {
        return customerDao.queryCustomerByOpenId(openId);
    }

    @Override
    public PatientInfo hasPatientInfo(PatientInfo patientInfo) {
        return customerDao.hasPatientInfo(patientInfo);
    }

    @Override
    public PatientInfo hasPatientInfoId(String patientInfoId) {
        return customerDao.hasPatientInfoId(patientInfoId);
    }

    @Override
    public void createPatientInfo(PatientInfo patientInfo) {
        customerDao.createPatientInfo(patientInfo);
    }

    @Override
    public List<PatientInfo> queryAllCustomerPatientInfos(String customer_id) {
        return customerDao.queryAllCustomerPatientInfos(customer_id);
    }

    @Override
    public void deletePatientInfo(String patient_infoid) {
        customerDao.deletePatientInfo(patient_infoid);
    }

}
