package com.example.fishco.service;

import com.example.fishco.model.Fish;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FishInterface {
    @GET("fishes")
    Call<List<Fish>> getAllFish();

    @GET("fish/{id}")
    Call<Fish> getFishById(@Path("id") String id);





}
