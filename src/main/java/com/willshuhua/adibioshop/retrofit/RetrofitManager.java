package com.willshuhua.adibioshop.retrofit;

import com.willshuhua.adibioshop.define.url.WechatUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static RetrofitManager ourInstance = new RetrofitManager();

    public static RetrofitManager getInstance() {
        return ourInstance;
    }

    private RetrofitManager() {
    }

    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private Retrofit.Builder builder = new Retrofit.Builder().baseUrl(WechatUrl.API).addConverterFactory(GsonConverterFactory.create());
    private Retrofit retrofit = builder.client(httpClient.build()).build();

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
