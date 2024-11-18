package com.example.fishco.service;

import com.example.fishco.model.Fish;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FishService {
    @GET("fishes")
    Call<List<Fish>> getAllFish(@Query("category") String category  );

    @GET("fish/{id}")
    Call<Fish> getFishById(@Path("id") String id);





}
