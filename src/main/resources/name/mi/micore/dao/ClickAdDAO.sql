-- #[INSERT_INTO_CLICK_AD]:
INSERT INTO Click_Ad(id, created_ts, updated_ts, click_impression_id, token, `position`, head_line, display_text, logo_link, click_link, display_link, company)
VALUES (
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
?,
?
);

-- #[GET_CLICK_AD_BY_ID]:
SELECT id, created_ts, updated_ts, click_impression_id, token, `position`, head_line, display_text, logo_link, click_link, display_link, company
FROM Click_Ad
WHERE id = ?;

