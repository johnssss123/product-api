package com.ecommerce.product_api.service;

import com.ecommerce.product_api.dto.AuthResponseDTO;
import com.ecommerce.product_api.dto.LoginRequestDTO;
import com.ecommerce.product_api.dto.RegisterRequestDTO;
import com.ecommerce.product_api.entity.User;
import com.ecommerce.product_api.exception.ResourceNotFoundException;
import com.ecommerce.product_api.repository.UserRepository;
import com.ecommerce.product_api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Register
    public String register(RegisterRequestDTO request) {

        // Check if username already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole().toUpperCase());

        userRepository.save(user);

        return "User registered successfully";
    }

    // Login
    public AuthResponseDTO login(LoginRequestDTO request) {

        // Find user by username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with username: " + request.getUsername()));

        // Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return new AuthResponseDTO(token, user.getUsername(), user.getRole());
    }
}
