package com.vkams.personal.lasyEats.RepositoryService;


import com.vkams.personal.lasyEats.dto.Items;
import org.springframework.stereotype.Service;

public interface ItemRepositoryService {

  String addItem(Items item);

  Items findByItemId(String itemId);
}
