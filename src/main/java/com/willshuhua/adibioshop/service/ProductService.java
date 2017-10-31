/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service;


import com.willshuhua.adibioshop.entity.product.Product;
import com.willshuhua.adibioshop.entity.product.ProductDiscount;

import java.util.List;
import java.util.Map;

public interface ProductService {

    List<Product> queryAllProduct();

    Product queryProductByProductId(String productId);

    ProductDiscount getProductDiscount(String productId, String discountType);
}
