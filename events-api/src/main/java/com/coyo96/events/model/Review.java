package com.coyo96.events.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Review {
    int reviewId;
    long reviewerId;
    String reviewDescription;
    int rating;
    LocalDateTime createdOn;

}
