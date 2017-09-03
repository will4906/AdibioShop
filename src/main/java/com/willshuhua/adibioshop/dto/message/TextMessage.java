package com.willshuhua.adibioshop.dto.message;

import lombok.Data;

@Data
public class TextMessage {

    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private String Content;

}
