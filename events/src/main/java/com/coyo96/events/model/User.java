package com.coyo96.events.model;



import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;


@AllArgsConstructor

@Slf4j
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
    private Date dateOfBirth;
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

    public String getUserId() {
        return getUserIdOpt().orElse(null);
    }

    private Optional<String> getUserIdOpt() {
        return Optional.ofNullable(this.userId);
    }

    public String getUsername() {
        return getUsernameOpt().orElse(null);
    }
    private Optional<String> getUsernameOpt() {
        return Optional.ofNullable(this.username);
    }

    public String getFirstName() {
        return getFirstNameOpt().orElse(null);
    }

    private Optional<String> getFirstNameOpt() {
        return Optional.ofNullable(this.firstName);
    }
    public String getMiddleName() {
        return getMiddleNameOpt().orElse(null);
    }
    private Optional<String> getMiddleNameOpt() {
        return Optional.ofNullable(this.middleName);
    }

    public String getLastName() {
        return getLastNameOpt().orElse(null);
    }
    private Optional<String> getLastNameOpt() {
        return Optional.ofNullable(this.lastName);
    }

    public String getEmail() {
        return getEmailOpt().orElse(null);
    }

    private Optional<String> getEmailOpt() {
        return Optional.ofNullable(this.email);
    }

    public Boolean isEmailVerified() {
        return isEmailVerifiedOpt().orElse(null);
    }
    private Optional<Boolean> isEmailVerifiedOpt() {
        return Optional.ofNullable(this.emailVerified);
    }
    public Date getDateOfBirth() {
        return getDateOfBirthOpt().orElse(null);
    }
    private Optional<Date> getDateOfBirthOpt() {
        return Optional.ofNullable(this.dateOfBirth);
    }

    public Integer getPrimaryPhone() {
        return getPrimaryPhoneOpt().orElse(null);
    }
    private Optional<Integer> getPrimaryPhoneOpt() {
        return Optional.ofNullable(this.primaryPhone);
    }
    public LocalDateTime getCreatedOn() {
        return getCreatedOnOpt().orElse(null);
    }
    private Optional<LocalDateTime> getCreatedOnOpt(){
        return Optional.ofNullable(this.createdOn);
    }

    public Character getGenderCode() {
        return getGenderCodeOpt().orElse(null);
    }

    private Optional<Character> getGenderCodeOpt() {
        return Optional.ofNullable(this.genderCode);
    }

    public Boolean isActivated() {
        return isActivatedOpt().orElse(null);
    }
    private Optional<Boolean> isActivatedOpt() {
        return Optional.ofNullable(this.activated);
    }
    public String getPicture() {
        return getPictureOpt().orElse(null);
    }

    private Optional<String> getPictureOpt() {
        return Optional.ofNullable(this.picture);
    }

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
        private String picture;

        public UserBuilder(String userId, String username) {
            this.userId = userId;
            this.username = username;
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

        public UserBuilder withGenderCode(String genderCode) {
            try {
                this.genderCode = genderCode.charAt(0);
            } catch (NullPointerException e) {
                return this;
            }
            return this;
        }

        public UserBuilder withActivated(boolean activated) {
            this.activated = activated;
            return this;
        }
        public UserBuilder withPicture(String picture) {
            this.picture = picture;
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
