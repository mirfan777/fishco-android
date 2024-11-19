package com.example.fishco.model;
import lombok.Data;
import java.time.LocalDateTime;
import com.google.gson.annotations.SerializedName;

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

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("comment_id")
    private Long commentId;

    @SerializedName("created_at")
    private String createdAt; // Bisa diganti LocalDateTime, jika ada deserializer

    @SerializedName("updated_at")
    private String updatedAt; // Sama seperti di atas
}

//@Data
//public class Article {
//    private Long id;
//    private String title;
//    private String slug;
//    private String body;
//    private Long user_id;
//    private String thumbnail;
//    private Long comment_id;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//}
