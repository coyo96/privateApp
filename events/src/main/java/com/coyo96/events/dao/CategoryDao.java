package com.coyo96.events.dao;

import com.coyo96.events.model.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> getEventCategories(int eventId);
}
