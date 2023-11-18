package com.coyo96.events.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
public class TestController {
    @GetMapping("/")
    public String hey(Principal principal) {
        if(principal != null) {
            return "hey " + principal.getName() ;

        } else {
            return "hey";
        }
    }
}
