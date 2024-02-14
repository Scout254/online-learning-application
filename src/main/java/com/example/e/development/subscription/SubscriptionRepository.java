package com.example.e.development.subscription;

import com.example.e.development.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsByUserAndSubscriptionType(User user, SubscriptionType subscriptionType);

    List<Subscription> findByIsActiveTrue();

    List<Subscription> findByIsActiveTrueOrderByStartTimeDesc();

    List<Subscription> findByUserAndIsActiveTrue(User user);
}