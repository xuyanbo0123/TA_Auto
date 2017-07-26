-- #[INSERT_INTO_MAIL_CHIMP_TEMPLATE]:
INSERT INTO Mail_Chimp_Template (id, created_ts, updated_ts, site_name, subject, content, from_address, from_name)
VALUES (
  NULL,
  CURRENT_TIMESTAMP(),
  CURRENT_TIMESTAMP(),
  ?,
  ?,
  ?,
  ?,
  ?
);

-- #[GET_MAIL_CHIMP_TEMPLATE_BY_SITE_NAME_AND_TYPE]:
SELECT
  id,
  created_ts,
  updated_ts,
  site_name,
  type,
  subject,
  content,
  from_address,
  from_name
FROM Mail_Chimp_Template
WHERE site_name = ? AND type = ?;