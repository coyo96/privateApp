package com.coyo96.events.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class UserTest {
    User user;


    @BeforeEach
    void setUp(){
        user = new User();
        user.setUserId("user1");
        user.setUsername("test");
    }

    @Test
    void getFirstName_Produces_String() {

        user.setFirstName("test_name");
        String expected = "test_name";
        Assertions.assertInstanceOf(String.class, user.getFirstName(), "Expected user.getFirstName() to return a string.");
        Assertions.assertEquals(expected, user.getFirstName(), "Expected user.getFirstName() to return a string of 'test_name'");
    }
    @Test
    void getFirstName_On_Null_Value_Does_Not_Produce_NullPointerException() {
        String userFirstName = user.getFirstName();
        Assertions.assertNull(userFirstName);
    }

}