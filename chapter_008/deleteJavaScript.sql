DELETE FROM job_vacancies 
WHERE vacancy_message IN 
(SELECT vacancy_message AS js_vacancies
    FROM job_vacancies 
    WHERE LOWER(vacancy_message) LIKE('%javascript%') OR LOWER(vacancy_message) LIKE('%java script%')
    EXCEPT
    SELECT vacancy_message 
    FROM job_vacancies
    WHERE LOWER(vacancy_message) LIKE('%java%javascript%') OR LOWER(vacancy_message) LIKE('%java%java script%'));