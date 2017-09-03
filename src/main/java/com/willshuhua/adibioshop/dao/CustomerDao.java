/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.dao;


import com.willshuhua.adibioshop.entity.Customer;
import org.apache.ibatis.annotations.Param;

public interface CustomerDao{

    void createCustomerAccount(Customer customer);

    Customer queryCustomerByOpenId(@Param("openId") String openId);

}
