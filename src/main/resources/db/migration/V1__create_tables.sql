CREATE TABLE IF NOT EXISTS warehouse(
    id              bigserial not null,
    "name"          varchar (30) NOT NULL unique,
    address         varchar (50) NOT NULL unique,
    storage_space   int4 NOT NULL,
    num_of_workers  int4 NOT NULL,
    req_workers     int4 NOT NULL,
    max_workers     int4 NOT NULL,
    primary key (id)
);

CREATE TYPE product_types AS ENUM('game', 'console', 'accessory', 'part', 'other');
CREATE TYPE product_status AS ENUM('in_storage', 'reserved', 'moving', 'sold');

CREATE TABLE IF NOT EXISTS product(
    id              bigserial not null,
    "name"          varchar(100) NOT NULL,
    description     varchar(500) NOT NULL,
    product_type    product_types NOT NULL,
    price           int4 NOT NULL,
    status          product_status NOT NULL,
    warehouse_id    int8,
    destination_id  int8,
    last_modified   timestamp,
    primary key (id)
);

CREATE TYPE work_positions AS ENUM('warehouse_worker', 'clerk', 'center_worker', 'it_worker', 'boss');

DROP SEQUENCE IF EXISTS worker;
CREATE TABLE IF NOT EXISTS worker(
    id              bigserial not null,
    "name"          varchar(50) NOT NULL,
    "position"      work_positions,
    salary          float8,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS worker_to_workplace(
    id              bigserial not null,
    worker_id       int8 NOT NULL,
    warehouse_id    int8 NOT NULL,
    "date"          timestamp NOT NULL,
    hours_worked    float8 NOT NULL,
    primary key (id)
);

ALTER TABLE worker_to_workplace
    ADD CONSTRAINT fk_workday_worker
        FOREIGN KEY (worker_id)
        REFERENCES worker
        ON DELETE CASCADE;
ALTER TABLE worker_to_workplace
    ADD CONSTRAINT fk_workday_warehouse
        FOREIGN KEY (warehouse_id)
        REFERENCES warehouse
        ON DELETE CASCADE;
ALTER TABLE product
    ADD CONSTRAINT fk_product_warehouse FOREIGN KEY (warehouse_id)
        REFERENCES warehouse
        ON DELETE CASCADE;
ALTER TABLE product
    ADD CONSTRAINT fk_product_destination FOREIGN KEY (destination_id)
        REFERENCES warehouse
        ON DELETE CASCADE;