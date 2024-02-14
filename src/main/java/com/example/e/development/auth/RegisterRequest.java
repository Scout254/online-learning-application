package com.example.e.development.auth;


import com.example.e.development.user.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegisterRequest {
    private String email;
    private String fullName;
    private String password;
    private Role role;

}