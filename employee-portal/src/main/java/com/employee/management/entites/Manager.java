package com.employee.management.entites;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@DiscriminatorValue(value = "manager")
public class Manager extends Employee{
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id")
    private List<Employee> subordinates;
}
