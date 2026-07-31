-- V1: initial schema for the hospital management app.
-- Column names follow Hibernate's default snake_case physical naming strategy,
-- so these must stay in lockstep with the @Entity classes (Hibernate runs in
-- ddl-auto=validate, meaning it fails fast on startup if this schema and the
-- entities disagree - that mismatch is the point, it's the safety net).

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
