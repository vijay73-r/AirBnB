package com.airbnb.repository;

import com.airbnb.entity.Room;
import com.airbnb.entity.RoomAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, Long> {

    Optional<RoomAvailability> findByRoomAndAvailableDate(Room room, LocalDate date);
}