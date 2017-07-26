-- #[INSERT_INTO_MAIL_CHIMP_INFORMATION]:
INSERT INTO Mail_Chimp_Information (id, created_ts, updated_ts, email_address, email_domain, first_name, last_name, lead_request_id, token)
VALUES (
  NULL,
  CURRENT_TIMESTAMP(),
  CURRENT_TIMESTAMP(),
  ?,
  ?,
  ?,
  ?,
  ?,
  ?
);

-- #[GET_MAIL_CHIMP_INFORMATION_BY_DATE_INTERVAL]:
SELECT
  id,
  created_ts,
  updated_ts,
  email_address,
  email_domain,
  first_name,
  last_name,
  lead_request_id,
  token
FROM Mail_Chimp_Information
WHERE created_ts BETWEEN ? AND ?;
