package com.willshuhua.adibioshop.retrofit;

import com.willshuhua.adibioshop.define.url.WechatUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitManager {
    private static RetrofitManager ourInstance = new RetrofitManager();

    public static RetrofitManager getInstance() {
        return ourInstance;
    }

    private RetrofitManager() {
    }

    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private Retrofit.Builder gsonBuilder = new Retrofit.Builder().baseUrl(WechatUrl.API).addConverterFactory(GsonConverterFactory.create());
    private Retrofit gsonRetrofit = gsonBuilder.client(httpClient.build()).build();

    private Retrofit.Builder xmlBuilder = new Retrofit.Builder().baseUrl(WechatUrl.API).addConverterFactory(SimpleXmlConverterFactory.create());
    private Retrofit xmlRetrofit = xmlBuilder.client(httpClient.build()).build();

    public Retrofit getGsonRetrofit() {
        return gsonRetrofit;
    }

    public Retrofit getXmlRetrofit() {
        return xmlRetrofit;
    }
}
