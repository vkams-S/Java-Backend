package com.vkams.personal.lasyEats.Repository;
import com.vkams.personal.lasyEats.model.MenuEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import java.util.List;
import java.util.Optional;
public  interface MenuRepository extends MongoRepository<MenuEntity,String>, QuerydslPredicateExecutor<MenuEntity> {
    Optional<MenuEntity> findMenuByRestaurantId(String restaurantId);

    Optional<List<MenuEntity>> findMenusByItemsItemIdIn(List<String> itemIdList);
}
