package com.payment.paymentapp.user.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private String username;
    private String password;
    private String email;
    private String role;
}
