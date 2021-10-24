package com.vkams.personal.lasyEats.services;

import com.vkams.personal.lasyEats.RepositoryService.QuantityRespositoryService;
import com.vkams.personal.lasyEats.dto.ItemQuantity;
import com.vkams.personal.lasyEats.exceptions.ItemNotFoundInRestaurantException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class QuantityServiceImpl implements QuantityService{
    @Autowired
    QuantityRespositoryService quantityRespositoryService;
    @Override
    public ItemQuantity updateQuantity(String itemId, String restaurantId, int quantity) {
          try{
              return quantityRespositoryService.updateQuantity(itemId,restaurantId,quantity);
          }
          catch (Exception e)
          {
              log.info("Unable to update qunatity of item"+e);
              throw new ItemNotFoundInRestaurantException(e.getMessage());
          }

    }

    @Override
    public ItemQuantity getQuantity(String itemId, String restaurantId) throws Exception {
        try{
            return quantityRespositoryService.getQuantity(itemId,restaurantId);
        }
        catch (Exception e)
        {
            throw new ItemNotFoundInRestaurantException(e.getMessage());
        }
    }
}
