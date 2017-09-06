package com.willshuhua.adibioshop.service;

import com.willshuhua.adibioshop.entity.cart.CartItem;

import java.util.List;

public interface CartService {

    List<CartItem> queryCartForCustomer(String customerId);
}
