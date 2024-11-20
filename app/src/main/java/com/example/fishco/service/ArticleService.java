package com.example.fishco.service;

import com.example.fishco.model.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ArticleService {
    @Headers("Content-Type: application/json")
    @GET("articles")
    Call<List<Article>> getAllArticles(@Header("Authorization") String token);
}