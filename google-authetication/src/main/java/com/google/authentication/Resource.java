package com.google.authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Resource {

    @GetMapping("/home")
    String home() {
        return "You are at home page";
    }
}
