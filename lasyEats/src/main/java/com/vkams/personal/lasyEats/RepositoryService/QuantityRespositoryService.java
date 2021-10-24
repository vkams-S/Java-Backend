package com.vkams.personal.lasyEats.RepositoryService;

import com.vkams.personal.lasyEats.dto.ItemQuantity;
import org.springframework.stereotype.Service;


public interface QuantityRespositoryService {

  public ItemQuantity updateQuantity(String itemId, String restaurantId, int quantity) throws Exception;

  public ItemQuantity getQuantity(String itemId, String restaurantId) throws Exception;
}
