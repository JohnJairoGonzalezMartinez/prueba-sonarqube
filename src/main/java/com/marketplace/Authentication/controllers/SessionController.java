package com.marketplace.Authentication.controllers;

import com.marketplace.Authentication.controllers.exceptions.InvalidTokenException;
import com.marketplace.Authentication.controllers.exceptions.NotActiveSessionException;
import com.marketplace.Authentication.controllers.security.JwtTokenUtil;
import com.marketplace.Authentication.models.LoginData;
import com.marketplace.Authentication.models.Session;
import com.marketplace.Authentication.models.UserAuthentication;
import com.marketplace.Authentication.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SessionController {

    @Autowired
    private AuthenticationController authController;

    @Autowired
    private PasswordController passwordController;

    @Autowired
    private SessionRepository repository;

    @Autowired
    private JwtTokenUtil jwtManager;

    public SessionController(){}

    public String login(LoginData login){
        UserAuthentication authentication = authController.findByEmail(login.getEmail());
        passwordController.assertPasswordsMatch(login.getPassword(), authentication.getPassword());
        String token = jwtManager.generateToken(authentication.getUserId());
        Session newSession = new Session();
        newSession.setToken(token);
        newSession.setUserId( authentication.getUserId() );
        repository.insert(newSession);
        return token;
    }

    public void logout(String token){
        assertTokenIsValid(token);
        repository.deleteById(token);
    }

    public String getTokenFromHeader(String header) {
        String[] tokens = header.split(" ");
        if ( tokens.length != 2 ){
            throw new InvalidTokenException("The token should have the next pattern: Bearer {providedJwtToken}");
        }
        if ( !tokens[0].equals("Bearer") ){
            throw new InvalidTokenException("The token header does not start with the \'Bearer\' clause");
        }
        assertTokenIsValid(tokens[1]);
        return tokens[1];
    }

    public String getIdIfUserHasActiveSession(String token) throws NotActiveSessionException {
        assertTokenIsValid(token);
        String userId = jwtManager.getUserIdFromToken(token);
        if ( repository.findById(token).isEmpty() ){
            throw new NotActiveSessionException("The user with id [" + userId + "] does not have an active session");
        }
        return userId;
    }

    public void assertTokenIsValid(String token){
        try{
            Objects.requireNonNull(jwtManager.getUserIdFromToken(token).equals(""));
        }
        catch (Exception e){
            throw new InvalidTokenException("The token is not valid");
        }
    }

}
