CREATE DATABASE car_catalog;

\c car_catalog;

CREATE TABLE engines (
	engine_id serial PRIMARY KEY,
	fuel_type varchar(30),
	volume decimal(2,1)
);

INSERT INTO engines (fuel_type, volume)
VALUES ('petrol', 2.3),
('petrol', 2.5),
('diesel', 2.5),
('petrol', 1.5),
('petrol', 3.0),
('diesel', 3.5);

CREATE TABLE transmissions (
	transmission_id serial PRIMARY KEY,
	type varchar(30),
	gear_num integer
);

INSERT INTO transmissions (type, gear_num)
VALUES ('mechanical', 4),
('mechanical', 5),
('mechanical', 6),
('automatic', 4),
('automatic', 5),
('robotic', 4),
('variable', 3);

CREATE TABLE car_bodies (
	body_id serial PRIMARY KEY,
	type varchar(30),
	seat_num integer
);

INSERT INTO car_bodies (type, seat_num)
VALUES ('hatchback', 4),
('minivan', 8),
('sports_car', 2),
('sedan', 5),
('station_wagon', 5),
('off_road_car', 5),
('limousine', 6);

CREATE TABLE cars (
	car_id serial PRIMARY KEY,
	model VARCHAR(50),
	car_engine_id INTEGER NOT NULL
	REFERENCES engines (engine_id),
	car_transmission_id INTEGER NOT NULL
	REFERENCES transmissions (transmission_id),
	car_body_id INTEGER NOT NULL
	REFERENCES car_bodies (body_id)
);

INSERT INTO cars (model, car_engine_id, car_transmission_id, car_body_id)
VALUES ('bmw', 2, 2, 4),
('mercedes', 1, 1, 2),
('jaguar', 4, 1, 4),
('honda', 5, 5, 6),
('kia', 6, 3, 3),
('mazda', 2, 4, 1);

--информация обо всех машинах

SELECT c.model, e.fuel_type, e.volume as engine_volume, t.type as transmission_type, t.gear_num as num_of_gears,
cb.type as car_body_type, cb.seat_num as num_of_seats
FROM cars AS c
JOIN engines AS e ON c.car_engine_id = e.engine_id
JOIN transmissions AS t ON c.car_transmission_id = t.transmission_id
JOIN car_bodies AS cb ON c.car_body_id = cb.body_id;

--неиспользуемые детали вместе

SELECT e.engine_id, t.transmission_id, cb.body_id
FROM cars AS c
FULL OUTER JOIN engines AS e ON c.car_engine_id = e.engine_id
FULL OUTER JOIN transmissions AS t ON c.car_transmission_id = t.transmission_id
FULL OUTER JOIN car_bodies AS cb ON c.car_body_id = cb.body_id
WHERE car_id IS NULL;

--неиспользуемые детали по отдельности

SELECT e.engine_id
FROM cars AS c
RIGHT OUTER JOIN engines AS e ON c.car_engine_id = e.engine_id
WHERE car_id IS NULL;

SELECT t.transmission_id
FROM cars AS c
RIGHT OUTER JOIN transmissions AS t ON c.car_transmission_id = t.transmission_id
WHERE car_id IS NULL;

SELECT cb.body_id
FROM cars AS c
RIGHT OUTER JOIN car_bodies AS cb ON c.car_body_id = cb.body_id
WHERE car_id IS NULL;