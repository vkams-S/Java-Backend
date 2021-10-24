package com.vkams.personal.lasyEats.services;

import com.vkams.personal.lasyEats.Exchanges.userResponse;
import com.vkams.personal.lasyEats.Repository.UserRepository;
import com.vkams.personal.lasyEats.RepositoryService.UserRepositoryService;
import com.vkams.personal.lasyEats.dto.User;
import com.vkams.personal.lasyEats.exceptions.UserNotFoundException;
import com.vkams.personal.lasyEats.model.UserEntity;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Provider;


@Service
@Log4j2
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepositoryService userRepositoryService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Provider<ModelMapper> modelMapperProvider;
    @Override
    public userResponse login(String userName, String password) {
        try {
           if(userRepositoryService.checkLogin(userName,password))
           {
              return new userResponse(userRepositoryService.getUserRestaurant(userName),0);
           }
           else {
               return new userResponse("",new UserNotFoundException().getErrorType());
           }

        }catch (Exception e)
        {
             return new userResponse("",new UserNotFoundException().getErrorType());

        }

    }


}
