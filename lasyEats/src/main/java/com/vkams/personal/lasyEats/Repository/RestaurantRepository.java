package com.vkams.personal.lasyEats.Repository;
import com.vkams.personal.lasyEats.model.RestaurantEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends MongoRepository<RestaurantEntity, String>, QuerydslPredicateExecutor<RestaurantEntity> {
    @Query("{ 'name' : ?0 }")
    Optional<List<RestaurantEntity>> findRestaurantsByName(String name);
    @Query("{ 'attr' : ?0 }")
    Optional<List<RestaurantEntity>> findRestaurantsByAttributes(String attr);

}

