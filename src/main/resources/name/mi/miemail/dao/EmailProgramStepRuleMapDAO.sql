-- #[INSERT_INTO_EMAIL_PROGRAM_STEP_RULE_MAP]:
INSERT INTO Email_Program_Step_Rule_Map
      (id, created_ts, updated_ts, email_program_step_id, email_rule_id, status)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?
);

-- #[GET_EMAIL_PROGRAM_STEP_RULE_MAP_BY_ID]:
SELECT id, created_ts, updated_ts, email_program_step_id, email_rule_id, status
FROM Email_Program_Step_Rule_Map
WHERE id = ?;