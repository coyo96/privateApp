package com.coyo96.events.dao;


import com.coyo96.events.model.User;

public interface UserDao {

    User getUserById(String userId);
    boolean createNewUser(User user);

    boolean updateUser(User user);

    String getUserIdByUsername(String username);

    User getPublicProfile(String userId);
}
