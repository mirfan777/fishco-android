package com.example.fishco.service;

import com.example.fishco.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserService {
    @GET("profile")
    Call<User> getUserProfile(@Header("Authorization") String token);
}
