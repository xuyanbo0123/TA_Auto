-- #[INSERT_INTO_EMAIL_SEND_LINK_TRACKING]:
INSERT INTO Email_Send_Link_Tracking
      (id, created_ts, updated_ts, email_send_id, email_template_link_id, clicked_ts)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?
);

-- #[GET_EMAIL_SEND_LINK_TRACKING_BY_ID]:
SELECT id, created_ts, updated_ts, email_send_id, email_template_link_id, clicked_ts
FROM Email_Send_Link_Tracking
WHERE id = ?;

