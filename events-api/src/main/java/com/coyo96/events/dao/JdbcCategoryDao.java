package com.coyo96.events.dao;

import com.coyo96.events.exception.DaoException;
import com.coyo96.events.model.Category;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Component
@Slf4j
public class JdbcCategoryDao implements CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    public List<Category> getEventCategories(int eventId) {
        String sql = "SELECT c.category_name, c.category_id " +
                "FROM categories AS c " +
                "JOIN event_categories AS ec ON c.category_id = ec.category_id " +
                "WHERE event_id = ?";
        List<Category> categories = new ArrayList<>();
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, eventId);
            while(rowSet.next()){
                categories.add(mapRowSetToCategory(rowSet));
            }
            return categories;
        } catch (CannotGetJdbcConnectionException e) {
            log.error(e.getMessage());
            throw new DaoException("Error connecting to database.");
        }
    }

    private Category mapRowSetToCategory(SqlRowSet rowSet) {
        int categoryId = rowSet.getInt("category_id");
        String categoryName = rowSet.getString("category_name");

        return new Category(categoryId, categoryName);

    }
}
