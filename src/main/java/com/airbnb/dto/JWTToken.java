package com.airbnb.dto;

import lombok.Data;

@Data
public class JWTToken {
    private String tokenType;
    private String token;
}
