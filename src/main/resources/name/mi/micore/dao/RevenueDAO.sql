-- #[INSERT_INTO_REVENUE]:
INSERT INTO Revenue(id, created_ts, updated_ts, type, source, arrival_uuid, token, amount, transaction_id)
VALUES (NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?);
-- #[GET_REVENUE_LIST]:
SELECT id, created_ts, updated_ts, type, source, arrival_uuid, token, amount, transaction_id
FROM Revenue;
-- #[GET_REVENUE_BY_ID]:
SELECT id, created_ts, updated_ts, type, source, arrival_uuid, token, amount, transaction_id
FROM Revenue
WHERE id = ?;
-- #[GET_REVENUE_LIST_BY_TYPE_AND_SOURCE]:
SELECT id, created_ts, updated_ts, type, source, arrival_uuid, token, amount, transaction_id
FROM Revenue
WHERE type = ? AND source = ?;
-- #[GET_DAILY_REVENUE_BY_TYPE_SOURCE_AND_DATE]:
SELECT COUNT(id) AS count, SUM(amount) AS sum
FROM Revenue
WHERE type = ? AND source = ? AND DATE(created_ts) BETWEEN DATE(?) AND DATE(?);