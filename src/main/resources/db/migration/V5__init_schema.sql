-- V1: initial schema for the hospital management app.
-- Column names follow Hibernate's default snake_case physical naming strategy,
-- so these must stay in lockstep with the @Entity classes (Hibernate runs in
-- ddl-auto=validate, meaning it fails fast on startup if this schema and the
-- entities disagree - that mismatch is the point, it's the safety net).

CREATE TABLE idempotency_records (
    idempotency_key   VARCHAR(255) PRIMARY KEY,
    request_hash      VARCHAR(255) NOT NULL,
    response_body     TEXT         NOT NULL,
    status_code       INTEGER      NOT NULL,
    created_at        TIMESTAMP    NOT NULL
);