package com.willshuhua.adibioshop.common;

import com.willshuhua.adibioshop.dto.access.AccessToken;
import com.willshuhua.adibioshop.retrofit.RetrofitManager;
import com.willshuhua.adibioshop.retrofit.wechat.WechatRequest;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TokenInstance {

    private Logger logger = Logger.getLogger(TokenInstance.class);

    private static TokenInstance ourInstance = new TokenInstance();

    public static TokenInstance getInstance() {
        return ourInstance;
    }

    private TokenInstance() {}

    private RetrofitManager retrofitManager = RetrofitManager.getInstance();

    public String getAccessToken(StringRedisTemplate redisTemplate, String appId, String appSecret) throws IOException {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        if (redisTemplate.hasKey("access_token")) {
            return ops.get("access_token");
        } else {
            Retrofit retrofit = retrofitManager.getGsonRetrofit();
            WechatRequest wechatRequest = retrofit.create(WechatRequest.class);
            Call<AccessToken> tokenCall = wechatRequest.requestAccessToken(appId, appSecret);
            AccessToken accessToken = tokenCall.execute().body();
            logger.info(accessToken);
            String strAccessToken = accessToken.getAccess_token();
            ops.set("access_token", strAccessToken, 7200, TimeUnit.SECONDS);
            return strAccessToken;
        }
    }

}
