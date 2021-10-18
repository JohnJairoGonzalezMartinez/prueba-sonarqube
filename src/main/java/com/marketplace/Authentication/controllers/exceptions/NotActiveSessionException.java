package com.marketplace.Authentication.controllers.exceptions;

public class NotActiveSessionException extends RuntimeException {

    public NotActiveSessionException(String message){
        super(message);
    }

}
