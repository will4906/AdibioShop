package com.willshuhua.adibioshop.dto.access;

import lombok.Data;

@Data
public class Authorization {

    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String openid;
    private String scope;

}
