package com.vkams.personal.lasyEats.Exchanges;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class editQuantityRequest {
    @NotNull String itemId;
    @NotNull String restaurantId;
    @NotNull int quantity;

}
