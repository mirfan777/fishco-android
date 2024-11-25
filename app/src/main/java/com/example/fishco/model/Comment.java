package com.example.fishco.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Comment {
    private Long id;
    private Long user_id;
    private String body;
    private Long article_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<Reply> replies;
}

//@Data
//public class Comment {
//    private Long id;
//    private Long user_id;
//    private String body;
//    private Long article_id;
//    private Long createdAt; // Timestamp dalam milidetik
//    private Long updatedAt; // Timestamp dalam milidetik
//    private List<Reply> replies;
//}