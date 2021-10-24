package com.vkams.personal.lasyEats.RepositoryService;

import ch.hsr.geohash.GeoHash;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.vkams.personal.lasyEats.Localutil.GeoLocation;
import com.vkams.personal.lasyEats.Localutil.Geoutil;
import com.vkams.personal.lasyEats.Repository.RestaurantRepository;
import com.vkams.personal.lasyEats.configurations.RedisConfiguration;
import com.vkams.personal.lasyEats.dto.Restaurant;
import com.vkams.personal.lasyEats.model.MenuEntity;
import com.vkams.personal.lasyEats.model.QRestaurantEntity;
import com.vkams.personal.lasyEats.model.RestaurantEntity;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import javax.inject.Provider;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
@Log4j2
@Service
public class RestaurantRepositoryServiceImpl implements RestaurantRepositoryService{
    public boolean isOpenNow(LocalTime time, RestaurantEntity restaurantEntity)
    {
         LocalTime openingTime = LocalTime.parse(restaurantEntity.getOpensAt());
         LocalTime closingTime = LocalTime.parse(restaurantEntity.getClosesAt());
         return time.isAfter(openingTime) && time.isBefore(closingTime);
    }

    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    RedisConfiguration redisConfiguration;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    Provider<ModelMapper> modelMapperProvider;
    QRestaurantEntity qRest = new QRestaurantEntity("rest");
    @Override
    public List<Restaurant> findAllRestaurantsCloseBy(double latitude, double longitude, LocalTime currentTime, double serviceRadius) {
       //check if redis first

        List<Restaurant> restaurantList=null;
        if(!redisConfiguration.isCacheAvailable())
        {
           restaurantList = findAllRestaurantsCloseByFromCache(latitude,longitude,currentTime,serviceRadius);
        }
        else
        {
            //if not available in cache then retrieve it from MongoDb and update cache entry
            restaurantList = findAllRestaurantsCloseByFromMongoDb(latitude,longitude,currentTime,serviceRadius);
        }
        return restaurantList;
    }

    //Helper functions for findAllRestaurantCloseBy

    public List<Restaurant> findAllRestaurantsCloseByFromCache(double latitude,double longitude,LocalTime currentTime,double serviceRadius)
    {
          List<Restaurant> restaurantList = new ArrayList<>();
          GeoLocation geoLocation = new GeoLocation(latitude,longitude);
        GeoHash geoHash = GeoHash.withCharacterPrecision(geoLocation.getLatitude(),geoLocation.getLongitude(),7);
        try(Jedis jedis = redisConfiguration.getJedisPool().getResource())
        {
           String JsonStringFromCache = jedis.get(geoHash.toBase32());
           if(JsonStringFromCache==null)
           {
              //we need to find the data from MongoDb and update the cache
               String createJson = "";
               try{
                   restaurantList =findAllRestaurantsCloseByFromMongoDb(geoLocation.getLatitude(),geoLocation.getLongitude(),currentTime,serviceRadius);
                   createJson = new ObjectMapper().writeValueAsString(restaurantList);
               }catch (Exception e)
               {
                  e.printStackTrace();
               }
               jedis.setex(geoHash.toBase32(),Long.valueOf(redisConfiguration.REDIS_ENTRY_EXPIRY_IN_SEC),createJson);

           }
           else
           {
              try{
                  restaurantList =new ObjectMapper().readValue(JsonStringFromCache, new TypeReference<List<Restaurant>>() {});

              }catch (Exception e)
              {
                  e.printStackTrace();
              }
           }
           return restaurantList;
        }
    }


    List<Restaurant> findAllRestaurantsCloseByFromMongoDb(double latitude,double longitude,LocalTime currentTime,double serviceRadius)
    {
        ModelMapper  modelMapper =modelMapperProvider.get();
        List<RestaurantEntity>restaurantEntities = restaurantRepository.findAll();
        log.info("Return of repository service:"+restaurantEntities.size());
        List<Restaurant> restaurantList = new ArrayList<>();

        for(RestaurantEntity restaurantEntity:restaurantEntities)
        {
            if(isRestaurantCloseByAndOpen(restaurantEntity,currentTime,latitude,longitude,serviceRadius))
            {
                restaurantList.add(modelMapper.map(restaurantEntity,Restaurant.class));

            }
        }
        return restaurantList;
    }

