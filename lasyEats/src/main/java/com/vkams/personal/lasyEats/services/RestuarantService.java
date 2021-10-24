package com.vkams.personal.lasyEats.services;

import com.vkams.personal.lasyEats.Exchanges.getRestaurantsRequest;
import com.vkams.personal.lasyEats.Exchanges.getRestaurantsResponse;

import java.time.LocalTime;
import java.util.concurrent.ExecutionException;

public interface RestuarantService {
     getRestaurantsResponse findAllRestaurantsCloseBy(getRestaurantsRequest getRestaurantsRequest, LocalTime currentTime);
     getRestaurantsResponse findRestaurantsBySearchQuery(getRestaurantsRequest getRestaurantsRequest, LocalTime currentTime);
     getRestaurantsResponse findRestaurantsBySerachQueryConcurrent(getRestaurantsRequest getRestaurantsRequest, LocalTime currentTime) throws InterruptedException, ExecutionException;
}
