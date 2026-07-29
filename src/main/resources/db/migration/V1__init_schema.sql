-- V1: initial schema for the hospital management app.
-- Column names follow Hibernate's default snake_case physical naming strategy,
-- so these must stay in lockstep with the @Entity classes (Hibernate runs in
-- ddl-auto=validate, meaning it fails fast on startup if this schema and the
-- entities disagree - that mismatch is the point, it's the safety net).

CREATE TABLE patients (
    id              BIGSERIAL PRIMARY KEY,
    full_name       VARCHAR(255) NOT NULL,
    date_of_birth   DATE         NOT NULL,
    phone_number    VARCHAR(20)  NOT NULL,
    email           VARCHAR(255),
    blood_group     VARCHAR(10),
    address         VARCHAR(500),
    CONSTRAINT uq_patients_phone_number UNIQUE (phone_number)
);

CREATE TABLE doctors (
    id               BIGSERIAL PRIMARY KEY,
    full_name        VARCHAR(255) NOT NULL,
    specialization   VARCHAR(50)  NOT NULL,
    license_number   VARCHAR(100) NOT NULL,
    phone_number     VARCHAR(20),
    active           BOOLEAN      NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_doctors_license_number UNIQUE (license_number)
);

CREATE TABLE appointments (
    id                 BIGSERIAL PRIMARY KEY,
    patient_id         BIGINT       NOT NULL,
    doctor_id          BIGINT       NOT NULL,
    scheduled_at       TIMESTAMP    NOT NULL,
    status             VARCHAR(20)  NOT NULL,
    reason_for_visit   VARCHAR(500),
    CONSTRAINT fk_appointments_patient FOREIGN KEY (patient_id) REFERENCES patients (id),
    CONSTRAINT fk_appointments_doctor  FOREIGN KEY (doctor_id)  REFERENCES doctors (id)
);

CREATE INDEX idx_appointments_scheduled_at_status ON appointments (scheduled_at, status);
CREATE INDEX idx_appointments_patient_id ON appointments (patient_id);

CREATE TABLE payments (
    id                       BIGSERIAL PRIMARY KEY,
    patient_id               BIGINT         NOT NULL,
    amount                   NUMERIC(12, 2) NOT NULL,
    method                   VARCHAR(20)    NOT NULL,
    status                   VARCHAR(20)    NOT NULL,
    transaction_reference    VARCHAR(255),
    created_at               TIMESTAMP      NOT NULL,
    CONSTRAINT fk_payments_patient FOREIGN KEY (patient_id) REFERENCES patients (id)
);

CREATE TABLE idempotency_records (
    idempotency_key   VARCHAR(255) PRIMARY KEY,
    request_hash      VARCHAR(255) NOT NULL,
    response_body     TEXT         NOT NULL,
    status_code       INTEGER      NOT NULL,
    created_at        TIMESTAMP    NOT NULL
);

CREATE TABLE inventory_items (
    id                  BIGSERIAL PRIMARY KEY,
    sku                 VARCHAR(100) NOT NULL,
    medicine_name       VARCHAR(255) NOT NULL,
    quantity_on_hand    INTEGER      NOT NULL,
    reorder_threshold   INTEGER      NOT NULL,
    CONSTRAINT uq_inventory_items_sku UNIQUE (sku)
);

CREATE TABLE reconciliation_discrepancies (
    id                  BIGSERIAL PRIMARY KEY,
    sku                 VARCHAR(100) NOT NULL,
    local_quantity      INTEGER      NOT NULL,
    supplier_quantity   INTEGER      NOT NULL,
    detected_at         TIMESTAMP    NOT NULL,
    resolved            BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE medical_records (
    id               BIGSERIAL PRIMARY KEY,
    patient_id       BIGINT    NOT NULL,
    appointment_id   BIGINT    NOT NULL,
    diagnosis        TEXT      NOT NULL,
    notes            TEXT,
    recorded_at      TIMESTAMP NOT NULL,
    CONSTRAINT fk_medical_records_patient     FOREIGN KEY (patient_id)     REFERENCES patients (id),
    CONSTRAINT fk_medical_records_appointment FOREIGN KEY (appointment_id) REFERENCES appointments (id)
);

CREATE INDEX idx_medical_records_patient_id ON medical_records (patient_id);
