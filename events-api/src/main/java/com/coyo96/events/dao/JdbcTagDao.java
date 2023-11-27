package com.coyo96.events.dao;

import com.coyo96.events.exception.DaoException;
import com.coyo96.events.model.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Slf4j
@Component
public class JdbcTagDao implements TagDao {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<Tag> getEventTags(int eventId) {
        String sql = "SELECT t.tag_id, t.name " +
                "FROM tags AS t " +
                "JOIN event_tags as et ON t.tag_id = et.tag_id " +
                "WHERE et.event_id = ?";

        List<Tag> tags = new ArrayList<>();
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, eventId);
            while (rowSet.next()) {
                tags.add(mapRowSetToTags(rowSet));
            }
            return tags;

        } catch (CannotGetJdbcConnectionException e) {
            log.error(e.getMessage());
            throw new DaoException("An error occurred connecting to the database.");
        }
    }

    private Tag mapRowSetToTags(SqlRowSet rowSet) {
        int tagId = rowSet.getInt("tag_id");
        String name = rowSet.getString("name");

        return new Tag(tagId, name);
    }
}
