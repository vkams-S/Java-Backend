package com.vkams.personal.lasyEats.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")

public class UserEntity {
    @Id
    private String id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String name;




    private String restaurantId;
}
