CREATE TABLE IF NOT EXISTS job_vacancies
(
    vacancy_message VARCHAR(200),
	vacancy_desc_ref VARCHAR(300),
	author VARCHAR(50) REFERENCES authors(nick_name),
	date TIMESTAMP,
    PRIMARY KEY (author, vacancy_message)
);