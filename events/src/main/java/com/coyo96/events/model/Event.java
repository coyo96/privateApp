package com.coyo96.events.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Event {
    int eventId;
    String organizerId;
    String eventName;
    LocalDate eventDate;
    LocalTime eventTime;
    String description;
    String imgUrl;
    Double ticketCost;
    String purchaseTicketUrl;
    LocalDateTime createdOn;
    Boolean isPrivate;
    List<Category> categories;
    Location eventLocation;
    List<Tag> eventTags;
    List<Review> eventReviews;

}
