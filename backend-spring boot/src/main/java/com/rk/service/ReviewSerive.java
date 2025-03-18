package com.rk.service;

import java.util.List;

import com.rk.Exception.ReviewException;
import com.rk.model.Review;
import com.rk.model.User;
import com.rk.request.ReviewRequest;

public interface ReviewSerive {
	
    public Review submitReview(ReviewRequest review,User user);
    public void deleteReview(Long reviewId) throws ReviewException;
    public double calculateAverageRating(List<Review> reviews);
}
