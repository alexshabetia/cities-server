CREATE TABLE IF NOT EXISTS cities
(
    id         serial,
    uuid       uuid         NOT NULL UNIQUE,
    name       varchar(255) NOT NULL,
    photo_link varchar(2048),
    PRIMARY KEY (id)
);

CREATE INDEX cities_name_idx
    ON cities (name);