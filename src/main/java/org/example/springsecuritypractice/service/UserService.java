package org.example.springsecuritypractice.service;

import lombok.RequiredArgsConstructor;
import org.example.springsecuritypractice.dto.RegistrationRequest;
import org.example.springsecuritypractice.model.Role;
import org.example.springsecuritypractice.model.User;
import org.example.springsecuritypractice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // Change this from BCryptPasswordEncoder to PasswordEncoder
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(RegistrationRequest request) {
        // Tip: findByEmail should return Optional<User> (see next bug fix)
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            // Changed to IllegalStateException to match your controller's catch block
            throw new IllegalStateException("Email already exists");
        }
        User user = User.builder().email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();

        return userRepository.save(user);
    }
}