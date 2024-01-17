package com.example.e.development.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private UserType subscriptionType;

    // Constructors, getters, and setters

    public void setSubscriptionType(UserType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
