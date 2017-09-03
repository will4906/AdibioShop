/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    private int row_id;
    private String product_id;
    private String product_groupid;
    private String name;
    private BigDecimal price;
    private String description;

}
