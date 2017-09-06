package com.willshuhua.adibioshop.service.impl;

import com.willshuhua.adibioshop.dao.CartDao;
import com.willshuhua.adibioshop.entity.cart.CartItem;
import com.willshuhua.adibioshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CartServiceImpl implements CartService{

    @Autowired
    CartDao cartDao;

    @Override
    public List<CartItem> queryCartForCustomer(String customerId) {
        return cartDao.queryCartForCustomer(customerId);
    }
}
