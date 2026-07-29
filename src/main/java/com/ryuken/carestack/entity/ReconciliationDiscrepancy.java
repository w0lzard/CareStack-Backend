package com.ryuken.carestack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "reconciliation_discrepancies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReconciliationDiscrepancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private int localQuantity;

    @Column(nullable = false)
    private int supplierQuantity;

    @Column(nullable = false)
    private Instant detectedAt;

    @Column(nullable = false)
    private boolean resolved=false;
}
