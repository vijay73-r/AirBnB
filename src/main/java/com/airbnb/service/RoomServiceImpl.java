package com.airbnb.service;

import com.airbnb.dto.RoomDto;
import com.airbnb.entity.Property;
import com.airbnb.entity.Room;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements  RoomService {
    private RoomRepository roomRepository;
    private PropertyRepository propertyRepository;
    public RoomServiceImpl(RoomRepository roomRepository, PropertyRepository propertyRepository) {
        this.roomRepository = roomRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public RoomDto createRoom(Long propertyId, RoomDto roomDto) {
        // Find the property associated with the room
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        // Map the RoomDto to Room entity
        Room room = mapToEntity(roomDto);
        room.setProperty(property);

        // Save the room entity to the database
        Room savedRoom = roomRepository.save(room);

        // Return the saved room as a DTO
        return mapToDto(savedRoom);
    }


    @Override
    public RoomDto updateRoom(Long roomId, RoomDto roomDto) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setType(roomDto.getType());

        Room updatedRoom = roomRepository.save(room);

        return mapToDto(updatedRoom);
    }


    public Room mapToEntity(RoomDto roomDto){
        Room room = new Room();
        room.setType(roomDto.getType());

        return room;
    }

    public RoomDto mapToDto(Room room){
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setType(room.getType());

        dto.setProperty(room.getProperty().getId());
        return dto;
    }



}
