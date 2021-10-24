/*
 *
 *  * Copyright (c) Crio.Do 2019. All rights reserved
 *
 */

package com.vkams.personal.lasyEats.Repository;

import com.vkams.personal.lasyEats.model.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<OrderEntity, String> {
    Optional<List<OrderEntity>> findByRestaurantId(String restaurantId);
}