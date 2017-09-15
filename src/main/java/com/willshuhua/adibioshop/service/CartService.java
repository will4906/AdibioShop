package com.willshuhua.adibioshop.service;

import com.willshuhua.adibioshop.dto.cart.AddCartItem;
import com.willshuhua.adibioshop.entity.PatientInfo;
import com.willshuhua.adibioshop.entity.cart.CartItem;
import com.willshuhua.adibioshop.entity.cart.CartPatientInfo;
import com.willshuhua.adibioshop.entity.cart.ItemProduct;
import com.willshuhua.adibioshop.entity.cart.ShoppingCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CartService {

    List<ItemProduct> queryCartForCustomer(String customerId);

    void addCartItemQuantity(String cartItemId);

    CartItem addCartItem(AddCartItem addCartItem);

    CartItem reduceCartItem(CartPatientInfo cartPatientInfo);

    ShoppingCart queryShoppingCart(String customer_id);

    void createShoppingCart(ShoppingCart shoppingCart);

    CartItem hasCartItem(String customer_id, String product_id);

    CartItem getCartItem(String cart_itemid);

    List<Map<String, Object>> queryCartPatientInfos(CartItem cartItem);
}
