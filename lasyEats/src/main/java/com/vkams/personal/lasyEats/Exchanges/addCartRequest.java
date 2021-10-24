package com.vkams.personal.lasyEats.Exchanges;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class addCartRequest {
    @NotNull String cartId;
    @NotNull String itemId;
    @NotNull String restaurantId;

}
