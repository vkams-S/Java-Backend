package com.vkams.personal.lasyEats.RepositoryService;

import com.vkams.personal.lasyEats.dto.Restaurant;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RestaurantRepositoryService {
    List<Restaurant> findAllRestaurantsCloseBy(double latitude, double longitude, LocalTime currentTime, double serviceRadius);
    List<Restaurant> findRestaurantsByName(double latitude,double longitude,String searchQuery,LocalTime currentTime,double serviceRadius);
    @Async
    CompletableFuture<List<Restaurant>> findRestaurantsByNameAsync(double latitude, double longitude,String searchQuery,LocalTime currentTime, double serviceRadius);
    List<Restaurant> findRestaurantsByAttributes(double latitude, double longitude,String searchQuery,LocalTime currentTime, double serviceRadius);
    @Async
    CompletableFuture<List<Restaurant>> findRestaurantsByAttributesAsync(double latitude, double longitude,String searchQuery,LocalTime currentTime, double serviceRadius);
    List<Restaurant> findRestaurantsByItemName(double latitude, double longitude,String searchQuery,LocalTime currentTime, double serviceRadius);
    @Async
    CompletableFuture<List<Restaurant>>findRestaurantsByItemNameAsync(double latitude, double longitude,String searchQuery,LocalTime currentTime, double serviceRadius);
    List<Restaurant> findRestaurantsByItemAttributes(double latitude, double longitude, String searchQuery, LocalTime currentTime, double serviceRadius);
    @Async
    CompletableFuture<List<Restaurant>> findRestaurantsByItemAttributesAsync(double latitude, double longitude, String searchQuery, LocalTime currentTime, double serviceRadius);
}
