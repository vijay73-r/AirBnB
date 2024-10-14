package com.airbnb.controller;

import com.airbnb.dto.BookingDto;
import com.airbnb.entity.AppUser;
import com.airbnb.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")

public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBooking(@RequestParam long propertyId, @RequestParam String roomType, @AuthenticationPrincipal AppUser user, @RequestBody BookingDto dto){
        BookingDto booking = bookingService.createBooking(propertyId, roomType, user, dto);
        if(booking == null){
            return new ResponseEntity<>("No rooms is available", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBooking(@RequestParam long bookingId ) {
        boolean isDeleted = bookingService.deleteBooking(bookingId);
        if (isDeleted) {
            return new ResponseEntity<>("Booking deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Booking not found or you are not the owner", HttpStatus.NOT_FOUND);
        }
    }

}
