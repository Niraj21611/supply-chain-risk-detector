package com.supplychain.authservice;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class Bootstrap {

    private final AuthService authService;

    @Bean
    CommandLineRunner seed() {
        return args -> {
            authService.createUserIfNotExists("admin@platform.com", "admin123", "ADMIN", null);
            System.out.println("Seeded admin@platform.com / admin123");
        };
    }
}
