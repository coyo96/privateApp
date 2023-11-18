package com.coyo96.events.dao;

import com.coyo96.events.exception.DaoException;
import com.coyo96.events.model.Review;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Component
@Slf4j
public class JdbcReviewDao implements ReviewDao {
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Review> getEventReviews(int eventId) {
        String sql = "SELECT review_id, reviewer_id, review_description, rating, createdOn " +
                "FROM reviews " +
                "WHERE event_id = ?";
        List<Review> reviews = new ArrayList<>();
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, eventId);
            while (rowSet.next()) {
                reviews.add(mapRowSetToReview(rowSet));
            }
            return reviews;
        } catch (CannotGetJdbcConnectionException e) {
            log.error(e.getMessage());
            throw new DaoException("An error occurred connecting to the database.");
        }
    }

    private Review mapRowSetToReview(SqlRowSet rowSet) {
        int reviewId = rowSet.getInt("review_id");
        String reviewerId = rowSet.getString("reviewer_id");
        String reviewDescription = rowSet.getString("review_description");
        int rating = rowSet.getInt("rating");
        Timestamp createdOn = rowSet.getTimestamp("createdOn");
        Review review = new Review();
        review.setReviewId(reviewId);
        review.setReviewerId(reviewerId);
        review.setReviewDescription(reviewDescription);
        review.setRating(rating);
        try {
            review.setCreatedOn(createdOn.toLocalDateTime());
        } catch (NullPointerException e) {
            log.error("Created_on should have had a value but returned null");
            review.setCreatedOn(null);
        }
        return review;
    }
}
