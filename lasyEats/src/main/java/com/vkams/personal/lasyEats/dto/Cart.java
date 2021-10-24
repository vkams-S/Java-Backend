package com.vkams.personal.lasyEats.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"status"})
public class Cart {
    private String id;
    private String restaurantId;
    private String userId;
    private List<Items> items =new ArrayList<>();
    private int total;
}
