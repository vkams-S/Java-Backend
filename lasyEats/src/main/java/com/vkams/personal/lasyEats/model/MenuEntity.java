package com.vkams.personal.lasyEats.model;

import com.vkams.personal.lasyEats.dto.Items;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "menus")

public class MenuEntity {
    @Id
    private String id;

    @NotNull
    private String restaurantId;

    @NotNull
    private List<Items> items = new ArrayList();

    public void addItem(Items item) {
        items.add(item);
    }

    public void removeItem(Items item) {
        items.remove(item);
    }
}
