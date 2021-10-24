package com.vkams.personal.lasyEats.services;

import com.vkams.personal.lasyEats.Exchanges.userResponse;

public interface UserService {
       userResponse login(String userName, String password);

}
