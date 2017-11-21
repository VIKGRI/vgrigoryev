
-- table of items
CREATE TABLE items (
             id INTEGER PRIMARY KEY,
			 name VARCHAR(30)
			 );
-- Deletes all duplicates in name column
	 DELETE from items
	 WHERE id NOT IN (
	                   SELECT min(id)
					   FROM items
					   GROUP BY name);
