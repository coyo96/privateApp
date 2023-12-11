package com.coyo96.events.controller;

import com.coyo96.events.dao.UserDao;
import com.coyo96.events.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void registerNewUser(Principal principal, @RequestBody User user) {
        String auth0Id = principal.getName();
        Long userId = userDao.getUserIdByAuth0Id(auth0Id);
        if(userId == null) {
            user.setAuth0Id(auth0Id);
            boolean isRegistered = userDao.createNewUser(user);
            if (!isRegistered) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }

    }

    @GetMapping("/user/validate")
    public ResponseEntity<Void> isRegistered(Principal principal) {
        String auth0Id = principal.getName();
        if(userDao.getUserIdByAuth0Id(auth0Id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @GetMapping("/user/{username}")
    public User getUser(Principal principal, @PathVariable String username) {
        long userId = userDao.getUserIdByUsername(username); // TODO: create method to get user by username
        if (principal.getName().equals(userId)) {
            return userDao.getUserById(userId);
        } else {
            return userDao.getPublicProfile(userId); // TODO: not implemented yet
        }
    }
}
