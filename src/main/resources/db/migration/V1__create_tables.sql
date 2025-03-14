create table TRACKED_CERTIFICATES (
    id serial primary key,
    name varchar(50) not null,
    description varchar(255) not null,
    url varchar(255) not null,
    cert_type int not null
);