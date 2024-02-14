package com.example.e.development.user;

import com.example.e.development.user.User;
import com.example.e.development.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user){
        //check if email already exists
        if (userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("User with email already exists");
        }
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        // Retrieve all users from the database
        return userRepository.findAll();
    }

    public boolean validateUserCredentials(String email, String password){
        //check if the user with the provided email exists
        User user = userRepository.findByEmail(email);

        //if the user doesn't exist or the password is incorrect
        if (user == null || !user.getPassword().equals(password)){
            throw new RuntimeException("Invalid email or password");
        }
        //if the user exists and the password matches
        return true;
    }
    public User editUserById(Long userId,User user){
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser !=null){
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setImageUrl(user.getImageUrl());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElse(null);
    }

    public boolean deleteUserById(Long userId){
        if (userRepository.existsById(userId)){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }


}
