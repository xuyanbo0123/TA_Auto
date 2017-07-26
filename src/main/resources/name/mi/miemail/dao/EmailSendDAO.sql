-- #[INSERT_INTO_EMAIL_SEND]:
INSERT INTO Email_Send
      (id, created_ts, updated_ts, email_record_id, email_role_id, email_program_step_id, email_template_id, email_from_id, queue_ts, sent_ts, opened_ts)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
?,
?,
?,
?,
?
);

-- #[GET_EMAIL_SEND_BY_ID]:
SELECT id, created_ts, updated_ts, email_record_id, email_role_id, email_program_step_id, email_template_id, email_from_id, queue_ts, sent_ts, opened_ts
FROM Email_Send
WHERE id = ?;

-- #[GET_ALL_EMAIL_SEND]:
SELECT id, created_ts, updated_ts, email_record_id, email_role_id, email_program_step_id, email_template_id, email_from_id, queue_ts, sent_ts, opened_ts
FROM Email_Send;

-- #[GET_UNSENT_EMAIL_SEND]:
SELECT id, created_ts, updated_ts, email_record_id, email_role_id, email_program_step_id, email_template_id, email_from_id, queue_ts, sent_ts, opened_ts
FROM Email_Send
WHERE queue_ts = 0;

-- #[UPDATE_EMAIL_SEND_SENT_TS]:
UPDATE Email_Send
SET sent_ts = CURRENT_TIMESTAMP()
WHERE id = ?;

-- #[UPDATE_EMAIL_SEND_QUEUE_TS]:
UPDATE Email_Send
SET queue_ts = CURRENT_TIMESTAMP()
WHERE id = ?;