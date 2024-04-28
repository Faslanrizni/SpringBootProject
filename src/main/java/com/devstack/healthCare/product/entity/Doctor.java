package com.devstack.healthCare.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity/*whenever Hibernate sees @Entity, it will create a table using the name of our class as table name*/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    private long id;
    private String name;
    private String address;
    private String contact;
    private double salary;
}
