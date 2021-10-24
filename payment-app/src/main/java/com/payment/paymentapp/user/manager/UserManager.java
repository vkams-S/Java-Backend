package com.payment.paymentapp.user.manager;

import com.payment.paymentapp.exception.NotFoundException;
import com.payment.paymentapp.user.model.SignUp;
import com.payment.paymentapp.user.model.UserResponse;

public interface UserManager {
    void create(SignUp signUpRequest);
    UserResponse get(String username) throws NotFoundException;
}
