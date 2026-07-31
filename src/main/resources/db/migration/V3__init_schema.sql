-- V1: initial schema for the hospital management app.
-- Column names follow Hibernate's default snake_case physical naming strategy,
-- so these must stay in lockstep with the @Entity classes (Hibernate runs in
-- ddl-auto=validate, meaning it fails fast on startup if this schema and the
-- entities disagree - that mismatch is the point, it's the safety net).

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
