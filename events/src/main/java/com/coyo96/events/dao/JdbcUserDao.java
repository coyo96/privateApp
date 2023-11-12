package com.coyo96.events.dao;

import com.coyo96.events.exception.DaoException;
import com.coyo96.events.model.User;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Slf4j
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public boolean newUser() {
        return true;
    }

    public User getUserById(String userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
            if(rowSet.next()){
                return mapRowSetToUser(rowSet);
            } else {
                log.debug("User " + userId + " not found");
                throw new DaoException("User " + userId + " not found");
            }
        } catch (CannotGetJdbcConnectionException e){
            log.error("A connection could not be made to postgres.", e);
            throw new DaoException("Could not connect to server database");
        }
    }

    private User mapRowSetToUser(SqlRowSet rowSet) {
        String userId = rowSet.getString("user_id");
        String username = rowSet.getString("username");
        String picture = rowSet.getString("picture");
        User.UserBuilder userBuilder = new User.UserBuilder(userId, username, picture);
        //TODO: add the rest of the fields with checks for null against the rest of them.
        return userBuilder.build();
    }
}
