package com.coyo96.events.dao;

import com.coyo96.events.model.User;

public interface UserDao {

    User getUserById(long userId);

    boolean createNewUser(User user);

    boolean updateUser(User user);

    long getUserIdByAuth0Id(String auth0Id);

    long getUserIdByUsername(String username);

    User getPublicProfile(long userId);
}
