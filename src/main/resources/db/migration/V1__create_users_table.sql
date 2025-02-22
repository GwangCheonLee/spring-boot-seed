-- 파일명: V1__create_users_table.sql

CREATE TABLE users
(
    id                      UUID                  DEFAULT ${uuidFunction} PRIMARY KEY,
    email                   VARCHAR(255) NOT NULL UNIQUE,
    password                VARCHAR(255) NOT NULL,
    name                    VARCHAR(255) NOT NULL,
    authentication_provider VARCHAR(50),
    role                    VARCHAR(50)           DEFAULT 'USER',
    is_deleted              BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at              TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
