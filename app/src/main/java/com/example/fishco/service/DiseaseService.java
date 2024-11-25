package com.example.fishco.service;

import com.example.fishco.model.Disease;
import com.example.fishco.model.Fish;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DiseaseService {

    @Headers("Content-Type: application/json")
    @GET("disease/{id}")
    Call<Disease> getDiseaseById(@Header("Authorization") String token , @Path("id") Integer id );
}
