-- #[INSERT_INTO_EMAIL_RULE]:
INSERT INTO Email_Rule(id, created_ts, updated_ts, name, `value`)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?
);

-- #[GET_EMAIL_RULE_BY_ID]:
SELECT id, created_ts, updated_ts, name, `value`
FROM Email_Rule
WHERE id = ?;