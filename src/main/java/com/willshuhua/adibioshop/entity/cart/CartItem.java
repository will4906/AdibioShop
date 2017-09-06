package com.willshuhua.adibioshop.entity.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private String cart_id;
    private String cart_itemid;
    private String product_id;
    private int quantity;

}
