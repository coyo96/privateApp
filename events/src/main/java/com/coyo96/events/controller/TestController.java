package com.coyo96.events.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String hey(@AuthenticationPrincipal OidcUser principal) {
        if(principal != null) {
            return "hey " + principal.getFullName() ;

        } else {
            return "hey";
        }
    }
}
