package com.coyo96.events.dao;

import com.coyo96.events.model.User;

public interface UserDao {

    User getUserById(long userId);

    boolean createNewUser(User user);

    boolean updateUser(User user);

    Long getUserIdByAuth0Id(String auth0Id);

    Long getUserIdByUsername(String username);

    User getPublicProfile(long userId);
}
