package com.willshuhua.adibioshop.dto.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnsapiUserinfo {

    private String access_token;
    private String openid;
    private String lang = "zh_CN";
}
