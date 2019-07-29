CREATE TABLE resume
(
    uuid      VARCHAR(36) NOT NULL
        CONSTRAINT resume_pk
            PRIMARY KEY,
    full_name TEXT
);

CREATE TABLE IF NOT EXISTS contact
(
    id          SERIAL   NOT NULL
        CONSTRAINT contact_pk
            PRIMARY KEY,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL,
    resume_uuid VARCHAR(36) NOT NULL
        CONSTRAINT contact_resume_uuid_fk
            REFERENCES resume
            ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE UNIQUE INDEX resume_pk
    ON resume (uuid);

