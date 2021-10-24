package com.vkams.personal.lasyEats.Exchanges;

import com.vkams.personal.lasyEats.dto.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class getOrderResponse {
    @NotNull List<Order> order;
    @NotNull String restaurantId;
}
