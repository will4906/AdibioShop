package com.willshuhua.adibioshop.entity.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private String cart_id;
    private String cart_itemid;
    private String product_id;
    private int quantity;
    private BigDecimal whole_price;

    public CartItem(String cart_id, String cart_itemid, String product_id, int quantity){
        this.cart_id = cart_id;
        this.cart_itemid = cart_itemid;
        this.product_id = product_id;
        this.quantity = quantity;
    }

}
