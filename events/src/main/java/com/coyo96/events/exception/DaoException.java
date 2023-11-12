package com.coyo96.events.exception;

public class DaoException extends RuntimeException{
    DaoException(){
        super();
    }
    public DaoException(String message){
        super(message);
    }
}
