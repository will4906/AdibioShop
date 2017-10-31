package com.willshuhua.adibioshop.entity.cart;

import com.willshuhua.adibioshop.dao.ProductDao;
import com.willshuhua.adibioshop.entity.product.Product;
import com.willshuhua.adibioshop.entity.product.ProductDiscount;
import com.willshuhua.adibioshop.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private String cart_id;
    private String cart_itemid;
    private String product_id;
    private int quantity;
    private BigDecimal whole_price;

    public CartItem(String cart_id, String cart_itemid, String product_id, int quantity){
        this.cart_id = cart_id;
        this.cart_itemid = cart_itemid;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public void computeWholePrice(String discountType, ProductDao productDao){
        switch (discountType){
            case "share":
                ProductDiscount productDiscount = productDao.getProductDiscount(product_id, discountType);
                Product product = productDao.queryProductByProductId(product_id);
                setWhole_price(product.getUnit_price().subtract(productDiscount.getDiscount()).multiply(new BigDecimal(quantity)));
                break;
            default:
                break;
        }
    }

    public void computeWholePrice(String discountType, ProductService productService){
        switch (discountType){
            case "share":
                ProductDiscount productDiscount = productService.getProductDiscount(product_id, discountType);
                Product product = productService.queryProductByProductId(product_id);
                setWhole_price(product.getUnit_price().subtract(productDiscount.getDiscount()).multiply(new BigDecimal(quantity)));
                break;
            default:
                break;
        }
    }
}
