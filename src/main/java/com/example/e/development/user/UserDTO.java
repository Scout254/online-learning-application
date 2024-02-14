package com.example.e.development.user;


import com.example.e.development.user.Role;
import com.example.e.development.user.User;
import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String email;
    private Role role;
    private String password;
    private String fullName;


    // Other fields as needed

    public UserDTO(User user) {
        // Map fields from user entity to DTO
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.password = user.getPassword();
        this.fullName = user.getFullName();
        // Map other fields as needed
    }
}