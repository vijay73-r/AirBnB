package com.airbnb.dto;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Property;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDto {

    private Long id;

    private String totalGuests;
    private String guestName;
    private String mobile;
    private String email;
    private Float totalPrice;
    private Integer totalNights;
    private String typeOfRoom;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private Long appUser;
    private Long property;
}
