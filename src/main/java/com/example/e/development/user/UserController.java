package com.example.e.development.user;

import com.example.e.development.user.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.Principal;
import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor


public class UserController {

    private final UserService service;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PatchMapping("/password-change")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        try {
            // Log the user attempting to change the password
            logger.info("User is attempting to change the password.");

            service.changePassword(request, connectedUser);

            // Log success
            logger.info("Password changed successfully.");

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Log any exceptions that occur during the password change process
            logger.error("Error changing password", e);

            // Log failure
            logger.error("Failed to change password.");

            // Return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Principal connectedUser) {
        try {
            User currentUser = service.getCurrentUser(connectedUser);
            UserDTO userDTO = new UserDTO(currentUser);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            // Log the exception using SLF4J
            // Return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(Principal connectedUser) {
        try {
            // Check if the connected user has the required role
            if (connectedUser instanceof UsernamePasswordAuthenticationToken) {
                List<GrantedAuthority> authorities = (List<GrantedAuthority>) ((UsernamePasswordAuthenticationToken) connectedUser).getAuthorities();
                if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                    List<UserDTO> users = service.getAllUsers();

                    // Log success
                    logger.info("Successfully fetched all users.");

                    return ResponseEntity.ok(users);
                }
            }

            // Return 403 Forbidden if the user doesn't have the required role
            logger.warn("Unauthorized access to /api/v1/users/all endpoint.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            // Log the exception using SLF4J
            logger.error("Error fetching users", e);

            // Log failure
            logger.error("Failed to fetch all users.");

            // Return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PatchMapping("/edit")
    public ResponseEntity<?> editUser(@RequestBody EditUserRequest editUserRequest, Principal connectedUser) {
        try {
            User currentUser = service.getCurrentUser(connectedUser);



            currentUser.setEmail(editUserRequest.getEmail());

            // Save the updated user
            service.saveUser(currentUser);

            // Log success
            logger.info("User information updated successfully.");

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Log the exception using SLF4J
            logger.error("Error editing user information", e);

            // Log failure
            logger.error("Failed to edit user information.");

            // Return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(Principal connectedUser) {
        try {
            User currentUser = service.getCurrentUser(connectedUser);

            // Log the user attempting to delete the account
            logger.info("User '{}' is attempting to delete their account.", currentUser.getUsername());

            service.deleteUser(currentUser);

            // Log success
            logger.info("Account deleted successfully for user '{}'.", currentUser.getUsername());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Log any exceptions that occur during the account deletion process
            logger.error("Error deleting account for user '{}'", connectedUser.getName(), e);

            // Log failure
            logger.error("Failed to delete account for user '{}'.", connectedUser.getName());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}