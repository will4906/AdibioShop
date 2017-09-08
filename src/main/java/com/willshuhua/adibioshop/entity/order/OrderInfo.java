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
public class OrderInfo {

    private String order_itemid;
    private String order_infoid;
    private String product_id;
    private String patient_infoid;

}
