--music types
CREATE TABLE music_types
(
  id SERIAL PRIMARY KEY , 
  title VARCHAR(30),
  description VARCHAR(200)
);

INSERT INTO music_types (title, description) VALUES ('rock','rock info');
INSERT INTO music_types (title, description) VALUES ('rap','rap info');
INSERT INTO music_types (title, description) VALUES ('classic','classic info');
INSERT INTO music_types (title, description) VALUES ('jazz','jazz info');
INSERT INTO music_types (title, description) VALUES ('folk','folk info');
INSERT INTO music_types (title, description) VALUES ('metal','metal info');

--addresses
CREATE TABLE addresses
(
  id SERIAL PRIMARY KEY , 
  street_name VARCHAR(30),
  house_no VARCHAR(10),
  apartment_no VARCHAR(10)
);

INSERT INTO addresses (street_name, house_no, apartment_no) VALUES ('street one', '1h', '1a');
INSERT INTO addresses (street_name, house_no, apartment_no) VALUES ('street two', '2h', '2a');
INSERT INTO addresses (street_name, house_no, apartment_no) VALUES ('street three', '3h', '3a');
INSERT INTO addresses (street_name, house_no, apartment_no) VALUES ('street four', '4h', '4a');
INSERT INTO addresses (street_name, house_no, apartment_no) VALUES ('street five', '5h', '5a');

--users
CREATE TABLE users
(
  id SERIAL PRIMARY KEY , 
  name VARCHAR(30) NOT NULL,
  login VARCHAR(30) UNIQUE NOT NULL,
  password VARCHAR(30) NOT NULL,
  email VARCHAR(30),
  role_id INTEGER REFERENCES roles (id) NOT NULL,
  address_id INTEGER REFERENCES addresses (id) NOT NULL
);

INSERT INTO users (name, login, password, email, role_id, address_id) VALUES ('name 1', 'n1', '1', 'n1@mail.ru', 3, 1);
INSERT INTO users (name, login, password, email, role_id, address_id) VALUES ('name 2', 'n2', '2', 'n2@mail.ru', 2, 1);
INSERT INTO users (name, login, password, email, role_id, address_id) VALUES ('name 3', 'n3', '3', 'n3@mail.ru', 2, 2);
INSERT INTO users (name, login, password, email, role_id, address_id) VALUES ('name 4', 'n4', '4', 'n4@mail.ru', 1, 3);
INSERT INTO users (name, login, password, email, role_id, address_id) VALUES ('name 5', 'n5', '5', 'n5@mail.ru', 1, 3);
INSERT INTO users (name, login, password, email, role_id, address_id) VALUES ('name 6', 'n6', '6', 'n6@mail.ru', 1, 4);
INSERT INTO users (name, login, password, email, role_id, address_id) VALUES ('name 7', 'n7', '7', 'n7@mail.ru', 1, 5);

--table users_music_types : 
CREATE TABLE users_music_types
(
  user_id INTEGER REFERENCES users (id), 
  music_type_id  INTEGER REFERENCES music_types (id),
  PRIMARY KEY( user_id, music_type_id)
);

INSERT INTO users_music_types (user_id, music_type_id) VALUES (1, 1);
INSERT INTO users_music_types (user_id, music_type_id) VALUES (1, 4);
INSERT INTO users_music_types (user_id, music_type_id) VALUES (1, 6);
INSERT INTO users_music_types (user_id, music_type_id) VALUES (2, 2);
INSERT INTO users_music_types (user_id, music_type_id) VALUES (2, 5);
INSERT INTO users_music_types (user_id, music_type_id) VALUES (3, 6);
INSERT INTO users_music_types (user_id, music_type_id) VALUES (3, 1);
INSERT INTO users_music_types (user_id, music_type_id) VALUES (4, 2);
INSERT INTO users_music_types (user_id, music_type_id) VALUES (4, 5);
INSERT INTO users_music_types (user_id, music_type_id) VALUES (5, 3);
INSERT INTO users_music_types (user_id, music_type_id) VALUES (5, 4);
INSERT INTO users_music_types (user_id, music_type_id) VALUES (5, 5);
INSERT INTO users_music_types (user_id, music_type_id) VALUES (6, 6);
INSERT INTO users_music_types (user_id, music_type_id) VALUES (7, 1);
INSERT INTO users_music_types (user_id, music_type_id) VALUES (7, 6);


--table roles :
CREATE TABLE  roles
(
  id SERIAL PRIMARY KEY, 
  name VARCHAR(30),
  description VARCHAR(200)
);

INSERT INTO roles (name, description) VALUES ('user','user info');
INSERT INTO roles (name, description) VALUES ('mandator','mandator info');
INSERT INTO roles (name, description) VALUES ('admin','admin info');

--privileges
CREATE TABLE privileges
(
  id SERIAL PRIMARY KEY,
  privilege_name VARCHAR(20),
  description VARCHAR(150)
);

INSERT INTO privileges (privilege_name, description) VALUES('insert', 'inserts user in the database');
INSERT INTO privileges (privilege_name, description) VALUES('delete', 'deletes user from the database');
INSERT INTO privileges (privilege_name, description) VALUES('update', 'updates user in the database');
INSERT INTO privileges (privilege_name, description) VALUES('select_by_id', 'select user by id from the database');
INSERT INTO privileges (privilege_name, description) VALUES('select_by_login', 'select user by login from the database');
INSERT INTO privileges (privilege_name, description) VALUES('select_by_role', 'select user by role from the database');
INSERT INTO privileges (privilege_name, description) VALUES('select_by_music_type', 'select user by music type from the database');
INSERT INTO privileges (privilege_name, description) VALUES('select_by_address', 'selects all user by address from the database');
INSERT INTO privileges (privilege_name, description) VALUES('select_all', 'selects all users from the database');


--roles_and_privileges
CREATE TABLE roles_and_privileges
(
  role_id INTEGER REFERENCES roles(id) NOT NULL,
  privilege_id INTEGER REFERENCES privileges(id) NOT NULL,
  PRIMARY KEY (role_id, privilege_id)
);

INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(3, 1);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(3, 2);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(3, 3);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(3, 4);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(3, 5);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(3, 6);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(3, 7);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(3, 8);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(3, 9);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(2, 1);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(2, 3);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(2, 4);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(2, 5);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(2, 6);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(2, 7);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(2, 8);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(2, 9);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(1, 3);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(1, 4);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(1, 5);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(1, 6);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(1, 7);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(1, 8);
INSERT INTO roles_and_privileges (role_id, privilege_id) VALUES(1, 9);