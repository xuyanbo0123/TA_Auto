-- #[INSERT_OR_UPDATE_EMAIL_RECORD_UNIQUE]:
INSERT INTO Email_Record_Unique(id, created_ts, updated_ts, email_address, email_domain, status)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?
)
ON DUPLICATE KEY
UPDATE `updated_ts`=CURRENT_TIMESTAMP(), `status`=VALUES(`status`);

-- #[GET_EMAIL_RECORD_UNIQUE_BY_ID]:
SELECT id, created_ts, updated_ts, email_address, email_domain, status
FROM Email_Record_Unique
WHERE id = ?;