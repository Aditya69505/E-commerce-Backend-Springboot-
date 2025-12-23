package com.ecom.ecom.dto;

import lombok.Data;
import com.ecom.ecom.model.Address;

@Data
public class UserRequest {

    private  String firstName;
    private String lastName;
    private String email;
    private String phone;

    private Address address;
}
