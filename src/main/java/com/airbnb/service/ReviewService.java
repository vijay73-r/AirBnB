package com.airbnb.service;

import com.airbnb.dto.ReviewDto;
import com.airbnb.entity.AppUser;

import java.util.List;

public interface ReviewService {

    public ReviewDto createReview(ReviewDto reviewDto, AppUser appUser, long id);

    public ReviewDto getReview(long id);

    public List<ReviewDto> getReviewByProperty(long propertyId);

    public void deleteReview(long id);

}
