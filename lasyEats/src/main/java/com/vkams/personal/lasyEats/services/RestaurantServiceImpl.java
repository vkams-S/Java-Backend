package com.vkams.personal.lasyEats.services;

import com.vkams.personal.lasyEats.Exchanges.getRestaurantsRequest;
import com.vkams.personal.lasyEats.Exchanges.getRestaurantsResponse;
import com.vkams.personal.lasyEats.RepositoryService.RestaurantRepositoryService;
import com.vkams.personal.lasyEats.dto.Restaurant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
@Service
@Log4j2
public class RestaurantServiceImpl implements RestuarantService{
    private final double peakHourServiceRadiusInKms =3.0;
    private final double normalHourServiceRadiusInKms = 5.0;
    @Autowired
    RestaurantRepositoryService restaurantRepositoryService;
    @Override
    public getRestaurantsResponse findAllRestaurantsCloseBy(getRestaurantsRequest getRestaurantsRequest, LocalTime currentTime) {
        List<Restaurant> restaurantList= new ArrayList<>();
        int h = currentTime.getHour();
        int m=currentTime.getMinute();
        if((h >= 8 && h <= 9) || (h == 10 && m == 0) || (h == 13) || (h == 14 && m == 0)
                || (h >= 19 && h <= 20) || (h == 21 && m == 0))
        {
            restaurantList =restaurantRepositoryService.findAllRestaurantsCloseBy(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), currentTime,peakHourServiceRadiusInKms);
        }
        else
        {
            restaurantList=restaurantRepositoryService.findAllRestaurantsCloseBy(getRestaurantsRequest.getLatitude(),getRestaurantsRequest.getLongitude(),currentTime,normalHourServiceRadiusInKms);
        }
        getRestaurantsResponse restaurantsResponse = new getRestaurantsResponse(restaurantList);
        log.info(restaurantsResponse);
        return restaurantsResponse;
    }

    @Override
    public getRestaurantsResponse findRestaurantsBySearchQuery(getRestaurantsRequest getRestaurantsRequest, LocalTime currentTime) {
        List<Restaurant> restaurantList;
        int h = currentTime.getHour();
        int m=currentTime.getMinute();
        if((h >= 8 && h <= 9) || (h == 10 && m == 0) || (h == 13) || (h == 14 && m == 0)
                || (h >= 19 && h <= 20) || (h == 21 && m == 0))
        {
            restaurantList =restaurantRepositoryService.
                    findRestaurantsByName(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,peakHourServiceRadiusInKms);
          //  restaurantList.addAll(restaurantRepositoryService.findRestaurantsByAttributes(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,peakHourServiceRadiusInKms));
           // restaurantList.addAll(restaurantRepositoryService.findRestaurantsByItemName(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,peakHourServiceRadiusInKms));
          //  restaurantList.addAll(restaurantRepositoryService.findRestaurantsByItemAttributes(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,peakHourServiceRadiusInKms));
        }
        else
        {
            restaurantList=restaurantRepositoryService.
                    findRestaurantsByName(getRestaurantsRequest.getLatitude(),getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,normalHourServiceRadiusInKms);
          //  restaurantList.addAll(restaurantRepositoryService.findRestaurantsByAttributes(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,normalHourServiceRadiusInKms));
            //restaurantList.addAll(restaurantRepositoryService.findRestaurantsByItemName(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,normalHourServiceRadiusInKms));
            //restaurantList.addAll(restaurantRepositoryService.findRestaurantsByItemAttributes(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,normalHourServiceRadiusInKms));
        }
        getRestaurantsResponse restaurantsResponse = new getRestaurantsResponse(restaurantList);
        log.info(restaurantsResponse);
        return restaurantsResponse;
    }

    @Override
    public getRestaurantsResponse findRestaurantsBySerachQueryConcurrent(getRestaurantsRequest getRestaurantsRequest, LocalTime currentTime) throws InterruptedException, ExecutionException {
        List<Restaurant> restaurantList;
        int h = currentTime.getHour();
        int m=currentTime.getMinute();
        if((h >= 8 && h <= 9) || (h == 10 && m == 0) || (h == 13) || (h == 14 && m == 0)
                || (h >= 19 && h <= 20) || (h == 21 && m == 0))
        {
            restaurantList = (List<Restaurant>) restaurantRepositoryService.
                    findRestaurantsByNameAsync(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,peakHourServiceRadiusInKms);
            restaurantList.addAll((Collection<? extends Restaurant>) restaurantRepositoryService.findRestaurantsByAttributesAsync(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,peakHourServiceRadiusInKms));
            restaurantList.addAll((Collection<? extends Restaurant>) restaurantRepositoryService.findRestaurantsByItemNameAsync(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,peakHourServiceRadiusInKms));
            restaurantList.addAll((Collection<? extends Restaurant>) restaurantRepositoryService.findRestaurantsByItemAttributesAsync(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,peakHourServiceRadiusInKms));
        }
        else
        {
            restaurantList= (List<Restaurant>) restaurantRepositoryService.
                    findRestaurantsByNameAsync(getRestaurantsRequest.getLatitude(),getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,normalHourServiceRadiusInKms);
            restaurantList.addAll((Collection<? extends Restaurant>) restaurantRepositoryService.findRestaurantsByAttributesAsync(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,normalHourServiceRadiusInKms));
            restaurantList.addAll((Collection<? extends Restaurant>) restaurantRepositoryService.findRestaurantsByItemNameAsync(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,normalHourServiceRadiusInKms));
            restaurantList.addAll((Collection<? extends Restaurant>) restaurantRepositoryService.findRestaurantsByItemAttributesAsync(getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), getRestaurantsRequest.getSearchFor(), currentTime,normalHourServiceRadiusInKms));
        }
        getRestaurantsResponse restaurantsResponse = new getRestaurantsResponse(restaurantList);
        log.info(restaurantsResponse);
        return restaurantsResponse;
    }
}
