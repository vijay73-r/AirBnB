package com.airbnb.controller;

import com.airbnb.dto.ReviewDto;
import com.airbnb.entity.AppUser;
import com.airbnb.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private ReviewService reviewService;
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping("/addReview")
    public ResponseEntity<?> addReview(@RequestBody ReviewDto review, @AuthenticationPrincipal AppUser appUser, @RequestParam long propertyId){
        ReviewDto review1 = reviewService.createReview(review, appUser, propertyId);
        if(review1 == null){
            return new ResponseEntity<>("Review already exist", HttpStatus.OK);
        }


        return new ResponseEntity<>(review1, HttpStatus.CREATED);
    }

    @GetMapping("/getReview")
    public ResponseEntity<ReviewDto> getReview(@RequestParam long id){
        ReviewDto review1 = reviewService.getReview(id);
        return new ResponseEntity<>(review1, HttpStatus.OK);
    }

    @GetMapping("/getReviews")
    public ResponseEntity<List<ReviewDto>> getReviewByProperty(@RequestParam long id){
        List<ReviewDto> reviews = reviewService.getReviewByProperty(id);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @DeleteMapping("/deleteReview")
    public ResponseEntity<?> deleteReview(@RequestParam long id){
        reviewService.deleteReview(id);
        return new ResponseEntity<>("Review successfully deleted", HttpStatus.OK);
    }





}
