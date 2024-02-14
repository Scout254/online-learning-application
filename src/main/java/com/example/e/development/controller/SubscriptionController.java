package com.example.e.development.controller;

import com.example.e.development.subscription.SubscriptionDto;
import com.example.e.development.subscription.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscribe/{userId}")
    public ResponseEntity<String> subscribeToPremium(
            @PathVariable Long userId,
            @RequestBody SubscriptionDto subscriptionDto) {

        subscriptionService.subscribeToPremium(userId, subscriptionDto);

        return ResponseEntity.ok("User subscribed to premium successfully");
    }

}
