package com.coyo96.events.model;



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

    //from Auth0
    private String userId;
    //from frontEnd
    private String username;
    //from Auth0
    private String firstName;
    //from frontEnd
    private String middleName;
    //from Auth0
    private String lastName;
    //from Auth0
    private String email;
    //from Auth0
    private Boolean emailVerified;
    //from frontEnd
    private LocalDate dateOfBirth;
    //from frontEnd
    private Integer primaryPhone;
    //from Auth0
    private LocalDateTime createdOn;
    //from FrontEnd
    private Character genderCode;
    //from frontEnd
    private Boolean activated;
    //from Auth0
    private String picture;

//    private List<Location> addresses;
}
