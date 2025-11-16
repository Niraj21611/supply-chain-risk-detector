package package com.supplychain.authservice.service;

import com.supplychain.authservice.model.RefreshToken;
import com.supplychain.authservice.model.User;
import com.supplychain.authservice.repo.RefreshTokenRepository;
import com.supplychain.authservice.repo.UserRepository;
import com.supplychain.authservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final RefreshTokenRepository refreshRepo;
    private final JwtService jwtService;

    public Optional<User> authenticate(String email, String rawPassword) {
        return userRepo.findByEmail(email)
                .filter(User::isActive)
                .filter(u -> BCrypt.checkpw(rawPassword, u.getPasswordHash()));
    }

    public String createRefreshToken(Long userId, long daysValid) {
        String token = UUID.randomUUID().toString();
        RefreshToken rt = new RefreshToken();
        rt.setToken(token);
        rt.setUserId(userId);
        rt.setExpiresAt(Instant.now().plusSeconds(daysValid * 24 * 3600));
        refreshRepo.save(rt);
        return token;
    }

    public Optional<RefreshToken> validateRefreshToken(String token) {
        return refreshRepo.findByToken(token).filter(rt -> rt.getExpiresAt().isAfter(Instant.now()));
    }

    public void revokeRefreshToken(String token) { refreshRepo.deleteByToken(token); }

    public String createAccessToken(User u) {
        List<String> roles = Collections.singletonList(u.getRole());
        return jwtService.generateAccessToken(u.getEmail(), u.getId(), u.getVendorId(), roles);
    }

    public User createUserIfNotExists(String email, String password, String role, Long vendorId) {
        return userRepo.findByEmail(email).orElseGet(() -> {
            User u = new User();
            u.setEmail(email);
            u.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
            u.setRole(role);
            u.setVendorId(vendorId);
            return userRepo.save(u);
        });
    }
}
