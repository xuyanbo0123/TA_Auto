-- #[INSERT_INTO_LEAD_REVENUE]:
INSERT INTO Lead_Revenue(id, created_ts, updated_ts, lead_request_id, amount, adjusted_amount, comment)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
?
);

-- #[INSERT_INTO_ON_DUPLICATE_UPDATE_LEAD_REVENUE]:
INSERT INTO
Lead_Revenue(id, created_ts, updated_ts, lead_request_id, amount, adjusted_amount, comment)
VALUES (NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?)
ON DUPLICATE KEY
UPDATE
  `updated_ts` = CURRENT_TIMESTAMP,
  `amount` = VALUES(`amount`),
  `adjusted_amount` = VALUES(`adjusted_amount`),
  `comment` = VALUES(`comment`);

-- #[GET_LEAD_REVENUE_BY_ID]:
SELECT id, created_ts, updated_ts, lead_request_id, amount, adjusted_amount, comment
FROM Lead_Revenue
WHERE id = ?;

-- #[GET_LEAD_REVENUE_BY_LEAD_REQUEST_ID]:
SELECT id, created_ts, updated_ts, lead_request_id, amount, adjusted_amount, comment
FROM Lead_Revenue
WHERE lead_request_id = ?;