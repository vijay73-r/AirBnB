package com.airbnb.dto;

import com.airbnb.entity.Room;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RoomAvailabilityDto {

    private Long id;

    private LocalDate availableDate;

    private Integer availableCount; // Number of rooms available for this date


    private Float price;

    private Room room;
}
