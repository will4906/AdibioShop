/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private String order_id;
    private String order_itemid;
    private String product_id;
    private int quantity;

}
