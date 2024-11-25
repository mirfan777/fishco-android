package com.example.fishco.model;

import com.google.gson.annotations.SerializedName;
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

    @SerializedName("class")
    private String fish_class;

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

    @SerializedName("venomous")
    private Integer venomous;

    @SerializedName("poisonous")
    private Integer poisonous;

    @SerializedName("aggressive")
    private Integer aggressive;

    @SerializedName("territorial")
    private Integer territorial;

    @SerializedName("food_type")
    private String foodType;

    @SerializedName("food")
    private String food;

    @SerializedName("min_temperature")
    private Float minTemperature;

    @SerializedName("max_temperature")
    private Float maxTemperature;

    @SerializedName("min_salinity")
    private Float minSalinity;

    @SerializedName("max_salinity")
    private Float maxSalinity;

    @SerializedName("min_aquarium")
    private Float minAquarium;

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
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;
}
