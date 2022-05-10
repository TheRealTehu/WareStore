INSERT INTO warehouse("name", address, storage_space, num_of_workers, req_workers, max_workers)
VALUES ('Center Location', '1011 Budapest Imagine Street 405', 1500, 30, 10, 30);

INSERT INTO warehouse("name", address, storage_space, num_of_workers, req_workers, max_workers)
VALUES ('Store Budakeszi', '1543 Budakeszi Non Exists Street 10', 500, 10, 7, 10);

INSERT INTO warehouse("name", address, storage_space, num_of_workers, req_workers, max_workers)
VALUES ('Store Sárpospatak', '3456 Sárpospatak Never Street 7', 765, 5, 7, 14);

INSERT INTO product("name", description, product_type, price, status, warehouse_id, destination_id, last_modified)
VALUES ('Fifa', 'Football videogame', 'game', 22000, 'in_storage', 1, 1, '05-10-2022 13:09:42');