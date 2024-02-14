package com.example.e.development.user;


import com.example.e.development.subscription.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        user.setImageUrl(userDto.getImageUrl());
        user.setPassword(userDto.getPassword()); // Store plain text password (not recommended)
        user.setUserType(UserType.BASIC); // All users are created as basic initially

        userService.registerUser(user);

        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user){
        boolean isValidUser = userService.validateUserCredentials(user.getEmail(), user.getPassword());
        if (isValidUser) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @PutMapping("/edit/{userId}")
    public ResponseEntity<User> editUserById(@PathVariable Long userId, User user){
        User updatedUser = userService.editUserById(userId, user);

        if (updatedUser !=null){
            return ResponseEntity.ok(updatedUser);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId,@RequestBody User user){
        User users = userService.getUserById(userId);
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId){
        boolean isDeleted = userService.deleteUserById(userId);

        if (isDeleted){
            return new ResponseEntity<>("User deleted successfully!",HttpStatus.OK);

        }else {
            return new ResponseEntity<>("User not found or deletion not successful",HttpStatus.NOT_FOUND);
        }
    }


}





