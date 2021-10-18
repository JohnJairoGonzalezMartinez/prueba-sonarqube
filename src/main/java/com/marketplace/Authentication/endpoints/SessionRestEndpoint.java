package com.marketplace.Authentication.endpoints;

import com.marketplace.Authentication.controllers.SessionController;
import com.marketplace.Authentication.models.LoginData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/marketplace/authentication/")
public class SessionRestEndpoint {

    @Autowired
    private SessionController controller;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginData loginData){
        String token = this.controller.login(loginData);
        return ResponseEntity.status(200).body(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String headerToken){
        this.controller.logout(headerToken);
        return ResponseEntity.status(200).body("Session closed successfully");
    }

    @GetMapping("/session/userId/{token}")
    public ResponseEntity<String> getIdFromSessionToken(@PathVariable("token") String token){
        String simpleToken = this.controller.getTokenFromHeader(token);
        String userId = this.controller.getIdIfUserHasActiveSession(simpleToken);
        return ResponseEntity.status(200).body(userId);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

}
