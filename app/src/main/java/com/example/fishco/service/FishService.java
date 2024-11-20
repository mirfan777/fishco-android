package com.example.fishco.service;

import com.example.fishco.model.Fish;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FishService {
    @Headers("Content-Type: application/json")
    @GET("fishes")
    Call<List<Fish>> getAllFish(@Header("Authorization") String token , @Query("category") Optional<String> category);

    @GET("fish/{id}")
    Call<Fish> getFishById(@Header("Authorization") String token , @Path("id") String id);
}
