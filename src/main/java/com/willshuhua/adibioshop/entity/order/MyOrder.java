package com.willshuhua.adibioshop.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyOrder {

    private long row_id;
    private String customer_id;
    private String order_id;
    private BigDecimal price;
    private String status;
    private String description;

    private String order_itemid;
    private String product_id;
    private int quantity;

    private String product_name;
}
