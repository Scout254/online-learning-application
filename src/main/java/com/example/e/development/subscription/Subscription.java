package com.example.e.development.subscription;

import com.example.e.development.user.User;
import com.example.e.development.user.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
