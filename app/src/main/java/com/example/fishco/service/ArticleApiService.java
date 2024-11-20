package com.example.fishco.service;

import com.example.fishco.model.Article; // Impor dari model
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ArticleApiService {
    @GET("articles")
    Call<ApiResponse<List<Article>>> getArticles();
}
