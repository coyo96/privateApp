package com.coyo96.events.dao;

import com.coyo96.events.exception.DaoException;
import com.coyo96.events.model.Review;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
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
        String sql = "SELECT review_id, reviewer_id, review_description, rating, created_on " +
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

    @Override
    public Review getReviewById(int reviewId) {
        String sql = "SELECT review_id, reviewer_id, review_description, rating, created_on " +
                "FROM reviews " +
                "WHERE review_id = ?";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, reviewId);
            if (rowSet.next()) {
                return mapRowSetToReview(rowSet);
            } else {
                throw new DaoException("Review not found");
            }
        } catch (CannotGetJdbcConnectionException e) {
            log.error(e.getMessage());
            throw new DaoException("An error occurred connecting to the database.");
        }
    }

    @Override
    public int createReview(Review review, int eventId) {
        String sql = "INSERT INTO reviews (reviewer_id, event_id, review_description, rating) " +
                "VALUES (?,?,?,?) " +
                "RETURNING review_id;";
        try {
            return jdbcTemplate.update(sql, int.class, review.getReviewerId(), eventId, review.getReviewDescription(),
                    review.getRating());
        } catch (CannotGetJdbcConnectionException e) {
            log.error(e.getMessage());
            throw new DaoException("An error occurred connecting to the database.");
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DaoException("A data access exception occurred: " + e.getMessage());
        }
    }

    @Override
    public boolean updateReview(Review review, int eventId) {
        String sql = "UPDATE reviewer_id = ?, event_id = ?, review_description = ?, rating = ?, created_on = ? " +
                "FROM reviews " +
                "WHERE review_id = ?";
        try {
            return (1 == jdbcTemplate.update(sql,
                    review.getReviewerId(),
                    eventId,
                    review.getReviewDescription(),
                    review.getRating(),
                    review.getCreatedOn(),
                    review.getReviewId()));
        } catch (CannotGetJdbcConnectionException e) {
            log.error(e.getMessage());
            throw new DaoException("An error occurred connecting to the database.");
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DaoException("A data access exception occurred: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteReview(int reviewId) {
        String deleteSql = "DELETE FROM reviews WHERE review_id = ?";
        try {
            return (1 == jdbcTemplate.update(deleteSql, reviewId));
        } catch (CannotGetJdbcConnectionException e) {
            log.error(e.getMessage());
            throw new DaoException("An error occurred connecting to the database.");
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DaoException("A data access exception occurred: " + e.getMessage());
        }
    }

    private Review mapRowSetToReview(SqlRowSet rowSet) {
        int reviewId = rowSet.getInt("review_id");
        long reviewerId = rowSet.getLong("reviewer_id");
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
