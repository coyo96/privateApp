package com.coyo96.events.controller;

import com.coyo96.events.dao.ReviewDao;
import com.coyo96.events.dao.UserDao;
import com.coyo96.events.model.Review;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@AllArgsConstructor
public class ReviewController {
    private ReviewDao reviewDao;
    private UserDao userDao;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/private/events/{eventId}/review")
    public Review createReview(@PathVariable int eventId, @RequestBody Review review, Principal principal) {
        String auth0Id = principal.getName();
        long userId = userDao.getUserIdByAuth0Id(auth0Id);
        review.setReviewerId(userId);
        int reviewId = reviewDao.createReview(review, eventId);
        return reviewDao.getReviewById(reviewId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/private/events/{eventId}/review/{reviewId}")
    public void updateReview(@PathVariable int eventId, @PathVariable int reviewId,
            @RequestBody Review review, Principal principal) {
        String userId = principal.getName();
        if (!userId.equals(reviewDao.getReviewById(reviewId).getReviewerId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!reviewDao.updateReview(review, eventId)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/private/events/{eventId}/review/{reviewId}")
    public void deleteReview(@PathVariable int eventId, @PathVariable int reviewId, Principal principal) {
        String userId = principal.getName();
        if (!userId.equals(reviewDao.getReviewById(reviewId).getReviewerId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!reviewDao.deleteReview(reviewId)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
