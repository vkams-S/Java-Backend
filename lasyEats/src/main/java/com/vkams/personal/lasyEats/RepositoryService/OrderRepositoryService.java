

package com.vkams.personal.lasyEats.RepositoryService;



import com.vkams.personal.lasyEats.dto.Cart;
import com.vkams.personal.lasyEats.dto.Order;

import java.util.List;

public interface OrderRepositoryService {



  Order placeOrder(Cart cart);


  List<Order> getOrdersByRestaurant(String restaurantId);

  Order updateStatus(String restaurantId, String orderId,
                     String status);

  Order getOrderById(String orderId);

}
