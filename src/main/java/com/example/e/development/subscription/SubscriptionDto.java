package com.example.e.development.subscription;

import com.example.e.development.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    private Long userId;
    private UserType subscriptionType;

    public void setSubscriptionType(UserType subscriptionType) {

    }

}
