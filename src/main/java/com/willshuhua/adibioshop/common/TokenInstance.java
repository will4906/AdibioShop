package com.willshuhua.adibioshop.common;

import com.willshuhua.adibioshop.dto.access.AccessToken;
import com.willshuhua.adibioshop.dto.token.Token;
import com.willshuhua.adibioshop.retrofit.RetrofitManager;
import com.willshuhua.adibioshop.retrofit.wechat.WechatRequest;
import org.apache.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class TokenInstance {

    private Logger logger = Logger.getLogger(TokenInstance.class);

    private static TokenInstance ourInstance = new TokenInstance();

    public static TokenInstance getInstance() {
        return ourInstance;
    }

    private TokenInstance() {
        Serializer serializer = new Persister();
        File source = new File("token.xml");

        try {
            token = serializer.read(Token.class, source);
        } catch (Exception e) {
            token = null;
            e.printStackTrace();
        }
    }

    private Token token = new Token();
    private RetrofitManager retrofitManager = RetrofitManager.getInstance();

    public String getAccessToken(String appId, String appSecret) throws IOException {
        if (token == null || token.getExpire() == null || token.getAccess_token() == null|| new Date().getTime() - Long.valueOf(token.getExpire()) * 1000 >= token.getAccess_time()){
            Retrofit retrofit = retrofitManager.getGsonRetrofit();
            WechatRequest wechatRequest = retrofit.create(WechatRequest.class);
            Call<AccessToken> tokenCall = wechatRequest.requestAccessToken(appId, appSecret);
            AccessToken accessToken = tokenCall.execute().body();

            logger.info(accessToken);

            token = new Token();
            token.setAccess_time(new Date().getTime());
            token.setAccess_token(accessToken.getAccess_token());
            token.setExpire(accessToken.getExpires_in());

            Serializer serializer = new Persister();
            File result = new File("token.xml");
            try {
                serializer.write(token, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return token.getAccess_token();
    }
}
