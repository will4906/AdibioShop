/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Customer {

    private String row_id;
    private String customer_id;
    private String telphone;
    private String email;
    private String wechat_id;
    private Date register_time;

}
