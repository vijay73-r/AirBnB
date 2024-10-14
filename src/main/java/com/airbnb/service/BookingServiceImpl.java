package com.airbnb.service;

import com.airbnb.dto.BookingDto;
import com.airbnb.entity.*;
import com.airbnb.repository.*;
import com.airbnb.util.PdfService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;
    private final RoomRepository roomRepository;
    private final RoomAvailabilityRepository roomAvailabilityRepository;
    private PdfService pdfService;
    private SmsService smsService;


    public BookingServiceImpl(BookingRepository bookingRepository,
                              PropertyRepository propertyRepository,
                              RoomRepository roomRepository,
                              RoomAvailabilityRepository roomAvailabilityRepository, PdfService pdfService, SmsService smsService) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.roomRepository = roomRepository;
        this.roomAvailabilityRepository = roomAvailabilityRepository;
        this.pdfService = pdfService;
        this.smsService = smsService;
    }

    @Override
    @Transactional
    public BookingDto createBooking(Long propertyId, String roomType, AppUser appUser, BookingDto dto) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property does not exist"));

        List<LocalDate> datesBetween = getDatesBetween(dto.getCheckInDate(), dto.getCheckOutDate());
        float totalPrice = 0;
        int totalNights = (int) ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());

        for (LocalDate date : datesBetween) {
            Room room = roomRepository.findByPropertyIdAndType(propertyId, roomType)
                    .orElseThrow(() -> new RuntimeException("Room does not exist for the given type"));

            RoomAvailability roomAvailability = roomAvailabilityRepository.findByRoomAndAvailableDate(room, date)
                    .orElseThrow(() -> new RuntimeException("No availability for the given room and date"));

            if (roomAvailability.getAvailableCount() == 0) {
                return null; // Room not available for one of the dates
            } else {
                // Add the price for each date
                totalPrice += roomAvailability.getPrice();

                // Decrease the room availability for that date
                roomAvailability.setAvailableCount(roomAvailability.getAvailableCount() - 1);
                roomAvailabilityRepository.save(roomAvailability);
            }
        }

        // Calculate GST and total price
        double gst = totalPrice * 0.18;
        double finalTotalPrice = totalPrice + gst;

        // Create booking entity
        Booking booking = mapToEntity(dto);
        booking.setTotalPrice((float) finalTotalPrice);
        booking.setAppUser(appUser);
        booking.setTypeOfRoom(roomType);
        booking.setProperty(property);
        booking.setTotalNights(totalNights);
        booking.setProperty(property);
        Booking savedBooking = bookingRepository.save(booking);

        pdfService.generatePdf(savedBooking, booking.getEmail());
        String message = "Dear " + dto.getGuestName() + ", your booking for " + roomType + " at property " + property.getName() + " is confirmed.";
        try {
            smsService.sendSms(dto.getMobile(), message);
        } catch (Exception e) {
            System.out.println("Failed to send SMS: " + e.getMessage());
        }

        return savedBooking != null ? mapToDto(savedBooking) : null;
    }

    @Override
    public boolean deleteBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking does not exist"));

        // Increase the room availability for the dates booked
        List<LocalDate> datesBetween = getDatesBetween(booking.getCheckInDate(), booking.getCheckOutDate());
        for (LocalDate date : datesBetween) {
            Room room = roomRepository.findByPropertyIdAndType(booking.getProperty().getId(), booking.getTypeOfRoom())
                    .orElseThrow(() -> new RuntimeException("Room does not exist for the given type"));

            RoomAvailability roomAvailability = roomAvailabilityRepository.findByRoomAndAvailableDate(room, date)
                    .orElseThrow(() -> new RuntimeException("No availability for the given room and date"));

            roomAvailability.setAvailableCount(roomAvailability.getAvailableCount() + 1);
            roomAvailabilityRepository.save(roomAvailability);
        }

        bookingRepository.delete(booking);
        return false;
    }

    @Override
    public BookingDto updateBooking(Long bookingId, BookingDto updatedDto) {
        return null;
    }

    @Override
    public BookingDto getBookingById(Long bookingId) {
        return null;
    }

    // Helper method to get dates between two dates
    public List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return dates;
    }



    // Mapping methods (similar to the previous example)
    public Booking mapToEntity(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setTotalGuests(bookingDto.getTotalGuests());
        booking.setGuestName(bookingDto.getGuestName());
        booking.setMobile(bookingDto.getMobile());
        booking.setEmail(bookingDto.getEmail());
        booking.setCheckInDate(bookingDto.getCheckInDate());
        booking.setCheckOutDate(bookingDto.getCheckOutDate());
        return booking;
    }

    public BookingDto mapToDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setTotalGuests(booking.getTotalGuests());
        bookingDto.setGuestName(booking.getGuestName());
        bookingDto.setMobile(booking.getMobile());
        bookingDto.setEmail(booking.getEmail());
        bookingDto.setTotalPrice(booking.getTotalPrice());
        bookingDto.setTotalNights(booking.getTotalNights());
        bookingDto.setTypeOfRoom(booking.getTypeOfRoom());
        bookingDto.setCheckInDate(booking.getCheckInDate());
        bookingDto.setCheckOutDate(booking.getCheckOutDate());
        bookingDto.setAppUser(booking.getAppUser().getId());
        bookingDto.setProperty(booking.getProperty().getId());
        return bookingDto;
    }
}
