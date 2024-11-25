package com.example.fishco.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Reply {
    @SerializedName("id")
    private Long id;

    @SerializedName("comment_id")
    private Long commentId;

    @SerializedName("user_id")
    private Long userId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("body")
    private String body;

    @SerializedName("created_at")
    private String createdAt;
}
