--liquibase formatted sql
--changeset kang:1

CREATE TABLE IF NOT EXISTS user_bcs_users
(
    id         UUID PRIMARY KEY,
    created_at TIMESTAMP,
    deleted_at TIMESTAMP,
    updated_at TIMESTAMP,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role_type  VARCHAR(255) NOT NULL
    );