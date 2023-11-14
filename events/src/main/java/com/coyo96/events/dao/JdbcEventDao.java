package com.coyo96.events.dao;

import com.coyo96.events.exception.DaoException;
import com.coyo96.events.model.Event;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@AllArgsConstructor
@Component
@Slf4j
public class JdbcEventDao implements EventDao {

    private final JdbcTemplate jdbcTemplate;
    private final CategoryDao categoryDao;
    private final LocationDao locationDao;
    private final TagDao tagDao;
    private final ReviewDao reviewDao;

    @Override
    public Event getEventById(int eventId) {
        String sql = "SELECT event_id, organizer_id, event_name, event_date, event_time, description, img_url, ticket_cost, purchase_ticket_link, created_on, is_private" +
                "FROM events " +
                "WHERE event_id = ?";
        Event event;
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, eventId);
            if(rowSet.next()) {
               event = mapRowSetToEvent(rowSet);
               event.setCategories(categoryDao.getEventCategories(eventId));
               event.setEventLocation(locationDao.getEventLocation(eventId));
               event.setEventTags(tagDao.getEventTags(eventId));
               event.setEventReviews(reviewDao.getEventReviews(eventId));
            } else {
                throw new DaoException("Query returned 0 rows.");
            }
            return event;
        } catch (CannotGetJdbcConnectionException e) {
            log.error(e.getMessage());
            throw new DaoException("An error occurred connecting to the database.");
        }
    }

    @Override
    public int createNewEvent(Event event) {
        String sql = "INSERT INTO events (organizer_id, event_name, event_date, event_time, description, img_url, ticket_cost, purchase_ticket_link, is_private " +
                "VALUE (?,?,?,?,?,?,?,?,?) RETURNING event_id";
        try {
            return jdbcTemplate.update(sql, int.class, event.getOrganizerId(), event.getEventName(), event.getEventTime(), event.getDescription(), event.getImgUrl(), event.getTicketCost(), event.getPurchaseTicketUrl(), event.getIsPrivate());
        } catch (CannotGetJdbcConnectionException e) {
            log.error(e.getMessage());
            throw new DaoException("An error occurred connecting to the database.");
        }
    }

    private Event mapRowSetToEvent(SqlRowSet rowSet) {
        int eventId = rowSet.getInt("event_id");
        String organizerId = rowSet.getString("organizer_id");
        String eventName = rowSet.getString("event_name");
        Date eventDate = rowSet.getDate("event_date");
        Time eventTime = rowSet.getTime("event_time");
        String description = rowSet.getString("description");
        String imgUrl = rowSet.getString("img_url");
        Double ticketCost = rowSet.getDouble("ticket_cost");
        String purchaseTicketUrl = rowSet.getString("purchase_ticket_url");
        Timestamp createdOn = rowSet.getTimestamp("created_on");
        Boolean isPrivate = rowSet.getBoolean("is_private");

        Event event = new Event();
        event.setEventId(eventId);
        event.setOrganizerId(organizerId);
        event.setEventName(eventName);
        if (eventDate != null) {
            event.setEventDate(eventDate.toLocalDate());
        }
        if (eventTime != null) {
            event.setEventTime(eventTime.toLocalTime());
        }
        event.setDescription(description);
        event.setImgUrl(imgUrl);
        event.setTicketCost(ticketCost);
        event.setPurchaseTicketUrl(purchaseTicketUrl);
        if (createdOn != null) {
            event.setCreatedOn(createdOn.toLocalDateTime());
        }
        event.setIsPrivate(isPrivate);

        return event;
    }
}
