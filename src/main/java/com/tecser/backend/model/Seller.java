package com.tecser.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    private String phone;

    @Column(name = "document_id", unique = true)
    private String documentId;

    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime registrationDate;

    @Column(name = "is_active")
    private Boolean isActive = true;
}