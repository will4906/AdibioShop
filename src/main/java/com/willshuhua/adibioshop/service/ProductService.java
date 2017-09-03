/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service;


import com.willshuhua.adibioshop.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> queryAllProduct();

    Product queryProductByProductId(String productId);
}
