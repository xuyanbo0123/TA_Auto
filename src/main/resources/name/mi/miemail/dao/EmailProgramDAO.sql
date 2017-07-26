-- #[INSERT_INTO_EMAIL_PROGRAM]:
INSERT INTO Email_Program(id, created_ts, updated_ts, name)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?
);

-- #[GET_EMAIL_PROGRAM_BY_ID]:
SELECT id, created_ts, updated_ts, name
FROM Email_Program
WHERE id = ?;

-- #[TOUCH_EMAIL_PROGRAM_UPDATED_TS]:
UPDATE Email_Program
SET updated_ts = CURRENT_TIMESTAMP()
WHERE id = ?;

-- #[INSERT_INTO_EMAIL_PROGRAM_PROPERTY]:
INSERT INTO Email_Program_Property
      (id, email_program_id, name, `value`)
VALUES(NULL,?,?,?);

-- #[GET_EMAIL_PROGRAM_PROPERTY]:
SELECT `value`
FROM Email_Program_Property
WHERE email_program_id = ?
  AND name = ?;

-- #[GET_ALL_EMAIL_PROGRAM_PROPERTIES]:
SELECT name, `value`
FROM Email_Program_Property
WHERE email_program_id = ?;

-- #[SET_EMAIL_PROGRAM_PROPERTY]:
INSERT INTO
Email_Program_Property
VALUES(NULL, ?, ?, ?)
ON DUPLICATE KEY UPDATE `value`=VALUES(`value`);