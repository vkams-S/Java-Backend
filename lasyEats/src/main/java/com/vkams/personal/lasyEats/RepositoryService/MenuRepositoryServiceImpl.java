package com.vkams.personal.lasyEats.RepositoryService;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.vkams.personal.lasyEats.Repository.ItemRepository;
import com.vkams.personal.lasyEats.Repository.MenuRepository;
import com.vkams.personal.lasyEats.Repository.QuantityRepository;
import com.vkams.personal.lasyEats.dto.Items;
import com.vkams.personal.lasyEats.dto.Menu;
import com.vkams.personal.lasyEats.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.inject.Provider;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
@Service
public class MenuRepositoryServiceImpl implements MenuRepositoryService{
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    QuantityRepository quantityRepository;
    @Autowired
    Provider<ModelMapper> modelMapperProvider;
    QMenuEntity qMenu = new QMenuEntity("menu");
    @Override
    public Menu findMenu(String restaurantId) {
        BooleanExpression filter = qMenu.restaurantId.eq(restaurantId);
        Optional<MenuEntity> menuEntity = menuRepository.findOne(filter);
        ModelMapper modelMapper = modelMapperProvider.get();
        //Optional<MenuEntity> optionalMenu = menuRepository.findMenuByRestaurantId(restaurantId);
        Menu menu =null;
        if(menuEntity.isPresent())
        {
            menu = modelMapper.map(menuEntity.get(),Menu.class);
        }
        return menu;
    }

    @Override
    public Menu addItemToMenu(String itemId, String restaurantId) throws Exception {
         ModelMapper modelMapper = modelMapperProvider.get();
        BooleanExpression filter = qMenu.restaurantId.eq(restaurantId);
         Optional<MenuEntity> optionalMenu = menuRepository.findOne(filter);
         Menu menu = null;
         if(optionalMenu.isPresent())
         {
            MenuEntity menuEntity = modelMapper.map(optionalMenu.get(),MenuEntity.class);
            Optional<ItemEntity> optionalItem = itemRepository.findByItemId(itemId);
            if(optionalItem.isPresent())
            {
              Items item = modelMapper.map(optionalItem.get(),Items.class);
              menuEntity.addItem(item);
              menuRepository.save(menuEntity);
              menu = modelMapper.map(menuEntity,Menu.class);
              QuantityEntity quantityEntity = new QuantityEntity();
              quantityEntity.setItemId(itemId);
              quantityEntity.setRestaurantId(restaurantId);
              quantityEntity.setQuantity(0);
              quantityRepository.save(quantityEntity);

            }

         }
         else {
             throw new Exception("Invalid restaurant id");
         }
        return menu;
    }

    @Override
    public Menu removeItemFromMenu(String itemId, String restaurantId) throws Exception {
        ModelMapper modelMapper = modelMapperProvider.get();
        BooleanExpression filter = qMenu.restaurantId.eq(restaurantId);
        Optional<MenuEntity> optionalMenu = menuRepository.findOne(filter);
        Menu menu=null;
        if(optionalMenu.isPresent())
        {
            MenuEntity menuEntity = modelMapper.map(optionalMenu.get(),MenuEntity.class);
            Items itemTobeDeleted = null;
            for(Items itemInMenu:menuEntity.getItems())
            {
                if(itemId.equals(itemInMenu.getItemId()))
                {
                    itemTobeDeleted = itemInMenu;
                }
            }
            if(itemTobeDeleted==null)
            {
                throw new Exception("Item not found in Menu!");
            }
            menuEntity.removeItem(itemTobeDeleted);
            menuRepository.save(menuEntity);
            menu = modelMapper.map(menuEntity,Menu.class);
        }
        else
        {
            throw new Exception("Invalid restaurant id!");
        }
        return menu;
    }

    @Override
    public Menu updateItemInMenu(Items item, String restaurantId) {
         ModelMapper modelMapper = modelMapperProvider.get();
        BooleanExpression filter = qMenu.restaurantId.eq(restaurantId);
         Optional<MenuEntity> optionalMenu = menuRepository.findOne(filter);
         Menu menu = null;
         if(optionalMenu.isPresent())
         {
             MenuEntity menuEntity = modelMapper.map(optionalMenu.get(),MenuEntity.class);
             int i=0;
             for(Items itemsinMenu:menuEntity.getItems())
             {
                 if(item.getItemId().equals(itemsinMenu.getItemId()))
                 {
                    itemsinMenu.setItemId(item.getItemId());
                    itemsinMenu.setName(item.getName());
                    itemsinMenu.setAttributes(item.getAttributes());
                    itemsinMenu.setPrice(item.getPrice());
                    itemsinMenu.setImageUrl(item.getImageUrl());
                    menuEntity.getItems().set(i,itemsinMenu);
                    break;
                 }
                 i++;
             }
             menuRepository.save(menuEntity);
             menu = modelMapper.map(menuEntity,Menu.class);
         }
        return menu;
    }
}
