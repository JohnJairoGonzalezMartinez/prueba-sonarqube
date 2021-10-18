package com.marketplace.Authentication.controllers;

import com.marketplace.Authentication.controllers.exceptions.IncorrectPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String encryptPassword(String password){
        return passwordEncoder.encode(password);
    }

    public void assertPasswordsMatch(String plainPassword, String encryptedPassword) throws IncorrectPasswordException {
        if ( !passwordEncoder.matches(plainPassword, encryptedPassword) ){
            throw new IncorrectPasswordException("The passwords does not match");
        }
    }

}
