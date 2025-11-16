package com.supplychain.authservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    private final KeyManager keyManager;
    private final long accessExpSec;

    public JwtService(KeyManager keyManager, @Value("${jwt.access-token-expiration-sec}") long accessExpSec) {
        this.keyManager = keyManager;
        this.accessExpSec = accessExpSec;
    }

    public String generateAccessToken(String subject, Long userId, Long vendorId, List<String> roles) {
        RSAPrivateKey priv = keyManager.getPrivateKey();
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessExpSec);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .claim("user_id", userId)
                .claim("vendor_id", vendorId)
                .claim("roles", roles)
                .signWith(priv, SignatureAlgorithm.RS256)
                .compact();
    }
}
