package com.willshuhua.adibioshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientInfo {

    private String customer_id;
    private String patient_infoid;
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
