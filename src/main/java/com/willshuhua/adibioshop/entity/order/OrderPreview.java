package com.willshuhua.adibioshop.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPreview {

    private String order_itemid;
    private String product_id;
    private int quantity;
    private String product_name;
}
