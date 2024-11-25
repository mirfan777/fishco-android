package com.example.fishco.model;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class Disease {
    @SerializedName("id")
    private Long id;

    @SerializedName("disease_type")
    private String diseaseType;

    @SerializedName("name")
    private String name;

    @SerializedName("cause_agent")
    private String causeAgent;

    @SerializedName("affected_part")
    private String affectedPart;

    @SerializedName("prevention")
    private String prevention;

    @SerializedName("description")
    private String description;

    @SerializedName("symptoms")
    private String symptoms;

    @SerializedName("note")
    private String note;

    @SerializedName("created_at")
    private LocalDateTime createdAt;

    @SerializedName("updated_at")
    private LocalDateTime updatedAt;

    @SerializedName("affected_fish")
    private List<Fish> affectedFish;

    @SerializedName("products_recommendation")
    private List<Product> productsRecommendation;
}