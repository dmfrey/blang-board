create table domain_event (
    id VARCHAR(36) primary key,
    occurred_on TIMESTAMP not null,
    data TEXT not null,
    project_key VARCHAR(256)
)