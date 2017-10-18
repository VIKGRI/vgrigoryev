--connect to request_tracker
\c request_tracker

SELECT r.id, r.description, r.user_id
FROM requests as r
WHERE r.description LIKE '%fix%'
AND r.state = 'closed';

SELECT r.id, r.category
FROM requests as r
WHERE r.user_id IN(1, 2)
AND r.state <> 'in process';

SELECT r.id, r.category, r.state, r.user_id
FROM requests as r
WHERE r.user_id BETWEEN 2 AND 3
AND r.state IN ('in process');