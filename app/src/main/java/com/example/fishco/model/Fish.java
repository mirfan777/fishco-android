package com.example.fishco.model;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Fish {
    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("kingdom")
    private String kingdom;

    @SerializedName("phylum")
    private String phylum;

    @SerializedName("fish_class")
    private String fishClass; // Consider renaming to avoid potential conflicts

    @SerializedName("order")
    private String order;

    @SerializedName("family")
    private String family;

    @SerializedName("genus")
    private String genus;

    @SerializedName("species")
    private String species;

    @SerializedName("colour")
    private String colour;

    @SerializedName("food_type")
    private String foodType;

    @SerializedName("food")
    private String food;

    @SerializedName("min_temperature")
    private Float minTemperature;

    @SerializedName("max_temperature")
    private Float maxTemperature;

    @SerializedName("min_ph")
    private Float minPh;

    @SerializedName("max_ph")
    private Float maxPh;

    @SerializedName("habitat")
    private String habitat;

    @SerializedName("overview")
    private String overview;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("url_thumbnail")
    private String urlThumbnail;

    @SerializedName("average_size")
    private Float averageSize;

    @SerializedName("created_at")
    private LocalDateTime createdAt;

    @SerializedName("updated_at")
    private LocalDateTime updatedAt;
}
