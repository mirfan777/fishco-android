package com.example.fishco.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data
public class Aquarium {
    @SerializedName("id")
    private Long id;

    @SerializedName("user_id")
    private Long userId;

    @SerializedName("name")
    private String name;

    @SerializedName("volume_size")
    private Float volumeSize;

    @SerializedName("type")
    private String type;

    @SerializedName("filter_type")
    private String filterType;

    @SerializedName("filter_capacity")
    private String filterCapacity;

    @SerializedName("filter_media")
    private String filterMedia;

    @SerializedName("min_temperature")
    private Float minTemperature;

    @SerializedName("max_temperature")
    private Float maxTemperature;

    @SerializedName("min_ph")
    private Float minPh;

    @SerializedName("max_ph")
    private Float maxPh;

    @SerializedName("min_salinity")
    private Float minSalinity;

    @SerializedName("max_salinity")
    private Float maxSalinity;

    @SerializedName("turbidity")
    private Float turbidity;

    @SerializedName("aquariumfishes")
    private List<Fish> aquariumFishes;
}
