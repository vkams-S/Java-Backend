package com.vkams.personal.lasyEats.services;

import com.vkams.personal.lasyEats.Exchanges.getMenuResponse;
import com.vkams.personal.lasyEats.RepositoryService.ItemRepositoryService;
import com.vkams.personal.lasyEats.RepositoryService.MenuRepositoryService;
import com.vkams.personal.lasyEats.dto.Items;
import com.vkams.personal.lasyEats.dto.Menu;
import com.vkams.personal.lasyEats.exceptions.ItemNotFoundInRestaurantException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MenuServiceImpl implements MenuService{
    com.vkams.personal.lasyEats.Exchanges.getMenuResponse getMenuResponse = new getMenuResponse();
    @Autowired
    MenuRepositoryService menuRepositoryService;
    @Autowired
    ItemRepositoryService itemRepositoryService;
    @Override
    public getMenuResponse findMenu(String restaurantId) throws Exception {
        Menu menu = menuRepositoryService.findMenu(restaurantId);
        if(menu!=null)
        {
            if(menu.getRestaurantId().equals(restaurantId))
            {
                return new getMenuResponse(menu);
            }
        }
        else
        {
            log.info("Unable to find Menu for this restaurant"+restaurantId);
            throw new Exception("Menu Not found!");

        }
        return new getMenuResponse(menu);
    }

    @Override
    public Items findItem(String itemId, String restaurantId) throws Exception {
        Menu menu = menuRepositoryService.findMenu(restaurantId);
        if(menu!=null)
        {
           for(Items item:menu.getItems())
           {
              if(item.getItemId().equals(itemId))
              {
                 return item;
              }
              else
              {
                  log.info("Not matching item found for itemId:"+itemId);
                 throw new ItemNotFoundInRestaurantException("item not found in retaurant menu, restaurantId"+restaurantId+",itemId:"+itemId);
              }
           }
        }
        else{
            log.info("Menu not found for this restaurant"+restaurantId);
            throw new Exception("Menu not found!");
        }

       return new Items();
    }

    @Override
    public com.vkams.personal.lasyEats.Exchanges.getMenuResponse addItem(Items item, String restaurantId) throws Exception {
          String itemId = itemRepositoryService.addItem(item);
          com.vkams.personal.lasyEats.Exchanges.getMenuResponse menuResponse = new getMenuResponse();
          try{
              Menu menu = menuRepositoryService.addItemToMenu(itemId,restaurantId);
              menuResponse.setMenu(menu);
              menuResponse.setMenuResponseType(0);
          }
          catch (Exception e)
          {
             log.info("Unable to add item to menu. RestaurantId:"+restaurantId+",itemId"+itemId);
             throw e;
          }
          return  menuResponse;
    }

    @Override
    public com.vkams.personal.lasyEats.Exchanges.getMenuResponse removeItem(String itemId, String restaurantId) throws Exception {
        com.vkams.personal.lasyEats.Exchanges.getMenuResponse menuResponse = new getMenuResponse();
         try{
             Menu menu = menuRepositoryService.removeItemFromMenu(itemId,restaurantId);
             menuResponse.setMenu(menu);
             menuResponse.setMenuResponseType(0);
         }
         catch (Exception e)
         {
            if(e instanceof ItemNotFoundInRestaurantException)
            {
              menuResponse.setMenuResponseType(((ItemNotFoundInRestaurantException) e).getErrorType());
            }
            return menuResponse;
         }
        return menuResponse;
    }

    @Override
    public com.vkams.personal.lasyEats.Exchanges.getMenuResponse updateItem(Items item, String restaurantId) throws ItemNotFoundInRestaurantException {
        com.vkams.personal.lasyEats.Exchanges.getMenuResponse getMenuResponse = new getMenuResponse();
        Menu menu = menuRepositoryService.findMenu(restaurantId);
        if(menu!=null)
        {
          itemRepositoryService.addItem(item);
          menu = menuRepositoryService.updateItemInMenu(item,restaurantId);
          getMenuResponse.setMenu(menu);
          getMenuResponse.setMenuResponseType(0);

        }
        else
        {
           getMenuResponse.setMenuResponseType(new ItemNotFoundInRestaurantException("").getErrorType());
        }
        return getMenuResponse;
    }
}
