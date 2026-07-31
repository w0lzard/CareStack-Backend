-- V1: initial schema for the hospital management app.
-- Column names follow Hibernate's default snake_case physical naming strategy,
-- so these must stay in lockstep with the @Entity classes (Hibernate runs in
-- ddl-auto=validate, meaning it fails fast on startup if this schema and the
-- entities disagree - that mismatch is the point, it's the safety net).


CREATE TABLE doctors (
    id               BIGSERIAL PRIMARY KEY,
    full_name        VARCHAR(255) NOT NULL,
    specialization   VARCHAR(50)  NOT NULL,
    license_number   VARCHAR(100) NOT NULL,
    phone_number     VARCHAR(20),
    active           BOOLEAN      NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_doctors_license_number UNIQUE (license_number)
);
