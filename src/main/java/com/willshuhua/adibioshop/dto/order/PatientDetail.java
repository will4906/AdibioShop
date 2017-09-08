package com.willshuhua.adibioshop.dto.order;

import lombok.Data;

@Data
public class PatientDetail {

    private String customer_id; //用户id，不需要前端填写
    private String name;        //姓名
    private String gender;      //性别
    private String age;         //年龄
    private String country;     //国家，由后台自动填写
    private String province;    //省份
    private String city;        //城市
    private String district;    //区
    private String address;     //具体地址
    private String phone;       //电话
    private String is_pregnant;  //是否怀孕
    private String has_diabetic; //是否有糖尿病史
    private String height;      //身高
    private String weight;      //体重
    private String product_id;  //产品id， 先向数据库请求后获得


}
