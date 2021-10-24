package com.security.springsecurity2.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignupRequest {
    private String  username;
    private String password;
}
