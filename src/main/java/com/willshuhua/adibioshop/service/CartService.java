package com.willshuhua.adibioshop.service;

import com.willshuhua.adibioshop.entity.cart.CartItem;
import com.willshuhua.adibioshop.entity.cart.ItemProduct;
import com.willshuhua.adibioshop.entity.cart.ShoppingCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartService {

    List<ItemProduct> queryCartForCustomer(String customerId);

    void addCartItemQuantity(String cartItemId);

    void insertCartItem(CartItem cartItem);

    CartItem reduceCartItem(CartItem cartItem);

    ShoppingCart queryShoppingCart(String customer_id);

    void createShoppingCart(ShoppingCart shoppingCart);

    CartItem hasCartItem(String customer_id, String product_id);
}
