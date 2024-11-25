package com.example.fishco.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Comment {
    @SerializedName("id")
    private Long id;

    @SerializedName("user_id")
    private Long userId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("article_id")
    private Long articleId;

    @SerializedName("body")
    private String body;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("replies")
    private List<Reply> replies;
}
