package com.coyo96.events.dao;

import com.coyo96.events.model.Location;

public interface LocationDao {
    Location getEventLocation(int eventId);
}
