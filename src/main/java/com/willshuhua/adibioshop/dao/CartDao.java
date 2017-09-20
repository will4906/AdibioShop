package com.willshuhua.adibioshop.dao;

import com.willshuhua.adibioshop.entity.cart.CartItem;
import com.willshuhua.adibioshop.entity.cart.CartPatientInfo;
import com.willshuhua.adibioshop.entity.cart.ItemProduct;
import com.willshuhua.adibioshop.entity.cart.ShoppingCart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CartDao {

    List<ItemProduct> queryCartForCustomer(@Param("customerId")String customerId);

    void updateCartItemQuantity(@Param("cart_itemid")String cart_itemid, @Param("quantity")int quantity);

    void insertCartItem(CartItem cartItem);

    void insertCartPatientInfo(CartPatientInfo cartPatientInfo);

    void deleteCartItem(CartItem cartItem);

    void deleteCartPatientInfo(CartPatientInfo cartPatientInfo);

    ShoppingCart queryShoppingCart(@Param("customer_id")String customer_id);

    void createShoppingCart(ShoppingCart shoppingCart);

    CartItem hasCartItem(@Param("customer_id")String customer_id, @Param("product_id")String product_id);

    CartItem getCartItem(@Param("cart_itemid")String cart_itemid);

    CartItem getCartItemByProductId(@Param("product_id")String product_id, @Param("customer_id")String customer_id);

    List<Map<String, Object>> queryCartPatientInfos(CartItem cartItem);

    CartPatientInfo getCartPatientInfo(String cart_patient_infoid);

    void deleteItemCartPatientInfos(CartItem cartItem);
}
