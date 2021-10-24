package com.payment.paymentapp.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUp {
    private String email;
    private String username;
    private String password;
}
