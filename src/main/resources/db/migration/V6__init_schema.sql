-- V1: initial schema for the hospital management app.
-- Column names follow Hibernate's default snake_case physical naming strategy,
-- so these must stay in lockstep with the @Entity classes (Hibernate runs in
-- ddl-auto=validate, meaning it fails fast on startup if this schema and the
-- entities disagree - that mismatch is the point, it's the safety net).

CREATE TABLE inventory_items (
    id                  BIGSERIAL PRIMARY KEY,
    sku                 VARCHAR(100) NOT NULL,
    medicine_name       VARCHAR(255) NOT NULL,
    quantity_on_hand    INTEGER      NOT NULL,
    reorder_threshold   INTEGER      NOT NULL,
    CONSTRAINT uq_inventory_items_sku UNIQUE (sku)
);
