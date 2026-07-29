package com.ryuken.carestack.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "idempotency_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdempotencyRecord {

    @Id
    private String idempotencyKey;

    @Column(nullable = false)
    private String requestHash;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String responseBody;

    @Column(nullable = false)
    private int statusCode;

    @Column(nullable = false)
    private Instant createdAt;
}