    private boolean isRestaurantCloseByAndOpen(RestaurantEntity restaurantEntity, LocalTime currentTime, double latitude, double longitude,double serviceRadius) {
        if(isOpenNow(currentTime,restaurantEntity))
        {
            return Geoutil.findDistanceInKms(latitude,longitude,restaurantEntity.getLatitude(),restaurantEntity.getLongitude()) < serviceRadius;
        }
        return false;
    }


    @Override
    public List<Restaurant> findRestaurantsByName(double latitude, double longitude, String searchQuery, LocalTime currentTime, double serviceRadius) {
          // Optional<List<RestaurantEntity>> restaurantEntities = restaurantRepository.findRestaurantsByName(searchQuery);
           BooleanExpression filter=qRest.name.contains(searchQuery);
           List<RestaurantEntity> restaurantEntities = (List<RestaurantEntity>) restaurantRepository.findAll(filter);
           ModelMapper modelMapper = modelMapperProvider.get();
           List<Restaurant> restaurantList = new ArrayList<>();
           //if(restaurantEntities.isPresent())
           //{
             //  List<RestaurantEntity> restaurantEntity = restaurantEntities.get();
               for(RestaurantEntity restaurant:restaurantEntities)
               {
                   if(isRestaurantCloseByAndOpen(restaurant,currentTime,latitude,longitude,serviceRadius))
                   {
                       restaurantList.add(modelMapper.map(restaurant,Restaurant.class));
                   }
             //  }
           }
        return restaurantList;
    }

    @Async
    @Override
    public CompletableFuture<List<Restaurant>> findRestaurantsByNameAsync(double latitude, double longitude, String searchQuery, LocalTime currentTime, double serviceRadius) {
        Optional<List<RestaurantEntity>> restaurantEntities = restaurantRepository.findRestaurantsByName(searchQuery);
        ModelMapper modelMapper = modelMapperProvider.get();
        List<Restaurant> restaurantList = new ArrayList<>();
        if(restaurantEntities.isPresent())
        {
            List<RestaurantEntity> restaurantEntity = restaurantEntities.get();
            for(RestaurantEntity restaurant:restaurantEntity)
            {
                if(isRestaurantCloseByAndOpen(restaurant,currentTime,latitude,longitude,serviceRadius))
                {
                    restaurantList.add(modelMapper.map(restaurant,Restaurant.class));
                }
            }
        }
        return CompletableFuture.completedFuture(restaurantList);
    }

    @Override
    public List<Restaurant> findRestaurantsByAttributes(double latitude, double longitude, String searchQuery, LocalTime currentTime, double serviceRadius) {

        BooleanExpression filterByAttr = qRest.attributes.contains(searchQuery);

        List<RestaurantEntity> restaurantEntities = (List<RestaurantEntity>) restaurantRepository.findAll(filterByAttr);
        ModelMapper modelMapper = modelMapperProvider.get();
        List<Restaurant> restaurantList = new ArrayList<>();
        if(!restaurantEntities.isEmpty())
        {

            for(RestaurantEntity restaurant:restaurantEntities)
            {
                if(isRestaurantCloseByAndOpen(restaurant,currentTime,latitude,longitude,serviceRadius))
                {
                    restaurantList.add(modelMapper.map(restaurant,Restaurant.class));
                }
            }
        }
        return restaurantList;
    }
    @Async
    @Override
    public CompletableFuture<List<Restaurant>> findRestaurantsByAttributesAsync(double latitude, double longitude, String searchQuery, LocalTime currentTime, double serviceRadius) {
        Optional<List<RestaurantEntity>> restaurantEntities = restaurantRepository.findRestaurantsByAttributes(searchQuery);
        ModelMapper modelMapper = modelMapperProvider.get();
        List<Restaurant> restaurantList = new ArrayList<>();
        if(restaurantEntities.isPresent())
        {
            List<RestaurantEntity> restaurantEntity = restaurantEntities.get();
            for(RestaurantEntity restaurant:restaurantEntity)
            {
                if(isRestaurantCloseByAndOpen(restaurant,currentTime,latitude,longitude,serviceRadius))
                {
                    restaurantList.add(modelMapper.map(restaurant,Restaurant.class));
                }
            }
        }
        return CompletableFuture.completedFuture(restaurantList);
    }

