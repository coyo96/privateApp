package com.coyo96.events.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {

    private Long userId;

    private String auth0Id;
    // from frontEnd
    private String username;
    // from Auth0
    @JsonAlias(value = "first_name")
    private String firstName;
    // from Auth0
    @JsonAlias(value = "last_name")
    private String lastName;
    // from Auth0
    private String email;
    // from Auth0
    @JsonAlias(value = "email_verified")
    private Boolean emailVerified;
    // from frontEnd
    @JsonAlias(value = "date_of_birth")
    private LocalDate dateOfBirth;
    // from frontEnd
    @JsonAlias(value = "primary_phone")
    private Long primaryPhone;
    // from Auth0
    @JsonAlias(value = "created_on")
    private LocalDateTime createdOn;
    // from FrontEnd
    @JsonAlias(value = "gender_code")
    private Character genderCode;
    // from frontEnd
    private Boolean activated;
    // from Auth0
    private String picture;

    @JsonAlias(value = "user_address")
    private List<UserAddress> userAddresses;
}
