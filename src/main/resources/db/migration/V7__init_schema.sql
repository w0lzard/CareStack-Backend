-- V1: initial schema for the hospital management app.
-- Column names follow Hibernate's default snake_case physical naming strategy,
-- so these must stay in lockstep with the @Entity classes (Hibernate runs in
-- ddl-auto=validate, meaning it fails fast on startup if this schema and the
-- entities disagree - that mismatch is the point, it's the safety net).

CREATE TABLE reconciliation_discrepancies (
    id                  BIGSERIAL PRIMARY KEY,
    sku                 VARCHAR(100) NOT NULL,
    local_quantity      INTEGER      NOT NULL,
    supplier_quantity   INTEGER      NOT NULL,
    detected_at         TIMESTAMP    NOT NULL,
    resolved            BOOLEAN      NOT NULL DEFAULT FALSE
);
