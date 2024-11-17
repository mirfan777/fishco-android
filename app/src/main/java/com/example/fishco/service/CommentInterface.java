package com.example.fishco.service;
import com.example.fishco.model.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface CommentInterface {
    @FormUrlEncoded
    @POST("requestToken")
    Call<TokenResponse> requestToken(
            @Field("user_id") Long user_id,
            @Field("body") String body,
            @Field("article_id") Long article_id
    );
}
