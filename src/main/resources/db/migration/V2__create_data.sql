/*Warehouse data*/
INSERT INTO warehouse("name", address, storage_space, num_of_workers, req_workers, max_workers)
VALUES ('Center Location', '1011 Budapest Imagine Street 405', 1500, 30, 10, 30);

INSERT INTO warehouse("name", address, storage_space, num_of_workers, req_workers, max_workers)
VALUES ('Store Budakeszi', '1543 Budakeszi Non Exists Street 10', 500, 10, 7, 10);

INSERT INTO warehouse("name", address, storage_space, num_of_workers, req_workers, max_workers)
VALUES ('Store Sárpospatak', '3456 Sárpospatak Never Street 7', 765, 5, 7, 14);

/*warehouse 1 products*/
INSERT INTO product("name", description, product_type, price, status, warehouse_id, destination_id, last_modified)
VALUES ('Fifa', 'Football videogame', 'game', 22000, 'in_storage', 1, 1, '05-10-2022 13:09:42');

INSERT INTO product("name", description, product_type, price, status, warehouse_id, destination_id, last_modified)
VALUES ('Mario', 'Jumping videogame', 'game', 24999, 'in_storage', 1, 1, '05-13-2022 17:52:33');

INSERT INTO product("name", description, product_type, price, status, warehouse_id, destination_id, last_modified)
VALUES ('Doom', 'Shooting videogame', 'game', 5000, 'in_storage', 1, 1, '05-13-2022 08:10:30');

INSERT INTO product("name", description, product_type, price, status, warehouse_id, destination_id, last_modified)
VALUES ('Fifa', 'Football videogame', 'game', 22000, 'in_storage', 1, 1, '01-03-2021 10:11:12');

INSERT INTO product("name", description, product_type, price, status, warehouse_id, destination_id, last_modified)
VALUES ('Fifa', 'Football videogame', 'game', 22000, 'in_storage', 1, 1, '12-31-2022 18:50:42');

/*warehouse 2 products*/
INSERT INTO product("name", description, product_type, price, status, warehouse_id, destination_id, last_modified)
VALUES ('Fifa', 'Football videogame', 'game', 22000, 'in_storage', 2, 2, '05-10-2022 13:09:42');

INSERT INTO product("name", description, product_type, price, status, warehouse_id, destination_id, last_modified)
VALUES ('PS Controller', 'Controller for console', 'accessory', 22000, 'in_storage', 2, 2, '05-10-2022 13:09:42');

INSERT INTO product("name", description, product_type, price, status, warehouse_id, destination_id, last_modified)
VALUES ('Fifa', 'Football videogame', 'game', 22000, 'in_storage', 2, 2, '05-10-2022 13:09:42');

INSERT INTO product("name", description, product_type, price, status, warehouse_id, destination_id, last_modified)
VALUES ('Fifa', 'Football videogame', 'game', 22000, 'in_storage', 2, 2, '05-10-2022 13:09:42');

/*Worker data*/
INSERT INTO worker("name", position, salary) VALUES ('John Boss', 'boss', 5000);

INSERT INTO worker("name", position, salary) VALUES ('IT Worker', 'it_worker', 3000);

INSERT INTO worker("name", position, salary) VALUES ('Carol Center', 'center_worker', 4300);

INSERT INTO worker("name", position, salary) VALUES ('Peter Clerkson', 'clerk', 3500);

INSERT INTO worker("name", position, salary) VALUES ('Michael Worker', 'warehouse_worker', 2000);

INSERT INTO worker("name", position, salary) VALUES ('Gordon Worker', 'warehouse_worker', 2000);

INSERT INTO worker("name", position, salary) VALUES ('Best Worker', 'warehouse_worker', 2500);

/*Work simulation*/
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (1, 1, '05-10-2022 08:00:00', 8);
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (1, 1, '05-11-2022 08:00:00', 8);
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (1, 1, '05-12-2022 08:00:00', 8);

INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (2, 1, '05-10-2022 08:00:00', 8);
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (2, 1, '05-15-2022 08:00:00', 4);

INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (3, 1, '05-15-2022 08:00:00', 8);
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (3, 1, '05-16-2022 08:00:00', 8);

INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (4, 2, '05-15-2022 08:00:00', 8);
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (4, 2, '05-20-2022 08:00:00', 8);
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (4, 2, '06-03-2022 08:00:00', 8);

INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (5, 2, '05-10-2022 08:00:00', 12);
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (5, 3, '10-15-2022 08:00:00', 12);
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (5, 2, '12-04-2022 08:00:00', 12);

INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (6, 3, '05-10-2022 08:00:00', 12);
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (6, 3, '05-11-2022 08:00:00', 12);
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (6, 3, '05-12-2022 08:00:00', 12);
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (6, 3, '05-13-2022 08:00:00', 12);

INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (7, 3, '04-15-2022 08:00:00', 12);
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (7, 3, '05-15-2022 08:00:00', 12);
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (7, 3, '06-15-2022 08:00:00', 12);
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (7, 3, '07-15-2022 08:00:00', 12);
INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) VALUES (7, 3, '08-15-2022 08:00:00', 12);