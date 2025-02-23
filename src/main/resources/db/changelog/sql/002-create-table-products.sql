--liquibase formatted sql
--changeset kang:2

CREATE TABLE IF NOT EXISTS product_bcs_products
(
    id           UUID PRIMARY KEY,
    created_at   TIMESTAMP,
    deleted_at   TIMESTAMP,
    updated_at   TIMESTAMP,
    created_by   UUID,
    description  TEXT,
    name         VARCHAR(255),
    price        INTEGER,
    shipping_fee INTEGER
    );

CREATE TABLE IF NOT EXISTS product_bcs_options
(
    id          UUID PRIMARY KEY,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    extra_price INTEGER NOT NULL,
    name        VARCHAR(255),
    type        VARCHAR(255),
    product_id  UUID NOT NULL,
    CONSTRAINT fk_products FOREIGN KEY (product_id) REFERENCES product_bcs_products
    );

CREATE TABLE IF NOT EXISTS product_bcs_option_values
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    value      VARCHAR(255) NOT NULL,
    option_id  UUID NOT NULL,
    CONSTRAINT fk_product_options FOREIGN KEY (option_id) REFERENCES product_bcs_options
    );