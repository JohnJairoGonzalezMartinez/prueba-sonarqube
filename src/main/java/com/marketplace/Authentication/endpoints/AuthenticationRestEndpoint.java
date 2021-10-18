package com.marketplace.Authentication.endpoints;

import com.marketplace.Authentication.controllers.AuthenticationController;
import com.marketplace.Authentication.controllers.SessionController;
import com.marketplace.Authentication.models.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/marketplace/authentication")
public class AuthenticationRestEndpoint {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private SessionController sessionController;

    @PostMapping()
    public ResponseEntity<UserAuthentication> createUserAuthentication(@RequestBody UserAuthentication data){
        UserAuthentication authentication = authenticationController.create(data);
        authentication.setPassword(null);
        return ResponseEntity.status(200).body(authentication);
    }

    @PutMapping()
    public ResponseEntity<UserAuthentication> updateUserAuthentication(@RequestBody UserAuthentication data, @RequestHeader("Authorization") String headerToken){
        String simpleToken = sessionController.getTokenFromHeader(headerToken);
        sessionController.getIdIfUserHasActiveSession(simpleToken);
        UserAuthentication authentication = authenticationController.update(data);
        authentication.setPassword(null);
        return ResponseEntity.status(200).body(authentication);
    }

    @DeleteMapping("/{userMail}")
    public ResponseEntity<String> deleteUserAuthentication(@PathVariable("userMail") String userMail, @RequestHeader("Authorization") String headerToken){
        String simpleToken = sessionController.getTokenFromHeader(headerToken);
        String userId = sessionController.getIdIfUserHasActiveSession(simpleToken);
        authenticationController.deleteByUserId(userId);
        return ResponseEntity.status(200).body("User removed from the authentication service");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

}
