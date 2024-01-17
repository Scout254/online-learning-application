package com.example.e.development.dto;

import com.example.e.development.models.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    private Long userId;
    private UserType subscriptionType;

    public void setSubscriptionType(UserType subscriptionType) {

    }

}
