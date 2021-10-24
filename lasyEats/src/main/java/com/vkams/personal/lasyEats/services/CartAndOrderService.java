package com.vkams.personal.lasyEats.services;

import com.vkams.personal.lasyEats.Exchanges.cartModifiedResponse;
import com.vkams.personal.lasyEats.Exchanges.getCartRequest;
import com.vkams.personal.lasyEats.Exchanges.getOrderResponse;
import com.vkams.personal.lasyEats.dto.Cart;
import com.vkams.personal.lasyEats.dto.Order;
import com.vkams.personal.lasyEats.exceptions.EmptyCartException;
import com.vkams.personal.lasyEats.exceptions.ItemNotFromSameRestaurant;

public interface CartAndOrderService {
   Cart findCartByUserId(getCartRequest getCartRequest);
   Cart createOrFindCart(String userId);
   cartModifiedResponse addItemToCart(String itemId, String cartId, String restaurantId) throws Exception;
   cartModifiedResponse removeItemFromCart(String itemId, String cartId, String restaurantId) throws Exception;
   Order postOrder(String cartId) throws EmptyCartException;
   getOrderResponse getAllPlacedOrders(String restaurantId);
   getOrderResponse getAllOrders(String restaurantId);
   getOrderResponse updateStatusOfPlacedOrder(String restaurantId, String orderId, String status);
   getOrderResponse updateStatusOfOrder(String restaurantId, String orderId, String status);
}
