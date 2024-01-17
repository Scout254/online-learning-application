package com.example.e.development.service;

import com.example.e.development.models.User;
import com.example.e.development.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        // Additional business logic or validation can be added here

        // Save the user to the database
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        // Retrieve all users from the database
        return userRepository.findAll();
    }
}
