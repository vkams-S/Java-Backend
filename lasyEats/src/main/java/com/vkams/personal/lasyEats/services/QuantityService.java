package com.vkams.personal.lasyEats.services;

import com.vkams.personal.lasyEats.dto.ItemQuantity;

public interface QuantityService {
    public ItemQuantity updateQuantity(String itemId,String restaurantId,int quantity);
    public ItemQuantity getQuantity(String itemId,String restaurantId) throws Exception;
}
