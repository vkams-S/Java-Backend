package com.vkams.personal.lasyEats.Repository;


import com.vkams.personal.lasyEats.model.QuantityEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface QuantityRepository extends MongoRepository<QuantityEntity,String> {
 Optional<QuantityEntity> findByItemIdAndRestaurantId(String itemId,String restaurantId);
}
