package com.ecom.ecom.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
//@AllArgsConstructor
//@NoArgsConstructor
@Data
@Table(name="addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;

}
