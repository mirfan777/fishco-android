package com.example.fishco.model;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Product {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private String link;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
