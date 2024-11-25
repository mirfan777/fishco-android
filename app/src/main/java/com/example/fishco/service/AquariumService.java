package com.example.fishco.service;

import com.example.fishco.model.Aquarium;
import com.example.fishco.model.Comment;
import com.example.fishco.model.TokenResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AquariumService {
    @FormUrlEncoded
    @POST("requestToken")
    Call<TokenResponse> requestToken(
            @Field("user_id") Long user_id,
            @Field("name") String name,
            @Field("volume_size") Float volume_size,
            @Field("length") Float length,
            @Field("width") Float width,
            @Field("height") Float height,
            @Field("material") String material,
            @Field("type") String type,
            @Field("filter_type") String filter_type,
            @Field("filter_capacity") String filter_capacity,
            @Field("filter_media") String filter_media,
            @Field("min_temperature") Float min_temperature,
            @Field("max_temperature") Float max_temperature,
            @Field("min_ph") Float min_ph,
            @Field("max_ph") Float max_ph,
            @Field("turbidity") Float turbidity,
            @Field("salinity") Float salinity,
            @Field("disolved_oxygen") Float disolved_oxygen,
            @Field("hardness") Float hardness,
            @Field("amonia") Float amonia,
            @Field("nitrite") Float nitrite,
            @Field("nitrate") Float nitrate

    );

    @Headers("Content-Type: application/json")
    @GET("aquarium/user/{id}")
    Call<List<Aquarium>> getUserAquarium(
            @Header("Authorization") String token,
            @Path("id") Integer user_id);
}
