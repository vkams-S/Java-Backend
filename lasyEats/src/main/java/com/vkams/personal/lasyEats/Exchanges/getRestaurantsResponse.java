package com.vkams.personal.lasyEats.Exchanges;

import com.vkams.personal.lasyEats.dto.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class getRestaurantsResponse {
     List<Restaurant> restaurants;
}
