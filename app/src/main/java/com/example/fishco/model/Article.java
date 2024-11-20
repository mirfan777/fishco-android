package com.example.fishco.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

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

    @SerializedName("comment_id")
    private Long commentId;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("url_thumbnail")
    private String urlThumbnail;

    @SerializedName("created_at")
    private String createdAt;

    // Penyesuaian model untuk atribut content
    @SerializedName("content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
