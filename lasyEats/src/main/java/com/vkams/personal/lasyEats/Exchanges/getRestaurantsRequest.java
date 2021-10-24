package com.vkams.personal.lasyEats.Exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class getRestaurantsRequest {
    @NotNull private double latitude;
    @NotNull private double longitude;
    private String SearchFor;
    public getRestaurantsRequest(double lat, double lon)
    {
        this.latitude=lat;
        this.longitude=lon;
    }

   public void setlatitude(@RequestParam(value = "latitude",required = true) double latitude)
   {
       this.latitude = latitude;
   }
   public  void setLongitude(@RequestParam(value = "longitude",required = true) double longitude)
   {
       this.longitude=longitude;
   }
  public void setSearchFor(@RequestParam(value = "SearchFor",required = true) String searchFor)
  {
      this.SearchFor=searchFor;
  }
}
