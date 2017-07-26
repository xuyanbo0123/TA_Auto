-- #[INSERT_INTO_LEAD_TYPE]:
INSERT INTO Lead_Type(id, created_ts, updated_ts, type_name, description)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?
);

-- #[GET_LEAD_TYPE_BY_ID]:
SELECT id, created_ts, updated_ts, type_name, description
FROM Lead_Type
WHERE id = ?;

-- #[GET_LEAD_TYPE_MAP]:
SELECT id, type_name
FROM Lead_Type;