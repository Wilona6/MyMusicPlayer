package com.example.myplayer.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static Retrofit mRetrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://music.eleuu.com")
            //.baseUrl("https://autumnfish.cn/")
            .build();
    public static Retrofit getRetrofit(){
        return mRetrofit;
    }
}
