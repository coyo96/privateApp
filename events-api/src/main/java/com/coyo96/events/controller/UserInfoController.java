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
public class UserInfoController {
    private UserDao userDao;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    //TODO: Set a PREAUTHORIZE annotation to only allow registration after user has logged in with Auth0 by using ROLES
    public void registerNewUser(Principal principal, @Valid User user) {
        String userId = principal.getName();
        user.setUserId(userId);
        boolean isRegistered = userDao.createNewUser(user);
        if(!isRegistered){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
