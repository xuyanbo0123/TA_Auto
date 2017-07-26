-- #[INSERT_INTO_EMAIL_PROGRAM_STEP]:
INSERT INTO Email_Program_Step(id, created_ts, updated_ts, email_program_id, step_name, email_template_id, email_from_id)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
?
);

-- #[GET_EMAIL_PROGRAM_STEP_BY_ID]:
SELECT id, created_ts, updated_ts, email_program_id, step_name, email_template_id, email_from_id
FROM Email_Program_Step
WHERE id = ?;

