package com.vkams.personal.lasyEats.model;

import com.vkams.personal.lasyEats.dto.Items;
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
@Document(collection = "orders")
@AllArgsConstructor
@NoArgsConstructor

public class OrderEntity {
    @Id
    private String id;

    @NotNull
    private String restaurantId;

    @NotNull
    private String userId;

    @NotNull
    private List<Items> items = new ArrayList();

    @NotNull
    private int total = 0;

    @NotNull
    private String placedTime;

    @NotNull
    private String status;
}
