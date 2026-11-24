package com.burhanstore.bubblereward.utils;

import static com.burhanstore.bubblereward.config.API.APP_SETTINGS_API;
import static com.burhanstore.bubblereward.config.API.LOGIN_API;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("YOUR_API_ENDPOINT_HERE") // Replace with your actual API endpoint
    Call<Integer> retrieveUserPoints(@Field("user_id") String userID);

    @FormUrlEncoded
    @POST(LOGIN_API) // Specify the URL of your PHP script
    Call<ApiResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );


    @GET(APP_SETTINGS_API) // Replace with your PHP script's URL
    Call<AppSettingsResponse> getAppSettingsData(@Query("id") String id);

}


