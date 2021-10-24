package com.vkams.personal.lasyEats.RepositoryService;

import com.vkams.personal.lasyEats.Repository.QuantityRepository;
import com.vkams.personal.lasyEats.dto.ItemQuantity;
import com.vkams.personal.lasyEats.model.ItemEntity;
import com.vkams.personal.lasyEats.model.QuantityEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.stereotype.Service;

import javax.inject.Provider;
import java.util.Optional;
@Service
public class QuantityRespositoryServiceImpl implements QuantityRespositoryService{
    @Autowired
    QuantityRepository quantityRepository;
    @Autowired
    Provider<ModelMapper> modelMapperProvider;
    @Override
    public ItemQuantity updateQuantity(String itemId, String restaurantId, int quantity) throws Exception {
        ModelMapper modelMapper = modelMapperProvider.get();
        Optional<QuantityEntity> optionalQuantityEntity = quantityRepository.findByItemIdAndRestaurantId(itemId,restaurantId);
        ItemQuantity itemQuantity = null;
        if(optionalQuantityEntity.isPresent())
        {
            QuantityEntity quantityEntity = optionalQuantityEntity.get();
            quantityEntity.setQuantity(quantity);
            quantityRepository.save(quantityEntity);
            itemQuantity = modelMapper.map(quantityEntity,ItemQuantity.class);

        }
        else
        {
            throw new Exception("Either restaurantId or itemId is invalid!");
        }
        return itemQuantity;
    }

    @Override
    public ItemQuantity getQuantity(String itemId, String restaurantId) throws Exception {
        ModelMapper modelMapper = modelMapperProvider.get();
        ItemQuantity itemQuantity = null;
        Optional<QuantityEntity> optionalItemEntity = quantityRepository.findByItemIdAndRestaurantId(itemId,restaurantId);
        if(optionalItemEntity.isPresent())
        {
            QuantityEntity quantityEntity = optionalItemEntity.get();
            itemQuantity = modelMapper.map(quantityEntity,ItemQuantity.class);

        }
        else
        {
           throw new Exception("Either itemId or restaurantId is invalid!");
        }

        return itemQuantity;
    }
}
