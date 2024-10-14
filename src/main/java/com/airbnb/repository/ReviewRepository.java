package com.airbnb.repository;

import com.airbnb.dto.ReviewDto;
import com.airbnb.entity.AppUser;
import com.airbnb.entity.Property;
import com.airbnb.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r where r.property=:property and r.appUser=:appUser")
    Review findByUserAndProperty(@Param("appUser") AppUser appUser, @Param("property")Property property);

    @Query("select r from Review r where r.property.id=:propertyId")
    List<Review> findReviewByPropertyId(@Param("propertyId") Long propertyId);


}