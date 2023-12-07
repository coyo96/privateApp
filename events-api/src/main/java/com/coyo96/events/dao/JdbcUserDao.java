package com.coyo96.events.dao;

import com.coyo96.events.exception.DaoException;
import com.coyo96.events.model.User;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@Slf4j
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public User getUserById(long userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
            if (rowSet.next()) {
                return mapRowSetToUser(rowSet);
            } else {
                log.debug("User " + userId + " not found");
                throw new DaoException("User " + userId + " not found");
            }
        } catch (CannotGetJdbcConnectionException e) {
            log.error("A connection could not be made to postgres.", e);
            throw new DaoException("Could not connect to server database");
        }
    }

    @Override
    public boolean createNewUser(User user) {
        String sql = "INSERT INTO users (auth0_id, username, first_name, last_name, email, " +
                "email_verified, date_of_birth, primary_phone, gender_code, picture) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            int rowsReturned = jdbcTemplate.update(sql, user.getAuth0Id(), user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(), user.getEmail(), user.getEmailVerified(), user.getDateOfBirth(),
                    user.getPrimaryPhone(), user.getGenderCode(), user.getPicture());
            if (rowsReturned != 1) {
                throw new DaoException("Insert did not return 1 row affected, returned: " + rowsReturned);
            }
            return true;
        } catch (CannotGetJdbcConnectionException e) {
            log.debug("Error connecting to database. Error message: " + e.getMessage() + " "
                    + Arrays.toString(e.getStackTrace()));
            throw new DaoException("Error connecting to database. Error message: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DaoException("Error connecting to database. Error message: " + e.getMessage());
        }
    }

    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE auth0_id = ?, username = ?, first_name = ?, last_name = ?, email = ?, " +
                "email_verified = ?, date_of_birth = ?, primary_phone = ?, created_on = ?, " +
                "gender_code = ?, activated = ?, picture = ? " +
                "FROM users " +
                "WHERE user_id = ?";
        try {
            int rowsReturned = jdbcTemplate.update(sql, user.getAuth0Id(), user.getUsername(), user.getFirstName(),
                    user.getLastName(), user.getEmail(), user.getEmailVerified(), user.getDateOfBirth(),
                    user.getPrimaryPhone(), user.getCreatedOn(), user.getGenderCode(),
                    user.getEmailVerified(), user.getPicture(), user.getUserId());
            if (rowsReturned != 1) {
                throw new DaoException("Insert did not return 1 row affected, returned: " + rowsReturned);
            }
            return true;
        } catch (CannotGetJdbcConnectionException e) {
            log.debug("Error connecting to database. Error message: " + e.getMessage() + " "
                    + Arrays.toString(e.getStackTrace()));
            throw new DaoException("Error connecting to database. Error message: " + e.getMessage());
        }
    }

    @Override
    public Long getUserIdByAuth0Id(String auth0Id) {
        String sql = "SELECT user_id FROM users WHERE auth0_id = ?";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, auth0Id);
            if (rowSet.next()) {
                return rowSet.getLong("user_id");
            } else {
                return null;
            }
        } catch (CannotGetJdbcConnectionException e) {
            log.debug("Error connecting to database. Error message: " + e.getMessage() + " "
                    + Arrays.toString(e.getStackTrace()));
            throw new DaoException("Error connecting to database. Error message: " + e.getMessage());
        }
    }

    @Override
    public Long getUserIdByUsername(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserIdByUsername'");
    }

    @Override
    public User getPublicProfile(long userId) {
        // TODO: create a public profile dto to return.
        return null;
    }

    private User mapRowSetToUser(SqlRowSet rowSet) {
        long userId = rowSet.getLong("user_id");
        String auth0Id = rowSet.getString("auth0_id");
        String username = rowSet.getString("username");
        String picture = rowSet.getString("picture");
        String firstName = rowSet.getString("first_name");
        String lastName = rowSet.getString("last_name");
        String email = rowSet.getString("email");
        boolean emailVerified = rowSet.getBoolean("email_verified");
        LocalDate dateOfBirth;

        try {
            dateOfBirth = rowSet.getDate("date_of_birth").toLocalDate();
        } catch (NullPointerException e) {
            dateOfBirth = null;
        }
        Long primaryPhone = rowSet.getLong("primaryPhone");
        LocalDateTime createdOn = rowSet.getTimestamp("created_on").toLocalDateTime(); // will never be null;
        Character genderCode;

        try {
            genderCode = rowSet.getString("gender_code").charAt(0);
        } catch (NullPointerException e) {
            genderCode = null;
        }
        boolean activated = rowSet.getBoolean("activated");

        return new User(userId, auth0Id, username, firstName, lastName, email, emailVerified, dateOfBirth,
                primaryPhone, createdOn, genderCode, activated, picture, null);
    }

}
