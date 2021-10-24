package com.vkams.personal.lasyEats.RepositoryService;

import com.vkams.personal.lasyEats.Repository.UserRepository;
import com.vkams.personal.lasyEats.exceptions.UserNotFoundException;
import com.vkams.personal.lasyEats.model.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.inject.Provider;
import java.util.Optional;
@Service
public class UserRepositoryServiceImpl implements UserRepositoryService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    Provider<ModelMapper> modelMapperProvider;
    @Override
    public boolean checkLogin(String username, String password) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);
        if(optionalUserEntity.isPresent())
        {
            UserEntity userEntity = optionalUserEntity.get();
            if(userEntity.getPassword().equals(password))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getUserRestaurant(String username) throws UserNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);
        if(optionalUserEntity.isPresent())
        {
            UserEntity userEntity = optionalUserEntity.get();
            return userEntity.getRestaurantId();
        }
        else
        {
            throw new UserNotFoundException();
        }
    }
}
