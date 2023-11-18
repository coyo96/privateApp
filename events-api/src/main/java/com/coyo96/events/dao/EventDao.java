package com.coyo96.events.dao;

import com.coyo96.events.model.Event;

public interface EventDao {
    Event getEventById(int eventId);
    int createNewEvent(Event event);
}
