package com.willshuhua.adibioshop.entity.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemProduct {

    private String cart_id;
    private String cart_itemid;
    private String product_id;
    private String product_name;
    private BigDecimal unit_price;
    private int quantity;
}
