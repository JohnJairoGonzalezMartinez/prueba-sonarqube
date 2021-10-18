package com.marketplace.Authentication.controllers;

import com.marketplace.Authentication.models.UserAuthentication;
import com.marketplace.Authentication.repositories.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationController {

    @Autowired
    private AuthenticationRepository repository;

    @Autowired
    private PasswordController passwordController;

    public AuthenticationController(){}

    public UserAuthentication create(UserAuthentication data){
        String encryptedPassword = passwordController.encryptPassword(data.getPassword());
        data.setPassword( encryptedPassword );
        return repository.insert(data);
    }

    public UserAuthentication findByEmail(String email){
        return repository.findById(email).get();
    }

    public UserAuthentication findByUserId(String userId){
        return repository.findByUserId(userId).get(0);
    }

    public UserAuthentication update(UserAuthentication update){
        String encryptedPassword = passwordController.encryptPassword(update.getPassword());
        update.setPassword( encryptedPassword );
        return repository.save(update);
    }

    public void deleteByEmail(String emailToDelete){
        repository.deleteById(emailToDelete);
    }

    public void deleteByUserId(String userId){
        String email = findByUserId(userId).getEmail();
        deleteByEmail(email);

    }

}
