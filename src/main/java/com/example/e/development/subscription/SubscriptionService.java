package com.example.e.development.subscription;

import com.example.e.development.user.User;
import com.example.e.development.user.Role;
import com.example.e.development.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public void subscribeToPremium(Long userId, SubscriptionDto subscriptionDto) {
        // Retrieve user by userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Check if the user is a basic user before upgrading to premium
        if (user.getUserType() == Role.BASIC) {
            // Upgrade user to premium
            user.setUserType(Role.PREMIUM);

            // Save the updated user to the database
            userRepository.save(user);

            // Create a subscription entry (if needed)
            Subscription subscription = new Subscription();
            subscription.setUser(user);
            subscription.setSubscriptionType(Role.PREMIUM);

            // Save the subscription to the database
            subscriptionRepository.save(subscription);
        } else {
            // Handle the case where the user is not a basic user
            throw new IllegalArgumentException("User must be a basic user to subscribe to premium");
        }
    }



    // Additional methods for subscription-related business logic if needed
}
