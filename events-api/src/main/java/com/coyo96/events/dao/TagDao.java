package com.coyo96.events.dao;

import com.coyo96.events.model.Tag;

import java.util.List;

public interface TagDao {

    List<Tag> getEventTags(int eventId);
}
