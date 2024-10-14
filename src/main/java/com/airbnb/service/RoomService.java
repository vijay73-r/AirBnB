package com.airbnb.service;

import com.airbnb.dto.RoomDto;

public interface RoomService {

    public RoomDto createRoom(Long propertyId ,RoomDto roomDto);



    public RoomDto updateRoom(Long roomId, RoomDto roomDto);
}
