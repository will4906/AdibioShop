/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service.impl;

import com.willshuhua.adibioshop.dao.ProductDao;
import com.willshuhua.adibioshop.entity.product.Product;
import com.willshuhua.adibioshop.entity.product.ProductDiscount;
import com.willshuhua.adibioshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public List<Product> queryAllProduct() {
        return productDao.queryAllProduct();
    }

    @Override
    public Product queryProductByProductId(String productId) {
        return productDao.queryProductByProductId(productId);
    }

    @Override
    public ProductDiscount getProductDiscount(String productId, String discountType) {
        return productDao.getProductDiscount(productId, discountType);
    }
}
