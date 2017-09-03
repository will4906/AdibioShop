/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service.impl;

import com.willshuhua.adibioshop.dao.CustomerDao;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
