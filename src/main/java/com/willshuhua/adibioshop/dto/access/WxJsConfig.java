package com.willshuhua.adibioshop.dto.access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxJsConfig {

    private String appId;
    private String timestamp;
    private String nonceStr;
    private String signature;

}
