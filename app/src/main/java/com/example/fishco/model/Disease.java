package com.example.fishco.model;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Disease {
    private Long id;
    private Integer disease_type;
    private String name;
    private String cause_agent;
    private String affected_part;
    private String prevention;
    private String description;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
