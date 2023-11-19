package com.coyo96.events.dao;

import com.coyo96.events.model.Review;

import java.util.List;

public interface ReviewDao {
    List<Review> getEventReviews(int eventId);

    Review getReviewById(int reviewId);

    int createReview(Review review, int eventId);

    boolean updateReview(Review review, int eventId);

    boolean deleteReview(int reviewId);
}
