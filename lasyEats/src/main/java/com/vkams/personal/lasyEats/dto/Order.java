package com.vkams.personal.lasyEats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @NotNull
    private String id;

    @NotNull
    private String restaurantId;

    @NotNull
    private String userId;

    @NotNull
    private List<Items> items = new ArrayList();

    @NotNull
    private int total;

    @NotNull
    private String timePlaced;

    @NotNull
    private String status;
}
