package com.airbnb.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class AppUserDto {
    private Long id;
    private String name;
    private String email;
    private String username;


    private String password;

    private String role;
}
