package com.vkams.personal.lasyEats.Exchanges;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestaurantOwnerSignUpRequest {
    @NotNull
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String password;

}
