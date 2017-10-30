package com.willshuhua.adibioshop.dto.access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsTicket {

    private String errcode;
    private String errmsg;
    private String ticket;
    private String expires_in;
}
