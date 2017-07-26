-- #[INSERT_INTO_BUYER]:
INSERT INTO Buyer(id, created_ts, updated_ts, buyer_type, name, contact_info)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?
);

-- #[GET_BUYER_BY_ID]:
SELECT id, created_ts, updated_ts, buyer_type, name, contact_info
FROM Buyer
WHERE id = ?;

