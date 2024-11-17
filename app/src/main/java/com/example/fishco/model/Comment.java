package com.example.fishco.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Comment {
    private Long id;
    private Long user_id;
    private String body;
    private Long article_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
