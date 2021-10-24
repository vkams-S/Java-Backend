package com.vkams.personal.lasyEats.RepositoryService;

import com.vkams.personal.lasyEats.Repository.ItemRepository;
import com.vkams.personal.lasyEats.dto.Items;
import com.vkams.personal.lasyEats.model.ItemEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Provider;
import java.util.Optional;
@Service
public class ItemRepositoryServiceImpl implements ItemRepositoryService{
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    Provider<ModelMapper> modelMapperProvider;
    @Override
    public String addItem(Items item) {
        ModelMapper modelMapper = modelMapperProvider.get();
        ItemEntity itemEntity = modelMapper.map(item,ItemEntity.class);
        itemRepository.save(itemEntity);
        return itemEntity.getItemId();
    }

    @Override
    public Items findByItemId(String itemId) {
        ModelMapper modelMapper = modelMapperProvider.get();
        Optional<ItemEntity> OptionalItemEntity = itemRepository.findByItemId(itemId);
        Items item=null;
        if(OptionalItemEntity.isPresent())
        {
            item = modelMapper.map(OptionalItemEntity,Items.class);
        }
        return item;
    }
}
