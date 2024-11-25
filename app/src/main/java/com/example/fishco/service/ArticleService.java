package com.example.fishco.service;

import com.example.fishco.model.Article;
import com.example.fishco.model.Comment;
import com.example.fishco.model.Reply;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ArticleService {
    @Headers("Content-Type: application/json")
    @GET("articles")
    Call<List<Article>> getAllArticles(@Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @GET("article/{id}/comments")
    Call<List<Comment>> getAllCommentsByArticleId(
            @Header("Authorization") String token,
            @Path("id") Integer article_id);

    @Headers("Content-Type: application/json")
    @POST("reply/create")
    Call<Reply> sendReply(
            @Header("Authorization") String token ,
            @Field("comment_id") Integer comment_id,
            @Field("user_id") Integer user_id,
            @Field("body") String body);

    @FormUrlEncoded
    @POST("comment/create")
    Call<Comment> sendComment(
            @Header("Authorization") String token ,
            @Field("article_id") Integer comment_id,
            @Field("user_id") Integer user_id,
            @Field("body") String body);


}