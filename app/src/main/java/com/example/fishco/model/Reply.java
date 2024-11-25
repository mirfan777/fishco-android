package com.example.fishco.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Reply {
    private Long id;
    private Long comment_id;
    private Long user_id;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<Reply> replies;
}
