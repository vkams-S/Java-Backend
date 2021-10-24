package com.vkams.personal.lasyEats.Repository;

import com.vkams.personal.lasyEats.dto.Cart;
import com.vkams.personal.lasyEats.model.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<CartEntity,String> {
    Optional<CartEntity> findByUserId(String userId);

}
