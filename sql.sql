CREATE DATABASE request_tracker;

--connect to request_tracker
 
CREATE TYPE privileges AS ENUM ('insert', 'delete', 'update', 'create', 'drop', 'process request');

CREATE TABLE roles (
    name character varying(30),
    privilege privileges,
    PRIMARY KEY (name, privilege)
);

CREATE TABLE users (
    id  integer PRIMARY KEY,
    name character varying(30),
	login character varying(30),
	password character varying(30),
	create_date timestamp
);

CREATE TYPE categories AS ENUM ('low', 'normal', 'high');
CREATE TYPE request_states AS ENUM ('new', 'in process', 'closed');

CREATE TABLE requests (
    id integer PRIMARY KEY,
    description text,
	category categories,
	state request_states,
	user_id integer REFERENCES users (id)
);

CREATE TABLE files (
    name character varying(30),
    path text,
	extension character varying(10),
	request_id integer REFERENCES requests (id),
	PRIMARY KEY (name, extension)
);
CREATE TABLE comments (
    id integer PRIMARY KEY,
    description text,
	request_id integer REFERENCES requests (id)
);

INSERT INTO roles(name, privilege) VALUES 
('admin', 'insert'),
('admin', 'create'),
('admin', 'drop'),
('admin', 'update'),
('manager', 'insert'),
('manager', 'update'),
('employee', 'process request');

INSERT INTO users (id, name, login, password, create_date) VALUES
(1, 'Иван Петров', 'ipetrov', '456789', '1999-01-08 04:05:06'),
(2, 'Алексей Кетов', 'aketov', '789fdfd', '1999-02-09 07:05:08'),
(3, 'Сергей Попов', 'spopov', 'dfs54588', '1999-11-10 05:05:06');

INSERT INTO requests (id, description, category, state, user_id) VALUES
(1, 'fix bug#1', 'low', 'new', 1),
(2, 'fix bug#2', 'normal', 'in process', 2),
(3, 'fix bug#3', 'high', 'closed', 3);

INSERT INTO files (name, path, extension, request_id) VALUES
('file 1', 'C:\\directory 1', 'txt', 1),
('file 2', 'C:\\directory 2', 'txt', 2),
('file 2', 'C:\\directory 2', 'xml', 2),
('file 4', 'C:\\directory 3', 'jpg', 3),
('file 5', 'C:\\directory 3', 'java', 3);

INSERT INTO comments (id, description, request_id) VALUES
(1, 'comment 1', 1),
(2, 'comment 2', 2),
(3, 'comment 3', 3);