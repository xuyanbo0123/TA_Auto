-- #[INSERT_INTO_EMAIL_FROM]:
INSERT INTO Email_From(id, created_ts, updated_ts, `name`, `from_address`, `from_text`)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?
);

-- #[GET_EMAIL_FROM_BY_ID]:
SELECT id, created_ts, updated_ts, `name`, `from_address`, `from_text`
FROM Email_From
WHERE id = ?;
