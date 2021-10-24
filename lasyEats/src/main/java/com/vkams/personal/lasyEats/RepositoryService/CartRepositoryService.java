package com.vkams.personal.lasyEats.RepositoryService;

import com.vkams.personal.lasyEats.dto.Cart;
import com.vkams.personal.lasyEats.dto.Items;
import com.vkams.personal.lasyEats.exceptions.CartNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;


public interface CartRepositoryService {
    @NotNull String createCart(Cart cart);
    Optional<Cart> findCartByUserId(String userId);
    Cart findCartByCartId(String cartId) throws CartNotFoundException;
    Cart addItem(Items item, String cartId, String restaurantId) throws CartNotFoundException;
    Cart removeItem(Items item,String cartId,String restaurantId) throws CartNotFoundException;

}
