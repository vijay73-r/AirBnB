package com.airbnb.controller;

import com.airbnb.dto.RoomAvailabilityDto;
import com.airbnb.service.RoomAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roomAvailability")
public class RoomAvailController {

    @Autowired
    private RoomAvailabilityService roomAvailabilityService;

    // Create or Update RoomAvailability
    @PostMapping
    public ResponseEntity<RoomAvailabilityDto> createOrUpdateRoomAvailability( @RequestBody RoomAvailabilityDto roomAvailabilityDto) {
        RoomAvailabilityDto savedRoomAvailability = roomAvailabilityService.saveOrUpdateRoomAvailability(roomAvailabilityDto);
        return ResponseEntity.ok(savedRoomAvailability);
    }

    // Get RoomAvailability by ID
    @GetMapping("/id")
    public ResponseEntity<RoomAvailabilityDto> getRoomAvailabilityById(@RequestParam Long id) {
        RoomAvailabilityDto roomAvailabilityDto = roomAvailabilityService.getRoomAvailabilityById(id);
        return ResponseEntity.ok(roomAvailabilityDto);
    }

    // Get all RoomAvailabilities
    @GetMapping
    public ResponseEntity<List<RoomAvailabilityDto>> getAllRoomAvailabilities() {
        List<RoomAvailabilityDto> roomAvailabilityDtos = roomAvailabilityService.getAllRoomAvailabilities();
        return ResponseEntity.ok(roomAvailabilityDtos);
    }

    // Delete RoomAvailability by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomAvailability(@PathVariable Long id) {
        roomAvailabilityService.deleteRoomAvailability(id);
        return ResponseEntity.noContent().build();
    }
}
