package com.airbnb.service;

import com.airbnb.dto.RoomAvailabilityDto;
import com.airbnb.entity.RoomAvailability;
import com.airbnb.repository.RoomAvailabilityRepository;
import com.airbnb.service.RoomAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomAvailabilityServiceImpl implements RoomAvailabilityService {

    @Autowired
    private RoomAvailabilityRepository roomAvailabilityRepository;

    @Override
    public RoomAvailabilityDto saveOrUpdateRoomAvailability(RoomAvailabilityDto roomAvailabilityDto) {
        RoomAvailability roomAvailability = mapToEntity(roomAvailabilityDto);
        RoomAvailability savedRoomAvailability = roomAvailabilityRepository.save(roomAvailability);
        return mapToDto(savedRoomAvailability);
    }

    @Override
    public RoomAvailabilityDto getRoomAvailabilityById(Long id) {
        RoomAvailability roomAvailability = roomAvailabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RoomAvailability not found"));
        return mapToDto(roomAvailability);
    }

    @Override
    public List<RoomAvailabilityDto> getAllRoomAvailabilities() {
        List<RoomAvailability> roomAvailabilities = roomAvailabilityRepository.findAll();
        return roomAvailabilities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRoomAvailability(Long id) {
        roomAvailabilityRepository.deleteById(id);
    }

    private RoomAvailabilityDto mapToDto(RoomAvailability roomAvailability) {
        RoomAvailabilityDto dto = new RoomAvailabilityDto();
        dto.setId(roomAvailability.getId());
        dto.setAvailableDate(roomAvailability.getAvailableDate());
        dto.setAvailableCount(roomAvailability.getAvailableCount());
        dto.setPrice(roomAvailability.getPrice());
        dto.setRoom(roomAvailability.getRoom()); // Assumes Room is properly handled in DTO
        return dto;
    }

    private RoomAvailability mapToEntity(RoomAvailabilityDto dto) {
        RoomAvailability roomAvailability = new RoomAvailability();
        roomAvailability.setId(dto.getId());
        roomAvailability.setAvailableDate(dto.getAvailableDate());
        roomAvailability.setAvailableCount(dto.getAvailableCount());
        roomAvailability.setPrice(dto.getPrice());
        roomAvailability.setRoom(dto.getRoom()); // Assumes Room is properly handled in DTO
        return roomAvailability;
    }
}
