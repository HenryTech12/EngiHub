package org.app.engihub.controller;

import org.app.engihub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{email}")
    public ResponseEntity<Map<String,Object>> fetchUserInfo(@PathVariable String email) {
        return new ResponseEntity<>(userService.fetchUserDetails(email), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Map<String,Object>> fetchUserInfo(@PathVariable Long id) {
        return new ResponseEntity<>(userService.fetchUserDetails(id), HttpStatus.OK);
    }


}
