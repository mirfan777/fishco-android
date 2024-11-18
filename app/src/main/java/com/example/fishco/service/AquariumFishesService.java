package com.example.fishco.service;

import com.example.fishco.model.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AquariumFishesService {
    @FormUrlEncoded
    @POST("requestToken")
    Call<TokenResponse> requestToken(
            @Field("fish_id") Long fish_id,
            @Field("aquarium_id") Long aquarium_id,
            @Field("user_id") Long user_id
    );
}
