package com.example.e.development.subscription;


import com.example.e.development.user.User;
import com.example.e.development.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final UserService userService;
    @PostMapping("/subscribeToBasic")
    public ResponseEntity<String> subscribeToBasic(Principal connectedUser) {
        try {
            User currentUser = userService.getCurrentUser(connectedUser);

            // Subscribe the user to BASIC
            subscriptionService.subscribeToBasic(currentUser);

            return ResponseEntity.ok("Subscribed to Basic successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/subscribeToPremium")
    public ResponseEntity<String> subscribeToPremium(Principal connectedUser) {
        try {

            User currentUser = userService.getCurrentUser(connectedUser);

            // Subscribe the user to PREMIUM
            subscriptionService.subscribeToPremium(currentUser);


            return ResponseEntity.ok("Subscribed to Premium successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/subscribeToVip")
    public ResponseEntity<String> subscribeToVip(Principal connectedUser) {
        try {

            User currentUser = userService.getCurrentUser(connectedUser);

            // Subscribe the user to VIP
            subscriptionService.subscribeToVip(currentUser);

            return ResponseEntity.ok("Subscribed to VIP successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/subscribeToLifetimeAccess")
    public ResponseEntity<String> subscribeToLifetimeAccess(Principal connectedUser) {
        try {

            User currentUser = userService.getCurrentUser(connectedUser);

            // Subscribe the user to LIFETIME ACCESS
            subscriptionService.subscribeToLifetimeAccess(currentUser);

            return ResponseEntity.ok("Subscribed to Lifetime Access successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/subscribeToAdvancedVip")
    public ResponseEntity<String> subscribeToAdvancedVip(Principal connectedUser) {
        try {

            User currentUser = userService.getCurrentUser(connectedUser);

            // Subscribe the user to ADVANCED VIP
            subscriptionService.subscribeToAdvancedVip(currentUser);

            return ResponseEntity.ok("Subscribed to Advanced VIP successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getSubscriptionStatus")
    public ResponseEntity<String> getSubscriptionStatus(Principal connectedUser) {
        try {
            // Get the authenticated user using the userService
            User currentUser = userService.getCurrentUser(connectedUser);

            // Check the subscription status
            String subscriptionStatus = subscriptionService.getSubscriptionStatus(currentUser);

            return ResponseEntity.ok(subscriptionStatus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getUsersWithActiveSubscriptions")
    public ResponseEntity<List<UserWithSubscriptionDto>> getUsersWithActiveSubscriptions(Principal connectedUser) {
        try {
            // Check if the user is an admin
            if (isAdmin(connectedUser)) {
                List<UserWithSubscriptionDto> usersWithActiveSubscriptions = subscriptionService.getAllUsersWithActiveSubscriptions();
                return ResponseEntity.ok(usersWithActiveSubscriptions);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private boolean isAdmin(Principal connectedUser) {
        if (connectedUser instanceof UsernamePasswordAuthenticationToken) {
            List<GrantedAuthority> authorities = (List<GrantedAuthority>) ((UsernamePasswordAuthenticationToken) connectedUser).getAuthorities();
            return authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        }
        return false;
    }

}