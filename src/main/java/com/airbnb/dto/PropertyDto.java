package com.airbnb.dto;

import com.airbnb.entity.City;
import com.airbnb.entity.Country;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class PropertyDto {
    private long id;
    private String name;
    private Integer noOfGuests;
    private Integer noOfBedroom;
    private Integer noOfBeds;
    private Integer noOfBathrooms;

    private long country;
    private long city;
}
