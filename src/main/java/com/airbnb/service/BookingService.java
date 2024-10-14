package com.airbnb.service;

import com.airbnb.dto.BookingDto;
import com.airbnb.entity.AppUser;

public interface BookingService {

    public BookingDto createBooking(Long propertyId, String roomType, AppUser appUser, BookingDto dto);
    boolean deleteBooking(Long bookingId);
    BookingDto updateBooking(Long bookingId, BookingDto updatedDto);
    BookingDto getBookingById(Long bookingId);
}
