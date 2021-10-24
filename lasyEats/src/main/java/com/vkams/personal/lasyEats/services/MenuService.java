package com.vkams.personal.lasyEats.services;

import com.vkams.personal.lasyEats.Exchanges.getMenuResponse;
import com.vkams.personal.lasyEats.dto.Items;
import com.vkams.personal.lasyEats.exceptions.ItemNotFoundInRestaurantException;
import org.springframework.stereotype.Service;

@Service
public interface MenuService {
    getMenuResponse findMenu(String restaurantId) throws Exception;
    Items findItem(String itemId,String restaurantId) throws Exception, ItemNotFoundInRestaurantException;
    getMenuResponse addItem(Items item, String restaurantId) throws Exception;
    getMenuResponse removeItem(String itemId, String restaurantId) throws Exception;
    getMenuResponse updateItem(Items item, String restaurantId) throws ItemNotFoundInRestaurantException;
}
