--liquibase formatted sql

--changeset mrmasterz:2024-06-09-001-user
CREATE TABLE USERS(
    user_id BIGSERIAL PRIMARY KEY,
    username varchar(128),
    first_name varchar(128),
    last_name varchar(128),
    email varchar(128),
    phone varchar(16)
    ) 
