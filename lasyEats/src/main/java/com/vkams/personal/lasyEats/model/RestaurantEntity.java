package com.vkams.personal.lasyEats.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "restaurants")
@QueryEntity

public class RestaurantEntity {
    @Id
    private String id;

    @NotNull
    private String restaurantId;

    @NotNull
    private String name;

    @NotNull
    private String city;

    @NotNull
    private String imageUrl;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    private String opensAt;

    @NotNull
    private String closesAt;

    @NotNull
    private List<String> attributes = new ArrayList<>();
}
