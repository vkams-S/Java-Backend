package com.vkams.personal.lasyEats.Exchanges;

import com.vkams.personal.lasyEats.dto.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class getMenuResponse {
    @NotNull Menu menu;
    @NotNull int menuResponseType;
    public getMenuResponse(Menu menu)
    {
         this.menu=menu;
    }
}
