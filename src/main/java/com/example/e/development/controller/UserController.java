package com.example.e.development.controller;


import com.example.e.development.dto.UserDto;
import com.example.e.development.models.User;
import com.example.e.development.models.UserType;
import com.example.e.development.service.SubscriptionService;
import com.example.e.development.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setUserType(UserType.BASIC); // All users are created as basic initially

        userService.registerUser(user);

        return ResponseEntity.ok("User registered successfully");
    }
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}





