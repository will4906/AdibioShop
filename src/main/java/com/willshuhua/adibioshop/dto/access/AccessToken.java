package com.willshuhua.adibioshop.dto.access;

import lombok.Data;

@Data
public class AccessToken {

    private String access_token;
    private String expires_in;

}
