-- #[GET_LEAD_SELL_BY_LEAD_REQUEST_ID_AND_BUYER_ACCOUNT_ID]:
SELECT id, created_ts, updated_ts, lead_request_id, buyer_account_id, sell_state
FROM Lead_Sell
WHERE
  lead_request_id = ?
AND
  buyer_account_id = ?;

-- #[GET_LEAD_SELL_LIST_WITHOUT_APPENDIX_BY_LEAD_REQUEST_ID]:
SELECT id, created_ts, updated_ts, lead_request_id, buyer_account_id, sell_state
FROM Lead_Sell
WHERE lead_request_id = ?;

-- #[INSERT_OR_UPDATE_LEAD_SELL_BY_LEAD_REQUEST_ID_AND_BUYER_ACCOUNT_ID]:
INSERT INTO
Lead_Sell(id, created_ts, updated_ts, lead_request_id, buyer_account_id, sell_state)
VALUES (null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?)
ON DUPLICATE KEY
UPDATE
  `updated_ts` = CURRENT_TIMESTAMP,
  `sell_state` = VALUES(`sell_state`);

-- #[GET_LEAD_SELL_APPENDIX_BY_ID]:
SELECT id, comment, post_url, post_entity
FROM Lead_Sell_Appendix
WHERE id = ?;

-- #[GET_LEAD_SELL_ID_BY_REQUEST_ID_AND_BUYER_ACCOUNT_ID]:
SELECT id
FROM Lead_Sell
WHERE lead_request_id = ? AND buyer_account_id = ?;

-- #[INSERT_OR_UPDATE_LEAD_SELL_APPENDIX_BY_ID]:
INSERT INTO
  Lead_Sell_Appendix(id, comment, post_url, post_entity)
  VALUES (?, ?, ?, ?)
ON DUPLICATE KEY
UPDATE
  `comment` = VALUES(`comment`),
  `post_url` = VALUES(`post_url`),
  `post_entity` = VALUES(`post_entity`);