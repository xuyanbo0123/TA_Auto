-- #[INSERT_INTO_EMAIL_ROLE]:
INSERT INTO Email_Role(id, created_ts, updated_ts, `name`, email_program_id, comments)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?
);

-- #[GET_EMAIL_ROLE_BY_ID]:
SELECT id, created_ts, updated_ts, name, email_program_id, comments
FROM Email_Role
WHERE id = ?;

-- #[TOUCH_EMAIL_ROLE_UPDATED_TS]:
UPDATE Email_Role
SET updated_ts = CURRENT_TIMESTAMP()
WHERE id = ?;

-- #[INSERT_INTO_EMAIL_ROLE_PROPERTY]:
INSERT INTO Email_Role_Property
      (id, email_role_id, name, `value`)
VALUES(NULL,?,?,?);


-- #[GET_EMAIL_ROLE_PROPERTY]:
SELECT `value`
FROM Email_Role_Property
WHERE email_role_id = ?
  AND `name` = ?;

-- #[GET_ALL_EMAIL_ROLE_PROPERTIES]:
SELECT `name`, `value`
FROM Email_Role_Property
WHERE email_role_id = ?;

-- #[SET_EMAIL_ROLE_PROPERTY]:
INSERT INTO
Email_Role_Property
VALUES(NULL, ?, ?, ?)
ON DUPLICATE KEY UPDATE `value`=VALUES(`value`);