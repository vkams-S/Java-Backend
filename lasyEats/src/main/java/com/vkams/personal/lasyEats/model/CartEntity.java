package com.vkams.personal.lasyEats.model;

import com.vkams.personal.lasyEats.dto.Items;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "Carts")
public class CartEntity {
  @Id
    private String id;
  @NotNull
    private String restaurantId;
  @NotNull
    private String userId;
  @NotNull
  List<Items> items = new ArrayList<>();
  @NotNull
  private double total=0;
  public void addItems(Items item)
  {
     items.add(item);
     total+=item.getPrice();
  }
  public void removeItems(Items item)
  {
     boolean removed=items.remove(item);
     if(removed)
     {
         total-=item.getPrice();
     }
  }
  public void clearCart()
  {
     if(items.size()>0)
     {
        items.clear();
        total=0;
     }
  }

}
