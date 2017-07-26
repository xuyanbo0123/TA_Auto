-- #[INSERT_INTO_REDIRECT]:
INSERT INTO Redirect(id, created_ts, updated_ts, `action`, `token`, `click_ad_position`, destination_url)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
?
);

-- #[GET_REDIRECT_BY_ID]:
SELECT id, created_ts, updated_ts, `action`, `token`, `click_ad_position`, destination_url
FROM Redirect
WHERE id = ?;

-- #[GET_REDIRECT_BY_ACTION_AND_TOKEN_AND_CLICK_AD_POSITION]:
SELECT id, created_ts, updated_ts, `action`, `token`, `click_ad_position`, destination_url
FROM Redirect
WHERE
 `action` = ?
AND
 `token` = ?
AND
 `click_ad_position` = ?;