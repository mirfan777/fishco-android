package com.example.fishco.model;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class  Aquarium {
    private Long id;
    private Long user_id;
    private String name;
    private Float volume_size;
    private Float length;
    private Float width;
    private Float height;
    private String material;
    private String type;
    private String filter_type;
    private String filter_capacity;
    private String filter_media;
    private Float min_temperature;
    private Float max_temperature;
    private Float min_ph;
    private Float max_ph;
    private Float turbidity;
    private Float salinity;
    private Float disolved_oxygen;
    private Float hardness;
    private Float amonia;
    private Float nitrite;
    private Float nitrate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
