-- #[INSERT_INTO_EMAIL_TEMPLATE]:
INSERT INTO Email_Template(id, created_ts, updated_ts, `name`, subject, content, status)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
?
);

-- #[GET_EMAIL_TEMPLATE_BY_ID]:
SELECT id, created_ts, updated_ts, `name`, subject, content, status
FROM Email_Template
WHERE id = ?;
