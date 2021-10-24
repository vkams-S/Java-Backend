package com.vkams.personal.lasyEats.model;

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
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "items")

public class ItemEntity {
    @Id
    private String id;

    @NotNull
    private String itemId;

    @NotNull
    private String name;

    @NotNull
    private String imageUrl;

    @NotNull
    private Double price;

    @NotNull
    private List<String> attributes = new ArrayList<>();
}
