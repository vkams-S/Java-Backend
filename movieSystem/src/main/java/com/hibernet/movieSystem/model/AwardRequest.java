package com.hibernet.movieSystem.model;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AwardRequest {
    String name;
    String year;
}
