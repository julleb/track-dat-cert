create table TRACKED_CERTIFICATES (
    tracked_certificates_id serial primary key,
    name varchar(50) unique not null,
    description varchar(255) not null,
    url varchar(255) not null,
    cert_type int not null
);

create table CERTIFICATES (
    certificate_id serial primary key,
    tracked_certificates_id int not null,
    valid_from timestamp not null,
    valid_to timestamp not null,
    common_name varchar(255) not null,
    issuer varchar(255) not null,
    FOREIGN KEY (certificate_id) REFERENCES TRACKED_CERTIFICATES(tracked_certificates_id)
);