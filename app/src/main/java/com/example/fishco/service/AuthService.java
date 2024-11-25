package com.example.fishco.service;

import com.example.fishco.model.TokenResponse;
import com.example.fishco.model.User;

import okhttp3.ResponseBody;
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

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String passwordConfirmation,
            @Field("phone_number") String phoneNumber,
            @Field("device_name") String deviceName
    );

//    @FormUrlEncoded
//    @POST("register")
//    Call<User> registerUser(
//            @Field("name") String name,
//            @Field("email") String email,
//            @Field("password") String password,
//            @Field("password_confirmation") String passwordConfirmation,
//            @Field("phone_number") String phoneNumber,
//            @Field("address") String address // Tetap kosong untuk registrasi awal
//    );
}
