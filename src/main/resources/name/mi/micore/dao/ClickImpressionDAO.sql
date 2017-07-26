-- #[INSERT_OR_UPDATE_CLICK_IMPRESSION_BY_CLICK_IMPRESSION_REQUEST_ID_AND_BUYER_ACCOUNT_ID]:
INSERT INTO
  Click_Impression(id, created_ts, updated_ts, click_impression_request_id, buyer_account_id, token)
  VALUES (NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?)
ON DUPLICATE KEY
UPDATE
  `updated_ts` = CURRENT_TIMESTAMP;

-- #[INSERT_OR_UPDATE_CLICK_IMPRESSION_APPENDIX_BY_ID]:
INSERT INTO
  Click_Impression_Appendix(id, comment, post_url, post_entity)
  VALUES (?, ?, ?, ?)
ON DUPLICATE KEY
UPDATE
  `comment` = VALUES(`comment`),
  `post_url` = VALUES(`post_url`),
  `post_entity` = VALUES(`post_entity`);

-- #[GET_CLICK_IMPRESSION_BY_TOKEN]:
SELECT id, created_ts, updated_ts, click_impression_request_id, buyer_account_id, token
FROM Click_Impression
WHERE token = ?;

-- #[GET_CLICK_IMPRESSION_APPENDIX_BY_ID]:
SELECT id, comment, post_url, post_entity
FROM Click_Impression_Appendix
WHERE id = ?;

-- #[GET_CLICK_IMPRESSION_BY_REQUEST_ID]:
SELECT id, created_ts, updated_ts, click_impression_request_id, buyer_account_id, token
FROM Click_Impression
WHERE click_impression_request_id = ?;

-- #[GET_CLICK_IMPRESSION_ID_BY_REQUEST_ID_AND_BUYER_ACCOUNT_ID]:
SELECT id
FROM Click_Impression
WHERE click_impression_request_id = ? AND buyer_account_id = ?;






