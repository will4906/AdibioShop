package com.willshuhua.adibioshop.entity.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {

    private String customer_id;
    private String cart_id;
}
