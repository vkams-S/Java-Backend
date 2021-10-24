package com.vkams.personal.lasyEats.Exchanges;

import javax.validation.constraints.NotNull;

public class restaurantOwnerLoginRequest {
    @NotNull
    String username;

    @NotNull
    String password;
}
