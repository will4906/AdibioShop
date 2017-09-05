package com.willshuhua.adibioshop.dto.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WechatTemplate {

    private String touser;
    private String template_id;
    private String url;
    private Map<String, String> miniprogram;
    private Map<String, Map<String, String>> data;

}
