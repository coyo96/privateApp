package com.coyo96.events.dao;

import com.coyo96.events.exception.DaoException;
import com.coyo96.events.model.Location;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class JdbcLocationDao implements LocationDao {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Location getEventLocation(int eventId) {
        String sql = "SELECT l.location_id, l.location_name, l.address, l.address_line_2, l.city, l.state_province, l.postal_code, l.country " +
                "FROM locations AS l " +
                "JOIN event_location AS el ON l.location_id = el.location_id " +
                "WHERE el.event_id = ?";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, eventId);
            if(rowSet.next()){
                return mapRowSetToLocation(rowSet);
            } else {
                log.error("Event id did not return a location.");
                throw new DaoException("Event id did not return a location.");
            }
        }  catch (CannotGetJdbcConnectionException e) {
            log.error(e.getMessage());
            throw new DaoException("An error occurred connecting to the database.");
        }
    }

    private Location mapRowSetToLocation(SqlRowSet rowSet) {
        int locationId = rowSet.getInt("location_id");
        String locationName = rowSet.getString("location_name");
        String address = rowSet.getString("address");
        String addressLine2 = rowSet.getString("address_line_2");
        String city = rowSet.getString("city");
        String stateProvince = rowSet.getString("state_province");
        int postalCode = rowSet.getInt("postal_code");
        String country = rowSet.getString("country");

        return new Location(locationId, locationName, address, addressLine2, city, stateProvince, postalCode, country);
    }
}
