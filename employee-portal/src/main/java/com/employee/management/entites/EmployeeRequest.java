package com.employee.management.entites;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeRequest {
    private String name;
    private Long salary;
    private Float rating;
    private String password;
    private EmployeeType type;
}
