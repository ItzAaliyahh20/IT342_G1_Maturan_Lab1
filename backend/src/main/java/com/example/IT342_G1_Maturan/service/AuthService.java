package com.example.IT342_G1_Maturan.service;

import com.example.IT342_G1_Maturan.dto.AuthResponse;
import com.example.IT342_G1_Maturan.dto.LoginRequest;
import com.example.IT342_G1_Maturan.dto.RegisterRequest;
import com.example.IT342_G1_Maturan.entity.User;
import com.example.IT342_G1_Maturan.repository.UserRepository;
import com.example.IT342_G1_Maturan.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    public AuthResponse registerUser(RegisterRequest req) {
        // simple checks
        if (req.getEmail() == null || req.getPassword() == null) {
            throw new IllegalArgumentException("Email and password required");
        }

        Optional<User> existing = userRepository.findByEmail(req.getEmail());
        if (existing.isPresent()) {
            throw new IllegalStateException("Email already in use");
        }

        String hashed = PasswordEncoder.hash(req.getPassword());
        User u = new User();
        u.setEmail(req.getEmail());
        u.setFirst_name(req.getFirst_name());
        u.setLast_name(req.getLast_name());
        u.setPassword(hashed);

        User saved = userRepository.save(u);
        String token = tokenProvider.createToken(saved.getUser_id());
        return new AuthResponse(token, saved.getUser_id());
    }

    public AuthResponse loginUser(LoginRequest req) {
        Optional<User> found = userRepository.findByEmail(req.getEmail());
        if (found.isEmpty()) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        User u = found.get();
        if (!PasswordEncoder.verify(req.getPassword(), u.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        String token = tokenProvider.createToken(u.getUser_id());
        return new AuthResponse(token, u.getUser_id());
    }

    public Optional<User> validateUser(String token) {
        return tokenProvider.getUserFromToken(token).flatMap(id -> userRepository.findById(id));
    }

    public void logoutUser(String token) {
        tokenProvider.invalidateToken(token);
    }
}
