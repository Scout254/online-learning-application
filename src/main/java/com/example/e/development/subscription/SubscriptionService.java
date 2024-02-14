package com.example.e.development.subscription;

import com.example.e.development.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public void subscribeToBasic(User user) {
        subscribeToSubscriptionType(user, SubscriptionType.BASIC);
    }

    public void subscribeToPremium(User user) {
        subscribeToSubscriptionType(user, SubscriptionType.PREMIUM);
    }

    public void subscribeToLifetimeAccess(User user) {
        subscribeToSubscriptionType(user, SubscriptionType.LIFETIME_ACCESS);
    }

    public void subscribeToAdvancedVip(User user) {
        subscribeToSubscriptionType(user, SubscriptionType.ADVANCED_VIP);
    }

    public void subscribeToVip(User user) {
        subscribeToSubscriptionType(user, SubscriptionType.VIP);
    }
    private void subscribeToSubscriptionType(User user, SubscriptionType subscriptionType) {
        // Check if the user is already subscribed to the specified subscription type
        if (!subscriptionRepository.existsByUserAndSubscriptionType(user, subscriptionType)) {
            // If not subscribed, create a new subscription for the specified type
            Subscription subscription = new Subscription();
            subscription.setUser(user);
            subscription.setSubscriptionType(subscriptionType);
            subscription.setStartTime(LocalDateTime.now());
            // Set the endTime based on your subscription logic
            subscription.setEndTime(calculateSubscriptionEndTime(subscriptionType));
            subscription.setActive(true);

            // Save the subscription
            subscriptionRepository.save(subscription);
        }
        // If already subscribed, you may want to handle this case differently, e.g., extend the subscription period
    }
    private LocalDateTime calculateSubscriptionEndTime(SubscriptionType subscriptionType) {
        if (subscriptionType == SubscriptionType.LIFETIME_ACCESS) {
            // Set it to a distant future date for a lifetime access example
            return LocalDateTime.now().plusYears(100);
        } else {
            // For other subscription types, set the end time to one month from the current time
            return LocalDateTime.now().plusMonths(1);
        }
    }



    public String getSubscriptionStatus(User user) {
        List<Subscription> activeSubscriptions = subscriptionRepository.findByUserAndIsActiveTrue(user);

        if (activeSubscriptions.isEmpty()) {
            return "User does not have an active subscription.";
        } else {
            // Assuming a user can have multiple active subscriptions, you might want to handle this accordingly
            Subscription latestSubscription = activeSubscriptions.get(0);

            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime endTime = latestSubscription.getEndTime();

            if (currentTime.isBefore(endTime)) {
                return "User has an active subscription until " + endTime;
            } else {
                return "User's subscription has expired.";
            }
        }
    }

    public List<UserWithSubscriptionDto> getAllUsersWithActiveSubscriptions() {
        List<Subscription> activeSubscriptions = subscriptionRepository.findByIsActiveTrueOrderByStartTimeDesc();

        return activeSubscriptions.stream()
                .map(subscription -> {
                    User user = subscription.getUser();
                    UserWithSubscriptionDto userWithSubscriptionDto = new UserWithSubscriptionDto();
                    userWithSubscriptionDto.setId(user.getId());
                    userWithSubscriptionDto.setEmail(user.getEmail());
                    userWithSubscriptionDto.setRole(user.getRole().name());
                    userWithSubscriptionDto.setSubscriptionType(subscription.getSubscriptionType());
                    userWithSubscriptionDto.setStartDate(subscription.getStartTime());
                    userWithSubscriptionDto.setEndDate(subscription.getEndTime());
                    return userWithSubscriptionDto;
                })
                .collect(Collectors.toList());
    }

    public SubscriptionType getUserSubscriptionType(User currentUser) {
        List<Subscription> activeSubscriptions = subscriptionRepository.findByUserAndIsActiveTrue(currentUser);

        if (activeSubscriptions.isEmpty()) {
            // User doesn't have an active subscription, you may handle this case accordingly
            return null; // Or any other way to represent no active subscription
        } else {
            // Assuming a user can have multiple active subscriptions, you might want to handle this accordingly
            Subscription latestSubscription = activeSubscriptions.get(0);
            return latestSubscription.getSubscriptionType();
        }
    }

}