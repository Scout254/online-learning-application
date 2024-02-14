package com.example.e.development.subscription;

import com.example.e.development.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    private Long userId;
    private Role subscriptionType;

    public void setSubscriptionType(Role subscriptionType) {

    }

}
