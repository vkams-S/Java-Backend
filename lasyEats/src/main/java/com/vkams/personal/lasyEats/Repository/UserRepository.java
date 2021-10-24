package com.vkams.personal.lasyEats.Repository;


import com.vkams.personal.lasyEats.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);

}
