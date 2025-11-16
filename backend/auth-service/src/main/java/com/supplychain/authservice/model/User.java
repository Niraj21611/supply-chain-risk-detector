package com.supplychain.authservice.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String passwordHash;
    private String role;
    private Long vendorId;
    private boolean active = true;
    private Instant createdAt = Instant.now();
}
