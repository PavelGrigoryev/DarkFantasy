--liquibase formatted sql

--changeset Grigoryev_Pavel:1
CREATE TABLE IF NOT EXISTS hero_entity
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR NOT NULL,
    hero_type   VARCHAR NOT NULL,
    hero_status VARCHAR NOT NULL,
    level       INT     NOT NULL,
    health      INT     NOT NULL
);
