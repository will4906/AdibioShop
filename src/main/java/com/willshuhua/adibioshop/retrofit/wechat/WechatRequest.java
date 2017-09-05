/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.retrofit.wechat;

import com.willshuhua.adibioshop.dto.access.AccessToken;
import com.willshuhua.adibioshop.dto.access.Authorization;
import com.willshuhua.adibioshop.dto.template.TemplateBack;
import com.willshuhua.adibioshop.dto.template.WechatTemplate;
import com.willshuhua.adibioshop.dto.wechat_pay.UnifiedOrder;
import com.willshuhua.adibioshop.dto.wechat_pay.UnifiedOrderBack;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface WechatRequest {

    @GET("/cgi-bin/token?grant_type=client_credential")
    Call<AccessToken> requestAccessToken(@Query("appid") String appId, @Query("secret") String appSecret);

    @POST("/cgi-bin/message/template/send")
    Call<TemplateBack> sendTemplate(@Query("access_token") String access_token, @Body WechatTemplate wechatTemplate);

    @GET("/sns/oauth2/access_token?grant_type=authorization_code")
    Call<Authorization> requestAuthorization(@Query("appid") String appId, @Query("secret") String appSecret, @Query("code") String code);

    @POST("/pay/unifiedorder")
    Call<UnifiedOrderBack> requestUnifiedOrder(@Body UnifiedOrder unifiedOrder);
}
