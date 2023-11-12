package com.coyo96.events.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;



@AllArgsConstructor
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
    private boolean emailVerified;
    //from frontEnd
    private Date dateOfBirth;
    //from frontEnd
    private int primaryPhone;
    //from Auth0
    private LocalDateTime createdOn;
    //from FrontEnd
    private char genderCode;
    //from frontEnd
    private boolean activated;
    //from Auth0
    private String picture;

    public static class UserBuilder {
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
        private boolean emailVerified;
        //from frontEnd
        private Date dateOfBirth;
        //from frontEnd
        private int primaryPhone;
        //from Auth0
        private LocalDateTime createdOn;
        //from FrontEnd
        private char genderCode;
        //from frontEnd
        private boolean activated;
        //from Auth0
        private final String picture;

        public UserBuilder(String userId, String username, String picture) {
            this.userId = userId;
            this.username = username;
            this.picture = picture;
        }

        public UserBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder withMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public UserBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withEmailVerified(boolean emailVerified) {
            this.emailVerified = emailVerified;
            return this;
        }

        public UserBuilder withDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public UserBuilder withPrimaryPhone(int primaryPhone) {
            this.primaryPhone = primaryPhone;
            return this;
        }

        public UserBuilder withCreatedOn(LocalDateTime createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public UserBuilder withGenderCode(char genderCode) {
            this.genderCode = genderCode;
            return this;
        }

        public UserBuilder withActivated(boolean activated) {
            this.activated = activated;
            return this;
        }

        public User build() {
            return new User(
                    this.userId,
                    this.username,
                    this.firstName,
                    this.middleName,
                    this.lastName,
                    this.email,
                    this.emailVerified,
                    this.dateOfBirth,
                    this.primaryPhone,
                    this.createdOn,
                    this.genderCode,
                    this.activated,
                    this.picture
            );
        }
    }

}
