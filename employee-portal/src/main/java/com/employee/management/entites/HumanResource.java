package com.employee.management.entites;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@DiscriminatorValue(value = "hr")
public class HumanResource extends Employee{
}
