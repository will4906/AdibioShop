package com.willshuhua.adibioshop.service.impl;

import com.willshuhua.adibioshop.dao.CartDao;
import com.willshuhua.adibioshop.entity.cart.CartItem;
import com.willshuhua.adibioshop.entity.cart.ItemProduct;
import com.willshuhua.adibioshop.entity.cart.ShoppingCart;
import com.willshuhua.adibioshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartDao cartDao;

    @Override
    public List<ItemProduct> queryCartForCustomer(String customerId) {
        return cartDao.queryCartForCustomer(customerId);
    }

    @Override
    public void addCartItemQuantity(String cartItemId) {
        cartDao.updateCartItemQuantity(cartItemId, 1);
    }

    @Override
    public void insertCartItem(CartItem cartItem) {
        cartDao.insertCartItem(cartItem);
    }

    @Override
    public CartItem reduceCartItem(CartItem cartItem) {
        int quantity = cartItem.getQuantity();
        if (quantity > 1){
            cartDao.updateCartItemQuantity(cartItem.getCart_itemid(), quantity - 1);
        }else {
            cartDao.deleteCartItem(cartItem);
        }
        cartItem.setQuantity(quantity - 1);
        return cartItem;
    }

    @Override
    public ShoppingCart queryShoppingCart(String customer_id) {
        return cartDao.queryShoppingCart(customer_id);
    }

    @Override
    public void createShoppingCart(ShoppingCart shoppingCart) {
        cartDao.createShoppingCart(shoppingCart);
    }

    @Override
    public CartItem hasCartItem(String customer_id, String product_id) {
        return cartDao.hasCartItem(customer_id, product_id);
    }
}
