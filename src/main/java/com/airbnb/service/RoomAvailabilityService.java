package com.airbnb.service;

import com.airbnb.dto.RoomAvailabilityDto;

import java.util.List;

public interface RoomAvailabilityService {

    RoomAvailabilityDto saveOrUpdateRoomAvailability(RoomAvailabilityDto roomAvailabilityDto);

    RoomAvailabilityDto getRoomAvailabilityById(Long id);

    List<RoomAvailabilityDto> getAllRoomAvailabilities();

    void deleteRoomAvailability(Long id);
}
