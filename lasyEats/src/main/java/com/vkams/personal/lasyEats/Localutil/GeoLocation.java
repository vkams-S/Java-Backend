package com.vkams.personal.lasyEats.Localutil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeoLocation implements Serializable {
     @NotNull private Double latitude;
     @NotNull private Double longitude;


     @Override
    public String toString()
     {
        StringBuilder stringBuilder = new StringBuilder();
        if(getLongitude()!=null && getLatitude()!=null)
        stringBuilder.append(getLatitude()).append(" ").append(getLatitude());
        return stringBuilder.toString();
     }
     public boolean equals(Object obj)
     {
        if(obj==null || obj.getClass()!=getClass())
        {
            return false;
        }
        GeoLocation geoLocation =(GeoLocation) obj;
        if(getLatitude().equals(geoLocation.getLatitude()) && getLongitude().equals(geoLocation.getLongitude()))
        {
           return true;
        }
        else {
            return false;
        }
     }

    public int hasHCode(){
        return super.hashCode();
    }

    @JsonIgnore
    public boolean isValidGeoLocation()
    {
        if(getLatitude()!=null && getLatitude()>= -90.00
         && getLatitude()<= 90.00 && getLongitude()!=null
        && getLongitude()>= -180.00 && getLatitude()<=180.00)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


}
