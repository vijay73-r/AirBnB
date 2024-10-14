package com.airbnb.controller;

import com.airbnb.dto.RoomDto;
import com.airbnb.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    private RoomService roomService;
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/add")
    public ResponseEntity<RoomDto> addRoom(@RequestParam long propertyId, @RequestBody RoomDto dto) {
        RoomDto room = roomService.createRoom(propertyId, dto);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }





}
