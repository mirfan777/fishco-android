package com.example.fishco.model;

import lombok.Data;

@Data
public class User {
    String id ;
    String name;
    String email;
    String email_verified_at;
    Integer role ;
    String address ;
    String phone_number;
}
