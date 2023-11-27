package com.coyo96.events.controller;

import com.coyo96.events.dao.UserDao;
import com.coyo96.events.model.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserInfoController {
    private UserDao userDao;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user/register")
    //TODO: Set a PREAUTHORIZE annotation to only allow registration after user has logged in with Auth0 by using ROLES
    public void registerNewUser(Principal principal, @Valid User user) {
        String userId = principal.getName();
        user.setUserId(userId);
        boolean isRegistered = userDao.createNewUser(user);
        if(!isRegistered){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{username}")
    //TODO:
    public User getUser(Principal principal, @PathVariable String username) {
        String userId = userDao.getUserIdByUsername(username);
        if(principal.getName().equals(userId)) {
           return userDao.getUserById(userId);
        } else {
           return userDao.getPublicProfile(userId); //TODO: not implemented yet
        }
    }
}
