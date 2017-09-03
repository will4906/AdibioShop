package com.willshuhua.adibioshop.dto.template;

import lombok.Data;

import java.util.Map;

@Data
public class WechatTemplate {

    private String touser;
    private String template_id;
    private String url;
    private Map<String, String> miniprogram;
    private Map<String, Map<String, String>> data;

}
