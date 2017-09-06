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
    private String name;
    private String gender;
    private float age;
    private String country;
    private String province;
    private String city;
    private String district;
    private String address;
    private String phone;
    private int has_diabetic;
    private int is_pregnant;
    private float height;
    private float weight;

}
