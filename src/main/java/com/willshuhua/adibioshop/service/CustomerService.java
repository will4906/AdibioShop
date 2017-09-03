/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service;

import com.willshuhua.adibioshop.entity.Customer;

/**
 * Created by will on 2017/8/19.
 */
public interface CustomerService {

    void createCustomerAccount(Customer customer);

    Customer queryCustomerByOpenId(String openId);
}
