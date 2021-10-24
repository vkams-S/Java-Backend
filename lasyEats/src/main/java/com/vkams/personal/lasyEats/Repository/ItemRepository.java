package com.vkams.personal.lasyEats.Repository;

import com.vkams.personal.lasyEats.model.ItemEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface ItemRepository extends MongoRepository<ItemEntity,String> {

    Optional<ItemEntity> findByItemId(String itemId);
}
