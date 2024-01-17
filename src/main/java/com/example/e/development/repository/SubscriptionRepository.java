package com.example.e.development.repository;

import com.example.e.development.models.Subscription;
import com.example.e.development.models.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {


}
