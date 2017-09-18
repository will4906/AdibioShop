package com.willshuhua.adibioshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerWechat {

    private String customer_id;
    private String openid;
    private String refresh_token;
    private Date retoken_time;
    private String nickname;
    private int sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private Object privilege;
    private String unionid;
}
