package com.burhanstore.bubblereward.utils;


import static com.burhanstore.bubblereward.config.API.GET_USER_DATA;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDataApiClient {
    private Retrofit retrofit;

    public UserDataApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(GET_USER_DATA)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getUserData(String email, final Callback<UserData> callback) {
        UserDataApiService service = retrofit.create(UserDataApiService.class);
        Call<UserData> call = service.getUserData(email);
        call.enqueue(callback);
    }


}
