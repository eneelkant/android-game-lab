package com.burhanstore.bubblereward.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserDataApiService {
    @GET("getUserData")
    Call<UserData> getUserData(@Query("email") String email);



}
