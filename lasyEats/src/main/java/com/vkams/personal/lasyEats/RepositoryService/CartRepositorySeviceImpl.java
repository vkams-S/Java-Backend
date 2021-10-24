package com.vkams.personal.lasyEats.RepositoryService;

import com.vkams.personal.lasyEats.Repository.CartRepository;
import com.vkams.personal.lasyEats.dto.Cart;
import com.vkams.personal.lasyEats.dto.Items;
import com.vkams.personal.lasyEats.exceptions.CartNotFoundException;
import com.vkams.personal.lasyEats.model.CartEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Provider;

import java.util.List;
import java.util.Optional;
@Service
public class CartRepositorySeviceImpl implements CartRepositoryService{
    @Autowired
    Provider<ModelMapper> modelMapperProvider;
    @Autowired
    CartRepository cartRepository;
    @Override
    public String createCart(Cart cart) {
       ModelMapper modelMapper = modelMapperProvider.get();
       CartEntity cartEntity =modelMapper.map(cart,CartEntity.class);
       cartRepository.save(cartEntity);
       return cart.getRestaurantId();

    }

    @Override
    public Optional<Cart> findCartByUserId(String userId) {
        ModelMapper modelMapper = modelMapperProvider.get();
        Optional<CartEntity> optionalCartEntity =cartRepository.findByUserId(userId);
        Optional<Cart> optionalCart =Optional.empty();
        Cart cart= null;
        if(optionalCartEntity.isPresent())
        {
          cart = modelMapper.map(optionalCartEntity.get(),Cart.class);
          optionalCart = Optional.of(cart);
        }
        else
        {
            throw new CartNotFoundException();
        }

        return optionalCart;
    }

    @Override
    public Cart findCartByCartId(String cartId) throws CartNotFoundException {
        ModelMapper modelMapper = modelMapperProvider.get();
        Optional<CartEntity> cartEntity =cartRepository.findById(cartId);
        Cart cart = null;
        if(cartEntity.isPresent())
        {
           cart = modelMapper.map(cartEntity.get(),Cart.class);
        }
        else
        {
           throw new CartNotFoundException();
        }
        return cart;
    }

    @Override
    public Cart addItem(Items item, String cartId, String restaurantId) throws CartNotFoundException {
        ModelMapper modelMapper = modelMapperProvider.get();
        Optional<CartEntity> optinalcartEntity = cartRepository.findById(cartId);
        Cart cart = null;
        if(optinalcartEntity.isPresent())
        {
            CartEntity cartEntity = optinalcartEntity.get();
            cartEntity.addItems(item);
            if(cartEntity.getItems().size() == 1)
            {
               cartEntity.setRestaurantId(restaurantId);
            }
            cartRepository.save(cartEntity);
            cart = modelMapper.map(cartEntity,Cart.class);
        }
        else
        {
            throw new CartNotFoundException();
        }
        return cart;
    }

    @Override
    public Cart removeItem(Items item, String cartId, String restaurantId) throws CartNotFoundException {
        ModelMapper modelMapper = modelMapperProvider.get();
        Optional<CartEntity> optionalCartEntity = cartRepository.findById(cartId);
        Cart cart=null;
        if(optionalCartEntity.isPresent())
        {
           CartEntity cartEntity = optionalCartEntity.get();
            List<Items> itemsList = cartEntity.getItems();
            for(Items itemIncart:itemsList)
            {
              if(item.getItemId().equals(itemIncart.getItemId()))
              {
                 cartEntity.removeItems(item);
                 break;
              }
            }
          if(cartEntity.getItems().size()==0)
          {
            cartEntity.setRestaurantId("");
          }
          cartRepository.save(cartEntity);
          cart = modelMapper.map(cartEntity,Cart.class);
        }
        else
        {
            throw new CartNotFoundException();
        }
        return cart;
    }
}
