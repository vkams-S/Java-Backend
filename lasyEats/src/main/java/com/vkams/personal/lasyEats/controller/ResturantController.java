package com.vkams.personal.lasyEats.controller;

import com.vkams.personal.lasyEats.Exchanges.*;
import com.vkams.personal.lasyEats.Repository.RestaurantRepository;
import com.vkams.personal.lasyEats.dto.*;
import com.vkams.personal.lasyEats.exceptions.CartNotFoundException;
import com.vkams.personal.lasyEats.exceptions.EmptyCartException;
import com.vkams.personal.lasyEats.exceptions.ItemNotFromSameRestaurant;
import com.vkams.personal.lasyEats.model.RestaurantEntity;
import com.vkams.personal.lasyEats.services.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalTime;



import static org.apache.commons.lang3.RandomStringUtils.random;

@RestController
@CrossOrigin("*")
@Log4j2
@RequestMapping(ResturantController.RESTAURANT_API_ENDPOINT)
public class ResturantController {
    public static final String RESTAURANT_API_ENDPOINT = "/lasyEats/v1";
    public static final String RESTAURANTS_API = "/restaurants";
    public static final String MENU_API = "/menu";
    public static final String MENU_ITEM_API = "/menu/item";
    public static final String MENU_ITEM_QUANTITY_API = "/item/available";
    public static final String CART_API = "/cart";
    public static final String CART_ITEM_API = "/cart/item";
    public static final String CART_CLEAR_API = "/cart/clear";
    public static final String POST_ORDER_API = "/order";
    public static final String GET_ORDER_API = "/orders";
    public static final String PLACED_ORDERS_API = "/orders/place";
    public static final String USER_LOGIN_API = "/user/login";

    @Autowired
    RestuarantService restuarantService;
    @Autowired
    MenuService menuService;
    @Autowired
    CartAndOrderService cartAndOrderService;
    @Autowired
    UserService userService;
    @Autowired
    QuantityService quantityService;
    @Autowired
    RestaurantRepository restaurantRepository;


