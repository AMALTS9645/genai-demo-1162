```java
package com.example.loginapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginApiApplication.class, args);
    }
}

package com.example.loginapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.loginapi.model.User;
import com.example.loginapi.service.UserService;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint for user login
     * @param user - User object containing login credentials
     * @return ResponseEntity with login status
     */
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        // Security: Validate user inputs to prevent injection attacks
        boolean isAuthenticated = userService.authenticateUser(user);
        if (isAuthenticated) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }
}

package com.example.loginapi.model;

public class User {
    private String username;
    private String password;

    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.loginapi.service;

import org.springframework.stereotype.Service;

import com.example.loginapi.model.User;

@Service
public class UserService {

    /**
     * Authenticates the user with given credentials
     * @param user - User object containing login credentials
     * @return true if user is authenticated, false otherwise
     */
    public boolean authenticateUser(User user) {
        // Security: Avoid hardcoding sensitive information
        // In real application, retrieve user record and hashed password from database
        String hardcodedUsername = "admin"; 
        String hardcodedPassword = "password"; 
        
        return user.getUsername().equals(hardcodedUsername) 
            && user.getPassword().equals(hardcodedPassword);
    }
}

package com.example.loginapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        // Logging the exception for debugging purposes
        System.err.println(ex.getMessage()); 
        return new ResponseEntity<>("An error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```