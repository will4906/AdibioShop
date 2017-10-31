package com.willshuhua.adibioshop.entity.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDiscount {

    private String product_discount_id;
    private String product_id;
    private String discount_type;
    private BigDecimal discount;
    private BigDecimal cashback;
    private String description;
}
