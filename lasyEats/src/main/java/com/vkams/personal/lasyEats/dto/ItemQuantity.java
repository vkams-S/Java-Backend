package com.vkams.personal.lasyEats.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemQuantity {
    @Id
    private String id;
    @NotNull
    private String itemId;
    @NotNull
    private String restaurantId;
    @NotNull
    private int quantity;
}
