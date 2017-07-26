-- #[INSERT_INTO_EMAIL_RECORD_ROLE_MAP]:
INSERT INTO Email_Record_Role_Map(id, created_ts, updated_ts, email_record_id, email_role_id, status)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?
);

-- #[GET_EMAIL_RECORD_ROLE_MAP_BY_ID]:
SELECT id, created_ts, updated_ts, email_record_id, email_role_id, status
FROM Email_Record_Role_Map
WHERE id = ?;