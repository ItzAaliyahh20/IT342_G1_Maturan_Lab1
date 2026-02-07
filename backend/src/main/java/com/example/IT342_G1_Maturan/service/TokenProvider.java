package com.example.IT342_G1_Maturan.service;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenProvider {

    private static class TokenEntry {
        final Integer userId;
        final Instant expiresAt;

        TokenEntry(Integer userId, Instant expiresAt) {
            this.userId = userId;
            this.expiresAt = expiresAt;
        }
    }

    private final Map<String, TokenEntry> tokenStore = new ConcurrentHashMap<>();

    public String createToken(Integer userId) {
        String token = UUID.randomUUID().toString();
        Instant expires = Instant.now().plusSeconds(60 * 60 * 24); // 24h
        tokenStore.put(token, new TokenEntry(userId, expires));
        return token;
    }

    public Optional<Integer> getUserFromToken(String token) {
        TokenEntry entry = tokenStore.get(token);
        if (entry == null) return Optional.empty();
        if (entry.expiresAt.isBefore(Instant.now())) {
            tokenStore.remove(token);
            return Optional.empty();
        }
        return Optional.of(entry.userId);
    }

    public boolean verifyToken(String token) {
        return getUserFromToken(token).isPresent();
    }

    public void invalidateToken(String token) {
        tokenStore.remove(token);
    }
}