    @Override
    public List<Restaurant> findRestaurantsByItemName(double latitude, double longitude, String searchQuery, LocalTime currentTime, double serviceRadius) {
        ModelMapper modelMapper= modelMapperProvider.get();
        BasicQuery query = new BasicQuery("{'items.name': {$regex: /" + searchQuery + "/i}}");
        List<MenuEntity> menus = mongoTemplate.find(query, MenuEntity.class, "menus");
        List<RestaurantEntity> restaurants = new ArrayList<>();
        for (MenuEntity menu : menus) {
            String restaurantId = menu.getRestaurantId();
            BasicQuery restaurantQuery = new BasicQuery("{restaurantId:" + restaurantId + "}");
            restaurants.add(mongoTemplate
                    .findOne(restaurantQuery, RestaurantEntity.class, "restaurants"));
        }
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();

        for (RestaurantEntity restaurant : restaurants) {

            if(isRestaurantCloseByAndOpen(restaurant, currentTime, latitude, longitude, serviceRadius))
            {
                restaurantList.add(modelMapper.map(restaurant,Restaurant.class));
            }

        }
        return restaurantList;
    }
    @Async
    @Override
    public CompletableFuture<List<Restaurant>> findRestaurantsByItemNameAsync(double latitude, double longitude, String searchQuery, LocalTime currentTime, double serviceRadius) {
        ModelMapper modelMapper= modelMapperProvider.get();
        BasicQuery query = new BasicQuery("{'items.name': {$regex: /" + searchQuery + "/i}}");
        List<MenuEntity> menus = mongoTemplate.find(query, MenuEntity.class, "menus");
        List<RestaurantEntity> restaurants = new ArrayList<>();
        for (MenuEntity menu : menus) {
            String restaurantId = menu.getRestaurantId();
            BasicQuery restaurantQuery = new BasicQuery("{restaurantId:" + restaurantId + "}");
            restaurants.add(mongoTemplate
                    .findOne(restaurantQuery, RestaurantEntity.class, "restaurants"));
        }
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();

        for (RestaurantEntity restaurant : restaurants) {

            if(isRestaurantCloseByAndOpen(restaurant, currentTime, latitude, longitude, serviceRadius))
            {
                restaurantList.add(modelMapper.map(restaurant,Restaurant.class));
            }

        }
        return CompletableFuture.completedFuture(restaurantList);
    }

    @Override
    public List<Restaurant> findRestaurantsByItemAttributes(double latitude, double longitude, String searchQuery, LocalTime currentTime, double serviceRadius) {
        ModelMapper modelMapper= modelMapperProvider.get();
        BasicQuery query = new BasicQuery("{'items.attributes': {$regex: /" + searchQuery + "/i}}");
        List<MenuEntity> menus = mongoTemplate.find(query, MenuEntity.class, "menus");
        List<RestaurantEntity> restaurants = new ArrayList<>();
        for (MenuEntity menu : menus) {
            String restaurantId = menu.getRestaurantId();
            BasicQuery restaurantQuery = new BasicQuery("{restaurantId:" + restaurantId + "}");
            restaurants.add(mongoTemplate
                    .findOne(restaurantQuery, RestaurantEntity.class, "restaurants"));
        }
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();

        for (RestaurantEntity restaurant : restaurants) {
            if(isRestaurantCloseByAndOpen(restaurant, currentTime, latitude, longitude, serviceRadius))
            {
                restaurantList.add(modelMapper.map(restaurant,Restaurant.class));
            }
        }
        return restaurantList;
    }
    @Async
    @Override
    public CompletableFuture<List<Restaurant>> findRestaurantsByItemAttributesAsync(double latitude, double longitude, String searchQuery, LocalTime currentTime, double serviceRadius) {
        ModelMapper modelMapper= modelMapperProvider.get();
        BasicQuery query = new BasicQuery("{'items.attributes': {$regex: /" + searchQuery + "/i}}");
        List<MenuEntity> menus = mongoTemplate.find(query, MenuEntity.class, "menus");
        List<RestaurantEntity> restaurants = new ArrayList<>();
        for (MenuEntity menu : menus) {
            String restaurantId = menu.getRestaurantId();
            BasicQuery restaurantQuery = new BasicQuery("{restaurantId:" + restaurantId + "}");
            restaurants.add(mongoTemplate
                    .findOne(restaurantQuery, RestaurantEntity.class, "restaurants"));
        }
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();

        for (RestaurantEntity restaurant : restaurants) {
            if(isRestaurantCloseByAndOpen(restaurant, currentTime, latitude, longitude, serviceRadius))
            {
                restaurantList.add(modelMapper.map(restaurant,Restaurant.class));
            }
        }
        return CompletableFuture.completedFuture(restaurantList);
    }


}
