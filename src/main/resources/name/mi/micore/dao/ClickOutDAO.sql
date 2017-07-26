-- #[INSERT_INTO_CLICK_OUT]:
INSERT INTO Click_Out(id, created_ts, updated_ts, click_impression_id, amount, adjusted_amount)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?
);

-- #[INSERT_INTO_ON_DUPLICATE_UPDATE_CLICK_OUT]:
INSERT INTO
Click_Out(id, created_ts, updated_ts, click_impression_id, amount, adjusted_amount)
VALUES (NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?)
ON DUPLICATE KEY
UPDATE
  `updated_ts` = CURRENT_TIMESTAMP,
  `amount` = VALUES(`amount`),
  `adjusted_amount` = VALUES(`adjusted_amount`);

-- #[GET_CLICK_OUT_BY_ID]:
SELECT id, created_ts, updated_ts, click_impression_id, amount, adjusted_amount
FROM Click_Out
WHERE id = ?;


-- #[GET_CLICK_OUT_BY_IMPRESSION_ID]:
SELECT id, created_ts, updated_ts, click_impression_id, amount, adjusted_amount
FROM Click_Out
WHERE click_impression_id = ?;


