package com.vkams.personal.lasyEats.Exchanges;

import com.vkams.personal.lasyEats.dto.Items;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class addMenuRequest {
    @NotNull Items item;
    @NotNull String restaurantId;


}
