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

create unique index contact_uuid_type_index
    on contact (resume_uuid, type);

CREATE TABLE IF NOT EXISTS section
(
    id          serial      NOT NULL
        CONSTRAINT section_pk
            PRIMARY KEY,
    resume_uuid varchar(36) NOT NULL
        CONSTRAINT section_resume_uuid_fk
            REFERENCES resume
            ON UPDATE RESTRICT ON DELETE CASCADE,
    type        text        NOT NULL,
    value       text        NOT NULL
);

create unique index section_uuid_type_index
    on section (resume_uuid, type);