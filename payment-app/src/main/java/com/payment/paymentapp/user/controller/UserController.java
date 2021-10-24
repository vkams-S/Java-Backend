package com.payment.paymentapp.user.controller;

import com.payment.paymentapp.exception.NotFoundException;
import com.payment.paymentapp.user.manager.UserManager;
import com.payment.paymentapp.user.model.SignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserManager userManager;

    @PostMapping("/user")
    void create(@RequestBody SignUp signUp){
        userManager.create(signUp);
    }

    @GetMapping("/user/{id}")
    ResponseEntity get(@PathVariable("id") String  id){
        try {
            return ResponseEntity.ok( userManager.get(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
