package com.example.fishco.model;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Article {
    private Long id;
    private String title;
    private String slug;
    private String body;
    private Long user_id;
    private String thumbnail;
    private Long comment_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
