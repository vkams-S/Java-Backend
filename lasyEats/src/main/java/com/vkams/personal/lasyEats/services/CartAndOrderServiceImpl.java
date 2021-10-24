package com.vkams.personal.lasyEats.services;

import com.vkams.personal.lasyEats.Exchanges.cartModifiedResponse;
import com.vkams.personal.lasyEats.Exchanges.getCartRequest;
import com.vkams.personal.lasyEats.Exchanges.getOrderResponse;
import com.vkams.personal.lasyEats.RepositoryService.CartRepositoryService;
import com.vkams.personal.lasyEats.RepositoryService.OrderRepositoryService;
import com.vkams.personal.lasyEats.dto.Cart;
import com.vkams.personal.lasyEats.dto.Items;
import com.vkams.personal.lasyEats.dto.Order;
import com.vkams.personal.lasyEats.exceptions.CartNotFoundException;
import com.vkams.personal.lasyEats.exceptions.EmptyCartException;
import com.vkams.personal.lasyEats.exceptions.ItemNotFromSameRestaurant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class CartAndOrderServiceImpl implements CartAndOrderService{
    @Autowired
    CartRepositoryService cartRepositoryService;
    @Autowired
    MenuService menuService;
    @Autowired
    OrderRepositoryService orderRepositoryService;
    com.vkams.personal.lasyEats.Exchanges.cartModifiedResponse cartModifiedResponse = new cartModifiedResponse();
    @Override
    public Cart findCartByUserId(getCartRequest getCartRequest) {
        Optional<Cart> cart = cartRepositoryService.findCartByUserId(getCartRequest.getUserId());
        Cart cartPassed = null;
        if(cart.isPresent())
        {
            cartPassed=cart.get();
        }
        return cartPassed;
    }

    @Override
    public Cart createOrFindCart(String userId) throws CartNotFoundException {
        Optional<Cart> optionalCart = cartRepositoryService.findCartByUserId(userId);
        if(optionalCart.isPresent())
        {
           return optionalCart.get();

        }
        else
        {
           Cart newCart= new Cart();
           newCart.setRestaurantId("");
           newCart.setUserId(userId);
           cartRepositoryService.createCart(newCart);
           cartRepositoryService.findCartByCartId(newCart.getId());
           return newCart;
        }


    }

    @Override
    public com.vkams.personal.lasyEats.Exchanges.cartModifiedResponse addItemToCart(String itemId, String cartId, String restaurantId) throws Exception {
         com.vkams.personal.lasyEats.Exchanges.cartModifiedResponse cartModifiedResponse = new cartModifiedResponse();
         try
         {
              Cart cart = cartRepositoryService.findCartByCartId(cartId);
              if(cart.getRestaurantId().equals(restaurantId))
              {
                  Items item = menuService.findItem(itemId,restaurantId);
                  Cart updateCart = cartRepositoryService.addItem(item,cartId,restaurantId);
                  cartModifiedResponse.setCart(updateCart);
                  cartModifiedResponse.setCartResponseType(0);
              }
              else
              {
                  cartModifiedResponse.setCart(cart);
                  cartModifiedResponse.setCartResponseType(new ItemNotFromSameRestaurant().getErrorType());
              }
         } catch (CartNotFoundException c)
         {
             log.info("Cart Not found"+c);
            //System.out.println("Cart Not found"+c);
            throw new CartNotFoundException();
         }

        return cartModifiedResponse;
    }

    @Override
    public com.vkams.personal.lasyEats.Exchanges.cartModifiedResponse removeItemFromCart(String itemId, String cartId, String restaurantId) throws Exception {
         try{
             Items item = menuService.findItem(itemId,restaurantId);
             Cart updateCart = cartRepositoryService.removeItem(item,cartId,restaurantId);
             cartModifiedResponse.setCart(updateCart);
             cartModifiedResponse.setCartResponseType(0);
         }
         catch (ItemNotFromSameRestaurant i)
         {
             log.info("Item not from same Restaurant"+i);

         }
         catch (CartNotFoundException c)
         {
             log.info("Cart not found!"+c);
         }
        return cartModifiedResponse;
    }

    @Override
    public Order postOrder(String cartId) throws EmptyCartException {
        try
        {
          Cart cart = cartRepositoryService.findCartByCartId(cartId);
          if(!cart.getItems().isEmpty())
          {
            Order order = orderRepositoryService.placeOrder(cart);
            return order;
          }
          else
          {
            log.info("Cart is Empty!");
            throw new EmptyCartException();
          }
        }catch (CartNotFoundException c)
        {
            log.info("Cart Not Found!"+c);
            throw new CartNotFoundException();
        }

    }

    @Override
    public getOrderResponse getAllPlacedOrders(String restaurantId) {
        List<Order> orderList = orderRepositoryService.getOrdersByRestaurant(restaurantId);
        List<Order> finalOrderList = new ArrayList<>();
        if(orderList.size()>0)
        {
          for(Order order: orderList)
          {
            if(order.getStatus().equals("PLACED"))
            {
               finalOrderList.add(order);
            }
          }
        }
        return new getOrderResponse(finalOrderList,restaurantId);
    }

    @Override
    public getOrderResponse getAllOrders(String restaurantId) {

        List<Order> orderList = orderRepositoryService.getOrdersByRestaurant(restaurantId);
        List<Order> finalOrderList = new ArrayList<>();
        if(orderList.size()>0)
        {
            for(Order order: orderList)
            {
                if(order.getStatus().equals("PLACED")||order.getStatus().equals("PREPARING")||order.getStatus().equals("PACKED"))
                {
                    finalOrderList.add(order);
                }
            }
        }
        return new getOrderResponse(finalOrderList,restaurantId);
    }

    @Override
    public getOrderResponse updateStatusOfPlacedOrder(String restaurantId, String orderId, String status) {
        Order order = orderRepositoryService.getOrderById(orderId);
        if(order!=null && order.getStatus().equals("PLACED"))
        {
            if(order.getRestaurantId().equals(restaurantId) && !order.getStatus().equals(status))
            {
               orderRepositoryService.updateStatus(restaurantId,orderId,status);

            }


        }
        List<Order> orderList = orderRepositoryService.getOrdersByRestaurant(restaurantId);
        List<Order> UpdatedOrderList = new ArrayList<>();
        if(!orderList.isEmpty())
        {
          for(Order order1:orderList)
          {
             if(order1.getStatus().equals("ACCEPTED")||order1.getStatus().equals("PREPARING")||order1.getStatus().equals("PACKED"))
             {
                UpdatedOrderList.add(order1);
             }
          }
        }
        return new getOrderResponse(UpdatedOrderList,restaurantId);
    }

    @Override
    public getOrderResponse updateStatusOfOrder(String restaurantId, String orderId, String status) {

        Order order = orderRepositoryService.getOrderById(orderId);
        if(order!=null)
        {
            if(order.getRestaurantId().equals(restaurantId) && !order.getStatus().equals(status))
            {
                orderRepositoryService.updateStatus(restaurantId,orderId,status);

            }


        }
        List<Order> orderList = orderRepositoryService.getOrdersByRestaurant(restaurantId);
        List<Order> UpdatedOrderList = new ArrayList<>();
        if(!orderList.isEmpty())
        {
            for(Order order1:orderList)
            {
                if(order1.getStatus().equals("ACCEPTED")||order1.getStatus().equals("PREPARING")||order1.getStatus().equals("PACKED"))
                {
                    UpdatedOrderList.add(order1);
                }
            }
        }
        return new getOrderResponse(UpdatedOrderList,restaurantId);
    }
}
