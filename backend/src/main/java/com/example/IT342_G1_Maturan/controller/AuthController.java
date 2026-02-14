package com.example.IT342_G1_Maturan.controller;

import com.example.IT342_G1_Maturan.dto.AuthResponse;
import com.example.IT342_G1_Maturan.dto.LoginRequest;
import com.example.IT342_G1_Maturan.dto.RegisterRequest;
import com.example.IT342_G1_Maturan.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req) {
        AuthResponse resp = authService.registerUser(req);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        AuthResponse resp = authService.loginUser(req);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = "Authorization", required = false) String authHeader,
                                    @RequestParam(name = "token", required = false) String tokenParam) {
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        } else if (tokenParam != null) {
            token = tokenParam;
        }

        if (token != null) {
            authService.logoutUser(token);
        }
        return ResponseEntity.ok().build();
    }
}