    @GetMapping(RESTAURANTS_API)
    public ResponseEntity<getRestaurantsResponse> getRestaurants(getRestaurantsRequest getRestaurantsRequest) {
        Double longitude = getRestaurantsRequest.getLongitude();
        Double latitude = getRestaurantsRequest.getLatitude();
        log.info("getRestaurants called with {}", getRestaurantsRequest);
        getRestaurantsResponse getRestaurantsResponse = restuarantService.findAllRestaurantsCloseBy(getRestaurantsRequest, LocalTime.now());
        if (latitude == null || longitude == null || latitude < 0 || latitude > 90 || longitude < 0 || longitude > 180) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getRestaurantsResponse);
        } else if (getRestaurantsRequest.getSearchFor() != null && getRestaurantsRequest.getSearchFor() != "") {
            getRestaurantsResponse = restuarantService.findRestaurantsBySearchQuery(getRestaurantsRequest, LocalTime.now());
            return ResponseEntity.ok().body(getRestaurantsResponse);

        } else {

            return ResponseEntity.ok().body(getRestaurantsResponse);

        }

    }

    @GetMapping(MENU_API)
    public ResponseEntity<getMenuResponse> getMenu(@RequestParam("restaurantId") String restaurantId) throws Exception {
        getMenuResponse getMenuResponse = menuService.findMenu(restaurantId);

        log.info("getMenu returned with {}", getMenuResponse);

        return ResponseEntity.ok().body(getMenuResponse);
    }

    @PostMapping(MENU_ITEM_API)
    public ResponseEntity<getMenuResponse> addItem(@RequestBody addMenuRequest addMenuRequest) {

        getMenuResponse getMenuResponse = null;
        try {

            Items item = addMenuRequest.getItem();
            item.setItemId(random(10, true, true));
            String restaurantId = addMenuRequest.getRestaurantId();
            getMenuResponse = menuService.addItem(item, restaurantId);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }

        log.info("Add item returned");

        return ResponseEntity.ok().body(getMenuResponse);
    }

    @DeleteMapping(MENU_ITEM_API)
    public ResponseEntity<getMenuResponse> removeItem(
            @RequestBody removeMenuItemRequest removeMenuItemRequest) {

        getMenuResponse menuResponse = null;
        try {
            String itemId = removeMenuItemRequest.getItemId();
            String restaurantId = removeMenuItemRequest.getRestaurantId();
            menuResponse = menuService.removeItem(itemId, restaurantId);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok().body(menuResponse);
    }

    @PutMapping(MENU_ITEM_API)
    public ResponseEntity<getMenuResponse> editItem(@RequestBody addMenuRequest addMenuRequest) {
        Items item = addMenuRequest.getItem();
        String restaurantId = addMenuRequest.getRestaurantId();
        if (restaurantId == null || restaurantId.equals("")) {
            return ResponseEntity.badRequest().body(null);
        } else {
            getMenuResponse menuResponse = menuService.updateItem(item, restaurantId);
            return ResponseEntity.ok().body(menuResponse);
        }

    }

    @PutMapping(MENU_ITEM_QUANTITY_API)
    public ResponseEntity<ItemQuantity> editItemQuantity(@RequestBody editQuantityRequest editQuantityRequest) {

        ItemQuantity itemQuantity = null;
        try {
            String itemId = editQuantityRequest.getItemId();

            String restaurantId = editQuantityRequest.getRestaurantId();
            int quantity = editQuantityRequest.getQuantity();
            itemQuantity = quantityService.updateQuantity(itemId, restaurantId, quantity);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }

        if (itemQuantity == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok().body(itemQuantity);


    }

    @GetMapping(MENU_ITEM_QUANTITY_API)
    public ResponseEntity<ItemQuantity> getItemQuantity(
            @RequestParam("itemId") String itemId,
            @RequestParam("restaurantId") String restaurantId) {
        ItemQuantity itemQuantity = null;
        try {
            itemQuantity = quantityService.getQuantity(itemId, restaurantId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(itemQuantity);
    }

    @GetMapping(CART_API)
    public ResponseEntity<Cart> getCart(getCartRequest getCartRequest) {
        log.info("getRestaurants called with {}", getCartRequest);
        Cart getCartResponse = new Cart();
        String userId = getCartRequest.getUserId();
        if (userId == null || userId.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getCartResponse);
        }
        try {
            getCartResponse =
                    cartAndOrderService.createOrFindCart(getCartRequest.getUserId());
            return ResponseEntity.ok().body(getCartResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getCartResponse);
        }
    }

    @PostMapping(CART_ITEM_API)
    public ResponseEntity<cartModifiedResponse> addItem(@RequestBody addCartRequest addCartRequest) throws Exception {
        cartModifiedResponse cartModifiedResponse = new cartModifiedResponse();
        try {
            cartModifiedResponse =
                    cartAndOrderService.addItemToCart(addCartRequest.getItemId(), addCartRequest.getCartId(),
                            addCartRequest.getRestaurantId());
            return ResponseEntity.ok().body(cartModifiedResponse);
        } catch (CartNotFoundException | ItemNotFromSameRestaurant e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cartModifiedResponse);
        }
    }

    @DeleteMapping(CART_ITEM_API)
    public ResponseEntity<cartModifiedResponse> deleteItem(
            @RequestBody deleteCartRequest deleteCartRequest) throws Exception {
        cartModifiedResponse cartModifiedResponse =
                cartAndOrderService.removeItemFromCart(
                        deleteCartRequest.getItemId(), deleteCartRequest.getCartId(),
                        deleteCartRequest.getRestaurantId());
        return ResponseEntity.ok().body(cartModifiedResponse);

    }

    @PostMapping(POST_ORDER_API)
    public ResponseEntity<Order> placeOrder(@RequestBody postOrderRequest postOrderRequest) {
        String cartId = postOrderRequest.getCartId();
        Order order = new Order();
        try {
            order = cartAndOrderService.postOrder(cartId);
            return ResponseEntity.ok().body(order);
        } catch (CartNotFoundException | EmptyCartException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(order);
        }
    }

    @GetMapping(PLACED_ORDERS_API)
    public ResponseEntity<getOrderResponse> getAllPlacedOrders(
            @RequestParam("restaurantId") String restaurantId) {
        if (restaurantId == null || restaurantId.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            getOrderResponse getOrdersResponse =
                    cartAndOrderService.getAllPlacedOrders(restaurantId);
            return ResponseEntity.ok().body(getOrdersResponse);
        }

    }

    @GetMapping(GET_ORDER_API)
    public ResponseEntity<getOrderResponse> getAllOrders(
            @RequestParam("restaurantId") String restaurantId) {
        if (restaurantId == null || restaurantId.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            getOrderResponse getOrdersResponse =
                    cartAndOrderService.getAllOrders(restaurantId);
            return ResponseEntity.ok().body(getOrdersResponse);
        }

    }

    @PutMapping(PLACED_ORDERS_API)
    public ResponseEntity<getOrderResponse> updatePlacedOrders(
            @RequestBody updateOderStatusRequest updateOrderStatusRequest) {
        String restaurantId = updateOrderStatusRequest.getRestaurantId();
        String orderId = updateOrderStatusRequest.getOrderId();
        if (restaurantId == null || restaurantId.equals("")
                || orderId == null || orderId.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            getOrderResponse getOrdersResponse =
                    cartAndOrderService.updateStatusOfPlacedOrder(restaurantId, orderId,
                            updateOrderStatusRequest.getStatus());
            return ResponseEntity.ok().body(getOrdersResponse);
        }

    }

    @PutMapping(GET_ORDER_API)
    public ResponseEntity<getOrderResponse> updateOrderStatus(
            @RequestBody updateOderStatusRequest updateOrderStatusRequest) {
        String restaurantId = updateOrderStatusRequest.getRestaurantId();
        String orderId = updateOrderStatusRequest.getOrderId();
        if (restaurantId == null || restaurantId.equals("")
                || orderId == null || orderId.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            getOrderResponse getOrdersResponse =
                    cartAndOrderService.updateStatusOfOrder(restaurantId, orderId,
                            updateOrderStatusRequest.getStatus());
            return ResponseEntity.ok().body(getOrdersResponse);
        }

    }

    @PostMapping(USER_LOGIN_API)
    public ResponseEntity<userResponse> login(@RequestBody userLoginRequest userLoginRequest) {
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        if (username == null || username.equals("") || password == null || password.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.ok().body(userService.login(username, password));
        }

    }



}
