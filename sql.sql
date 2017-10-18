CREATE DATABASE request_tracker;

--connect to request_tracker
\c request_tracker
 

CREATE TABLE privileges (
    name character varying(30),
    description text,
    PRIMARY KEY (name)
);

INSERT INTO privileges(name, description) VALUES 
('insert', 'description of insert'),
('delete', 'description of delete'),
('update', 'description of update'),
('create', 'description of create'),
('drop', 'description of drop'),
('process request', 'description of process request');


CREATE TABLE roles (
    name character varying(30),
    description text,
    PRIMARY KEY (name)
);

INSERT INTO roles(name, description) VALUES 
('admin', 'description of admin'),
('manager', 'description of manager'),
('employee', 'description of employee');

--ассоциативная таблица для реализации связи многие-ко-многим между ролями и привилегиями
CREATE TABLE roles_privileges (
    role_name character varying(30) REFERENCES roles(name),
    priv_name character varying(30) REFERENCES privileges(name),
    PRIMARY KEY (role_name, priv_name)
);


INSERT INTO roles_privileges(role_name, priv_name) VALUES 
('admin', 'insert'),
('admin', 'create'),
('admin', 'drop'),
('admin', 'update'),
('manager', 'insert'),
('manager', 'update'),
('employee', 'process request');

CREATE TABLE users (
    id  integer PRIMARY KEY,
    name character varying(30),
	role character varying(30) REFERENCES roles(name),
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

INSERT INTO users (id, name, role, login, password, create_date) VALUES
(1, 'Иван Петров', 'admin', 'ipetrov', '456789', '1999-01-08 04:05:06'),
(2, 'Алексей Кетов', 'employee', 'aketov', '789fdfd', '1999-02-09 07:05:08'),
(3, 'Сергей Попов', 'manager', 'spopov', 'dfs54588', '1999-11-10 05:05:06');

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