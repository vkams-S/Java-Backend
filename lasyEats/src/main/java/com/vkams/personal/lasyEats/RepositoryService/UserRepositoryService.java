package com.vkams.personal.lasyEats.RepositoryService;


import com.vkams.personal.lasyEats.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;


public interface UserRepositoryService {

  boolean checkLogin(String username, String password);

  String getUserRestaurant(String username) throws UserNotFoundException;
}
