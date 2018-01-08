CREATE TABLE engines (
	engine_id serial PRIMARY KEY,
	fuel_type varchar(30),
	volume decimal(2,1)
);

CREATE TABLE transmissions (
	transmission_id serial PRIMARY KEY,
	type varchar(30),
	gear_num integer
);

CREATE TABLE car_bodies (
	body_id serial PRIMARY KEY,
	type varchar(30),
	seat_num integer
);

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