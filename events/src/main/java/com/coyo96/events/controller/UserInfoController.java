package com.coyo96.events.controller;

import com.coyo96.events.dao.UserDao;
import com.coyo96.events.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserInfoController {
    private UserDao userDao;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void registerNewUser(Principal principal) {

    }
}
