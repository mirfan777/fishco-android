package com.example.fishco.model;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Fish {
    private Long id;
    private String name;
    private String kingdom;
    private String phylum;
    private String fish_class;
    private String order;
    private String family;
    private String genus;
    private String species;
    private String colour;
    private String food_type;
    private String food;
    private Float min_temperature;
    private Float max_temperature;
    private Float min_ph;
    private Float max_ph;
    private String habitat;
    private String overview;
    private String thumbnail;
    private Float average_size;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
