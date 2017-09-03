/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String order_id;
    private String customer_id;
    private BigDecimal price;
    private String status;
    private String description;

}
