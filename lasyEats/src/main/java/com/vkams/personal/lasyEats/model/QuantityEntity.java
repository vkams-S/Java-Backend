package com.vkams.personal.lasyEats.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "quantities")
@QueryEntity

public class QuantityEntity {
    @Id
    private String id;

    @NotNull
    private String itemId;

    @NotNull
    private String restaurantId;

    @NotNull
    private int quantity;
}
