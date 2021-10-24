package com.vkams.personal.lasyEats.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
    private String id;

    @NotNull
    String username;

    @NotNull
    String password;

    String restaurantId;

    @NotNull
    String name;
}
