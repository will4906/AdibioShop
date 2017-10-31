/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.dao;

import com.willshuhua.adibioshop.entity.product.Product;
import com.willshuhua.adibioshop.entity.product.ProductDiscount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductDao {

    List<Product> queryAllProduct();

    Product queryProductByProductId(String productId);

    ProductDiscount getProductDiscount(@Param("product_id")String productId, @Param("discount_type")String discountType);
}
