package com.willshuhua.adibioshop.retrofit;

import com.willshuhua.adibioshop.define.url.WechatUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitManager {
    private static RetrofitManager ourInstance = new RetrofitManager();

    public static RetrofitManager getInstance() {
        return ourInstance;
    }

    private RetrofitManager() {

        logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        gsonBuilder = new Retrofit.Builder().baseUrl(WechatUrl.API).addConverterFactory(GsonConverterFactory.create());
        gsonRetrofit = gsonBuilder.client(httpClient.build()).build();

        xmlBuilder = new Retrofit.Builder().baseUrl(WechatUrl.PAY_API).addConverterFactory(SimpleXmlConverterFactory.create());
        xmlRetrofit = xmlBuilder.client(httpClient.build()).build();
    }

    private HttpLoggingInterceptor logging;

    private OkHttpClient.Builder httpClient;

    private Retrofit.Builder gsonBuilder;
    private Retrofit gsonRetrofit;

    private Retrofit.Builder xmlBuilder;
    private Retrofit xmlRetrofit;

    public Retrofit getGsonRetrofit() {
        return gsonRetrofit;
    }

    public Retrofit getXmlRetrofit() {
        return xmlRetrofit;
    }
}
