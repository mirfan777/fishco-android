package com.example.fishco.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AquariumFishes {
    private Long id;
    private Long fish_id;
    private Long aquarium_id;
    private Long user_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
