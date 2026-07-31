-- V1: initial schema for the hospital management app.
-- Column names follow Hibernate's default snake_case physical naming strategy,
-- so these must stay in lockstep with the @Entity classes (Hibernate runs in
-- ddl-auto=validate, meaning it fails fast on startup if this schema and the
-- entities disagree - that mismatch is the point, it's the safety net).

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
