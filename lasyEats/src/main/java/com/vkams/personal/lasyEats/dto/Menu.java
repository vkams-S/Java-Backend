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
public class Menu {
    @NotNull
    private String restaurantId;
    @NotNull
    private List<Items> items = new ArrayList<>();
}
