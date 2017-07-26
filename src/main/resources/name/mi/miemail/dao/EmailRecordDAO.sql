-- #[INSERT_INTO_EMAIL_RECORD]:
INSERT INTO Email_Record(id, created_ts, updated_ts, email_address, email_domain, status, arrival_id, user_id)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
?,
?
);

-- #[GET_EMAIL_RECORD_BY_ID]:
SELECT id, created_ts, updated_ts, email_address, email_domain, status, arrival_id, user_id
FROM Email_Record
WHERE id = ?;

-- #[TOUCH_EMAIL_RECORD_UPDATED_TS]:
UPDATE Email_Record
SET updated_ts = CURRENT_TIMESTAMP()
WHERE id = ?;

-- #[INSERT_INTO_EMAIL_RECORD_PROPERTY]:
INSERT INTO Email_Record_Property(id, email_record_id, name, `value`)
VALUES(NULL,?,?,?);

-- #[GET_EMAIL_RECORD_PROPERTY]:
SELECT `value`
FROM Email_Record_Property
WHERE email_record_id = ?
  AND `name` = ?;

-- #[GET_ALL_EMAIL_RECORD_PROPERTIES]:
SELECT `name`, `value`
FROM Email_Record_Property
WHERE email_record_id = ?;

-- #[SET_EMAIL_RECORD_PROPERTY]:
INSERT INTO
Email_Record_Property
VALUES(NULL, ?, ?, ?)
ON DUPLICATE KEY UPDATE `value`=VALUES(`value`);

-- #[GET_ALL_ACTIVE_EMAIL_RECORD]:
SELECT id, created_ts, updated_ts, email_address, email_domain, status, arrival_id, user_id
FROM Email_Record
WHERE `status` = 'active';

-- #[UPDATE_EMAIL_RECORD_BY_EMAIL_ADDRESS_AND_STATUS]:
UPDATE Email_Record
SET updated_ts = CURRENT_TIMESTAMP(), `status` = ?
WHERE email_address = ?
AND `status` = ?;

