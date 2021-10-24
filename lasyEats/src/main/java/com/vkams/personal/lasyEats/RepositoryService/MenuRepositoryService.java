

package com.vkams.personal.lasyEats.RepositoryService;


import com.vkams.personal.lasyEats.dto.Items;
import com.vkams.personal.lasyEats.dto.Menu;
import org.springframework.stereotype.Service;
@Service
public interface MenuRepositoryService {


  Menu findMenu(String restaurantId);
  Menu addItemToMenu(String itemId, String restaurantId) throws Exception;

  Menu removeItemFromMenu(String itemId, String restaurantId) throws Exception;

  Menu updateItemInMenu(Items item, String restaurantId);
}
