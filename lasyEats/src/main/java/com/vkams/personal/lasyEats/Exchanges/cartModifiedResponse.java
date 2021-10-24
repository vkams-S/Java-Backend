package com.vkams.personal.lasyEats.Exchanges;

import com.vkams.personal.lasyEats.dto.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class cartModifiedResponse {
    @NotNull private Cart cart;
    @NotNull private int cartResponseType;
}
