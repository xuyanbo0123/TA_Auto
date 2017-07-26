-- #[INSERT_INTO_EMAIL_TEMPLATE_LINK]:
INSERT INTO Email_Template_Link
      (id, created_ts, updated_ts, email_template_id, link_name, link_text, link_url)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
?
);

-- #[GET_EMAIL_TEMPLATE_LINK_BY_ID]:
SELECT id, created_ts, updated_ts, email_template_id, link_name, link_text, link_url
FROM Email_Template_Link
WHERE id = ?;
