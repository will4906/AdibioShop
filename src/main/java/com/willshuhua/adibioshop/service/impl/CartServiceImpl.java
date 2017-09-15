package com.willshuhua.adibioshop.service.impl;

import com.willshuhua.adibioshop.dao.CartDao;
import com.willshuhua.adibioshop.dao.CustomerDao;
import com.willshuhua.adibioshop.dto.cart.AddCartItem;
import com.willshuhua.adibioshop.entity.PatientInfo;
import com.willshuhua.adibioshop.entity.cart.CartItem;
import com.willshuhua.adibioshop.entity.cart.CartPatientInfo;
import com.willshuhua.adibioshop.entity.cart.ItemProduct;
import com.willshuhua.adibioshop.entity.cart.ShoppingCart;
import com.willshuhua.adibioshop.service.CartService;
import groovy.util.IFileNameFinder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService{

    private Logger logger = Logger.getLogger(CartServiceImpl.class);
    @Autowired
    CartDao cartDao;
    @Autowired
    CustomerDao customerDao;

    @Override
    public List<ItemProduct> queryCartForCustomer(String customerId) {
        return cartDao.queryCartForCustomer(customerId);
    }

    @Transactional
    @Override
    public void addCartItemQuantity(String cartItemId) {
        CartItem cartItem = cartDao.getCartItem(cartItemId);
        cartDao.updateCartItemQuantity(cartItemId, cartItem.getQuantity() + 1);
    }

    @Transactional
    @Override
    public CartItem addCartItem(AddCartItem addCartItem) {
        CartItem cartItem = addCartItem.getCartItem();
        String cartItemId = cartItem.getCart_itemid();
        String product_id = cartItem.getProduct_id();
        PatientInfo patientInfo = addCartItem.getPatientInfo();

//        解析patientInfo
        String patient_infoid = patientInfo.getPatient_infoid();
        if (patient_infoid == null || patient_infoid.equals("")){
            PatientInfo hasPatient = customerDao.hasPatientInfo(patientInfo);
            if (hasPatient != null){
                patientInfo = hasPatient;
            }else {
                patientInfo.setPatient_infoid(UUID.randomUUID().toString());
                customerDao.createPatientInfo(patientInfo);
            }
        }else {
            PatientInfo hasPatient = customerDao.hasPatientInfoId(patient_infoid);
            if (hasPatient != null){
                patientInfo = hasPatient;
            }else {
                return null;
            }
        }
        logger.info(patientInfo);
//        解析cartItem
        if (cartItemId != null){
            cartItem = cartDao.getCartItem(cartItemId);
            if (cartItem != null){
                cartDao.updateCartItemQuantity(cartItemId, cartItem.getQuantity() + 1);
                cartDao.insertCartPatientInfo(new CartPatientInfo(cartItemId, UUID.randomUUID().toString(), product_id, patientInfo.getPatient_infoid()));
            }else {
                ShoppingCart shoppingCart = cartDao.queryShoppingCart(patientInfo.getCustomer_id());
                cartItem = new CartItem(shoppingCart.getCart_id(), UUID.randomUUID().toString(), product_id, 1);
                cartDao.insertCartItem(cartItem);
                cartDao.insertCartPatientInfo(new CartPatientInfo(cartItem.getCart_itemid(), UUID.randomUUID().toString(), product_id, patientInfo.getPatient_infoid()));
            }
        }else {
            ShoppingCart shoppingCart = cartDao.queryShoppingCart(patientInfo.getCustomer_id());
            cartItem = new CartItem(shoppingCart.getCart_id(), UUID.randomUUID().toString(), product_id, 1);
            cartDao.insertCartItem(cartItem);
            cartDao.insertCartPatientInfo(new CartPatientInfo(cartItem.getCart_itemid(), UUID.randomUUID().toString(), product_id, patientInfo.getPatient_infoid()));
        }
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        return cartItem;
    }

    @Transactional
    @Override
    public CartItem reduceCartItem(CartPatientInfo cartPatientInfo) {
        CartPatientInfo cartPatientInfo1 = cartDao.getCartPatientInfo(cartPatientInfo.getCart_patient_infoid());
        if (cartPatientInfo1 != null){
            cartPatientInfo = cartPatientInfo1;
        }else {
            return null;
        }
        CartItem cartItem = cartDao.getCartItem(cartPatientInfo.getCart_itemid());
        if (cartItem == null){
            return null;
        }
        cartDao.deleteCartPatientInfo(cartPatientInfo);
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

    @Override
    public CartItem getCartItem(String cart_itemid) {
        return cartDao.getCartItem(cart_itemid);
    }

    @Transactional
    @Override
    public List<Map<String, Object>> queryCartPatientInfos(CartItem cartItem) {
        CartItem cartItem1 = cartDao.getCartItem(cartItem.getCart_itemid());
        if (cartItem1 != null){
            cartItem = cartItem1;
        }else {
            return null;
        }
        return cartDao.queryCartPatientInfos(cartItem);
    }

}
