package com.example.fishco.model;

import com.google.gson.annotations.SerializedName;

public class Article {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("slug")
    private String slug;

    @SerializedName("body")
    private String body;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("comment_id")
    private int commentId;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    // Getters and Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public int getCommentId() { return commentId; }
    public void setCommentId(int commentId) { this.commentId = commentId; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
