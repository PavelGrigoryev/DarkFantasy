--liquibase formatted sql

--changeset Grigoryev_Pavel:2
INSERT INTO hero_entity (name, hero_type, hero_status, level, health)
VALUES ('Aragorn', 'WARRIOR', 'EXPLORING', 10, 100),
       ('Legolas', 'HUNTER', 'FIGHTING', 8, 90),
       ('Gandalf', 'MAGE', 'RESTING', 15, 150),
       ('Anduin', 'PRIEST', 'EXPLORING', 12, 120),
       ('Valeera', 'ROGUE', 'FIGHTING', 9, 80),
       ('Thrall', 'WARRIOR', 'RESTING', 11, 110),
       ('Rexxar', 'HUNTER', 'EXPLORING', 7, 70),
       ('Jaina', 'MAGE', 'FIGHTING', 14, 140),
       ('Tyrande', 'PRIEST', 'RESTING', 13, 130),
       ('Edwin', 'ROGUE', 'EXPLORING', 5, 50);
