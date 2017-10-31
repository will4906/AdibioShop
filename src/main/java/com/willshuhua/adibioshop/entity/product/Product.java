/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.entity.product;

import com.willshuhua.adibioshop.service.ProductService;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    private int row_id;
    private String product_id;
    private String product_groupid;
    private String product_name;
    private BigDecimal unit_price;
    private BigDecimal real_price;
    private String description;

    public void computeRealPrice(String discountType, ProductService productService){
        switch (discountType){
            case "share":
                ProductDiscount productDiscount = productService.getProductDiscount(product_id, discountType);
                setReal_price(getUnit_price().subtract(productDiscount.getDiscount()));
                break;
            default:
                setReal_price(getUnit_price());
                break;
        }
    }
}
