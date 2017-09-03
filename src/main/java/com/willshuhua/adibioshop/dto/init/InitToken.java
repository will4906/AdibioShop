package com.willshuhua.adibioshop.dto.init;

import lombok.Data;

public @Data class InitToken {

    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;
}
