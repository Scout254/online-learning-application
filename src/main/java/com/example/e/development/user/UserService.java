package com.example.e.development.user;

import com.example.e.development.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        try {
            // Log the user attempting to change the password
            logger.info("User '{}' is attempting to change the password.", user.getUsername());

            // check if the current password is correct
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                // Log wrong a password attempt
                logger.warn("User provided wrong current password.");

                throw new IllegalStateException("Wrong password");
            }

            // check if the two new passwords are the same
            if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
                // Log password mismatch
                logger.warn("New password and confirmation password do not match.");

                throw new IllegalStateException("Passwords are not the same");
            }

            // update the password
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));

            // save the new password
            repository.save(user);

            // Log success
            logger.info("Password changed successfully for user '{}'.", user.getUsername());
        } catch (Exception e) {
            // Log any exceptions that occur during the password change process
            logger.error("Error changing password for user '{}'", user.getUsername(), e);

            // Log failure
            logger.error("Failed to change password for user '{}'.", user.getUsername());

            throw e; // Propagate the exception after logging
        }
    }

    public User getCurrentUser(Principal connectedUser) {
        return (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
    }


    public List<UserDTO> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }
    public void saveUser(User user) {
        repository.save(user);
    }
    public void deleteUser(User user) {
        // Log the deletion of the account
        logger.info("Deleting account for user '{}'.", user.getUsername());

        repository.delete(user);
    }
    public User getUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

}