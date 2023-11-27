package com.coyo96.events.utils;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CheckNewUser {
    private JdbcTemplate jdbcTemplate;

    public String checkNewUser(String userId) {
        return null;
        // if we can add a flow to the /register endpoint to give a user special permissions then we won't need to check if the user is registered in out db already.
    }
}
