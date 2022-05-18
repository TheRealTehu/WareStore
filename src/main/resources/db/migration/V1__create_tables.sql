CREATE TABLE IF NOT EXISTS warehouse(
    id              int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name"          varchar (30) NOT NULL unique,
    address         varchar (50) NOT NULL unique,
    storage_space   int NOT NULL,
    num_of_workers  int NOT NULL,
    req_workers     int NOT NULL,
    max_workers     int NOT NULL
);

CREATE TYPE product_types AS ENUM('game', 'console', 'accessory', 'part', 'other');
CREATE TYPE product_status AS ENUM('in_storage', 'reserved', 'moving', 'sold');

CREATE TABLE IF NOT EXISTS product(
    id              int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name"          varchar(100) NOT NULL,
    description     varchar(500) NOT NULL,
    product_type    product_types NOT NULL,
    price           int NOT NULL,
    status          product_status NOT NULL,
    warehouse_id    int,
    destination_id  int,
    last_modified   timestamp
);

CREATE TYPE work_positions AS ENUM('warehouse_worker', 'clerk', 'center_worker', 'it_worker', 'boss');

DROP SEQUENCE IF EXISTS worker;
CREATE TABLE IF NOT EXISTS worker(
    id              int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name"          varchar(50) NOT NULL,
    "position"      work_positions,
    salary          DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS worker_to_workplace(
    id              int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    worker_id       int NOT NULL,
    warehouse_id    int NOT NULL,
    "date"          timestamp NOT NULL,
    hours_worked    DOUBLE PRECISION NOT NULL
);

ALTER TABLE worker_to_workplace
    ADD FOREIGN KEY (worker_id)
        REFERENCES worker(id)
        ON DELETE CASCADE;
ALTER TABLE worker_to_workplace
    ADD FOREIGN KEY (warehouse_id)
        REFERENCES warehouse(id)
        ON DELETE CASCADE;