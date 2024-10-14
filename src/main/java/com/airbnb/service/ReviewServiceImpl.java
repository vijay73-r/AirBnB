package com.airbnb.service;

import com.airbnb.dto.ReviewDto;
import com.airbnb.entity.AppUser;
import com.airbnb.entity.Property;
import com.airbnb.entity.Review;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{

    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }
    @Override
    public ReviewDto createReview(ReviewDto reviewDto, AppUser user, long id) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new RuntimeException("property not found"));
        Review checkUser = reviewRepository.findByUserAndProperty(user, property);
        if (checkUser != null) {
            return null;
        }
        Review save = mapToEntity(reviewDto);
        save.setProperty(property);
        save.setAppUser(user);
        Review saved = reviewRepository.save(save);
        return mapToDto(saved);
    }

    @Override
    public ReviewDto getReview(long id) {
        Review reviews = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
        return mapToDto(reviews);
    }

    @Override
    public List<ReviewDto> getReviewByProperty(long propertyId){
        List<Review> listById = reviewRepository.findReviewByPropertyId(propertyId);

        return listById.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteReview(long id) {
        reviewRepository.deleteById(id);
    }


    public Review mapToEntity(ReviewDto reviewDto){
        Review review = new Review();
        review.setRating(reviewDto.getRating());
        review.setDescription(reviewDto.getDescription());

        return review;
    }

    public ReviewDto mapToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setRating(review.getRating());
        reviewDto.setDescription(review.getDescription());
        reviewDto.setAppUser(review.getAppUser().getId());
        reviewDto.setProperty(review.getProperty().getId());
        return reviewDto;
    }

}
