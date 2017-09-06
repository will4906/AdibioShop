package com.willshuhua.adibioshop.dao;

import com.willshuhua.adibioshop.entity.cart.CartItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao {

    List<CartItem> queryCartForCustomer(@Param("customerId")String customerId);
}
