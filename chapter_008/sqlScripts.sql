
-- 1)
SELECT p.name, c.name
FROM person AS p
LEFT OUTER JOIN company AS c
ON p.company_id = c.id
WHERE p.company_id IS NULL OR p.company_id <> 5;

--2)
WITH company_persons AS (
SELECT c.name, COUNT(p.id) AS person_number
FROM company AS c
JOIN person AS p
ON c.id = p.company_id
GROUP BY c.name)
SELECT name, person_number
FROM company_persons
WHERE person_number = (SELECT MAX(person_number) FROM company_persons);