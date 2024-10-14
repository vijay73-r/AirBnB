package com.airbnb.repository;

import com.airbnb.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

//    @Query("select r from Room r where r.property.id = :propertyId and r.type = :roomType")
//    List<Room> findByPropertyIdAndType(@Param("propertyId") Long propertyId, @Param("roomType") String room);



    Optional<Room> findByPropertyIdAndType(Long propertyId, String type);



}