package com.vkams.personal.lasyEats.dto;

import lombok.*;
import org.springframework.data.annotation.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Items {
    @Id
    private String id;
    @NotNull
    String itemId;
    @NotNull
    String name;
    @NotNull
    String imageUrl;
    @NotNull
    List<String> attributes = new ArrayList<>();
    @NotNull
    double price;
}
