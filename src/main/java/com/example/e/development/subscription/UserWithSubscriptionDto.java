package com.example.e.development.subscription;

import com.example.e.development.subscription.SubscriptionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserWithSubscriptionDto {
    private Integer id;
    private String email;
    private String role;
    private SubscriptionType subscriptionType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}