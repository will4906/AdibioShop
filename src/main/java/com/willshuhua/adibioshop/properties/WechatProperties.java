/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by will on 2017/8/19.
 */
@Component
@PropertySource(name = "wechat", value = "classpath:config/wechat.properties", encoding = "utf-8")
public class WechatProperties {

    private String token;
    private String appid;
    private String appsecret;
    private String mch_id;
    private String apikey;
    private String merchant;

    public WechatProperties() {
    }



    public String getToken() {
        return token;
    }

    @Value("${wechat.token}")
    public void setToken(String token) {
        this.token = token;
    }

    public String getAppid() {
        return appid;
    }

    @Value("${wechat.appid}")
    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }
    @Value("${wechat.appsecret}")
    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getMch_id() {
        return mch_id;
    }

    @Value("${wechat.mch_id}")
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getApikey() {
        return apikey;
    }

    @Value("${wechat.apikey}")
    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getMerchant() {
        return merchant;
    }

    @Value("${wechat.merchant}")
    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }
}

