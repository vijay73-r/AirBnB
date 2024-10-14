package com.airbnb.dto;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Property;
import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private Integer rating;
    private String description;

    private long property;
    private long appUser;
}
