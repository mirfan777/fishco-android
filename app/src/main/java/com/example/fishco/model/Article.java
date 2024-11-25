package com.example.fishco.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Article {
    @SerializedName("id")
    private Long id;

    @SerializedName("title")
    private String title;

    @SerializedName("slug")
    private String slug;

    @SerializedName("body")
    private String body;

    @SerializedName("user_id")
    private Long userId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("url_thumbnail")
    private String urlThumbnail;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("comments")
    private List<Comment> comments;
}
