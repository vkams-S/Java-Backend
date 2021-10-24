package com.vkams.personal.lasyEats.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.ArrayList;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Restaurant {
    private String restaurantId;
    private String name;
    private String city;
    private String imageUrl;
    private Double latitude;
    private Double longitude;
    private String opensAt;
    private String closesAt;
    private ArrayList<String> attributes;
    public String SerializeToJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString =mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        return jsonString;
    }
}
