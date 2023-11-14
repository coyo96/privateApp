package com.coyo96.events.dao;

import com.coyo96.events.model.Review;

import java.util.List;

public interface ReviewDao {
    List<Review> getEventReviews(int eventId);
}
