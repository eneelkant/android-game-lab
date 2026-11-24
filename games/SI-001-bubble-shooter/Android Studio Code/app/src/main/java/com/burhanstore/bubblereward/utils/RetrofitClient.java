package com.burhanstore.bubblereward.utils;

import static com.burhanstore.bubblereward.config.API.GET_POINT_LIVE;
import static com.burhanstore.bubblereward.config.API.LOGIN_API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(GET_POINT_LIVE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


       public static Retrofit loginApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(LOGIN_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static Retrofit getLogin() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(LOGIN_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
