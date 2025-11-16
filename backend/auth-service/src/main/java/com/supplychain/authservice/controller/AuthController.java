package com.supplychain.authservice.controller;

import com.supplychain.authservice.model.RefreshToken;
import com.supplychain.authservice.model.User;
import com.supplychain.authservice.service.AuthService;
import com.supplychain.authservice.security.KeyManager;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final KeyManager keyManager;

    @Value("${jwt.refresh-token-exp-days:30}")
    private long refreshDays;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        var opt = authService.authenticate(req.getEmail(), req.getPassword());
        if (opt.isEmpty()) return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));

        User u = opt.get();
        String access = authService.createAccessToken(u);
        String refresh = authService.createRefreshToken(u.getId(), refreshDays);

        return ResponseEntity.ok(Map.of("accessToken", access, "refreshToken", refresh));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshReq req) {
        var rtOpt = authService.validateRefreshToken(req.getRefreshToken());
        if (rtOpt.isEmpty()) return ResponseEntity.status(401).body(Map.of("error", "Invalid refresh token"));

        RefreshToken rt = rtOpt.get();
        User u = authService.userRepo.findById(rt.getUserId()).orElseThrow();
        String access = authService.createAccessToken(u);
        return ResponseEntity.ok(Map.of("accessToken", access));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutReq req) {
        if (req.getRefreshToken() != null) authService.revokeRefreshToken(req.getRefreshToken());
        return ResponseEntity.ok(Map.of("ok", true));
    }

    // JWKS endpoint
    @GetMapping("/.well-known/jwks.json")
    public ResponseEntity<?> jwks() {
        var jwk = keyManager.getRsaJwk().toPublicJWK();
        return ResponseEntity.ok(Map.of("keys", java.util.List.of(jwk.toJSONObject())));
    }

    @Data static class LoginReq { private String email; private String password; }
    @Data static class RefreshReq { private String refreshToken; }
    @Data static class LogoutReq { private String refreshToken; }
}
