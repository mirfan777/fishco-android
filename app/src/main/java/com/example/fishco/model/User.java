package com.example.fishco.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime emailVerifiedAt;
    private Integer roleId;
    private String address;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
