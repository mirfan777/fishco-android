package com.example.fishco.service;

import com.example.fishco.model.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {
    @FormUrlEncoded
    @POST("requestToken")
    Call<TokenResponse> requestToken(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_name") String deviceName
    );
}
