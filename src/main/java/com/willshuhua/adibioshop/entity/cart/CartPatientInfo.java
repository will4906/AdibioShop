package com.willshuhua.adibioshop.entity.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartPatientInfo {

    private String cart_itemid;
    private String cart_patient_infoid;
    private String product_id;
    private String patient_infoid;

}
