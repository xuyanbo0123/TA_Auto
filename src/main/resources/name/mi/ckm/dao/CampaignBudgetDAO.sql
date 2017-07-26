-- #[INSERT_INTO_CAMPAIGN_BUDGET]:
INSERT INTO Campaign_Budget(id, created_ts, updated_ts, traffic_provider_id, provider_supplied_id, name, local_status, provider_status, period, delivery_method, amount, uploaded_ts, is_uploaded)
VALUES(
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
?,
?
);

-- #[GET_CAMPAIGN_BUDGET_BY_ID]:
SELECT id, created_ts, updated_ts, traffic_provider_id, provider_supplied_id, `name`, local_status, provider_status, period, delivery_method, amount, uploaded_ts, is_uploaded
FROM Campaign_Budget
WHERE id = ?;

-- #[GET_CAMPAIGN_BUDGET_BY_PROVIDER_SUPPLIED_ID]:
SELECT id, created_ts, updated_ts, traffic_provider_id, provider_supplied_id, `name`, local_status, provider_status, period, delivery_method, amount, uploaded_ts, is_uploaded
FROM Campaign_Budget
WHERE provider_supplied_id = ?;

-- #[GET_CAMPAIGN_BUDGET_BY_NAME]:
SELECT id, created_ts, updated_ts, traffic_provider_id, provider_supplied_id, `name`, local_status, provider_status, period, delivery_method, amount, uploaded_ts, is_uploaded
FROM Campaign_Budget
WHERE `name` = ?;

-- #[BATCH_INSERT_OR_UPDATE_CAMPAIGN_BUDGET_BY_PROVIDER_SUPPLIED_ID]:
INSERT INTO Campaign_Budget (id, created_ts, updated_ts, traffic_provider_id, provider_supplied_id, name, local_status, provider_status, period, delivery_method, amount, uploaded_ts, is_uploaded)
VALUES (null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
ON DUPLICATE KEY
UPDATE
`updated_ts` = CURRENT_TIMESTAMP,
`traffic_provider_id` = VALUES(`traffic_provider_id`),
`name` = VALUES(`name`),
`provider_status` = VALUES(`provider_status`),
`period` = VALUES(`period`),
`delivery_method` = VALUES(`delivery_method`),
`amount` = VALUES(`amount`),
`uploaded_ts` = VALUES(`uploaded_ts`),
`is_uploaded` = VALUES(`is_uploaded`);

-- #[GET_PENDING_SYNC_CAMPAIGN_BUDGETS]:
SELECT id, created_ts, updated_ts, traffic_provider_id, provider_supplied_id, `name`, local_status, provider_status, period, delivery_method, amount, uploaded_ts, is_uploaded
FROM Campaign_Budget
WHERE is_uploaded = false;

-- #[SET_IS_UPLOADED_FOR_ALL_CAMPAIGN_BUDGETS]:
UPDATE Campaign_Budget SET is_uploaded = ?;

-- #[UPDATE_CAMPAIGN_BUDGET_PROVIDER_SUPPLIED_ID_BY_ID]:
UPDATE Campaign_Budget
SET provider_supplied_id = ?, updated_ts = CURRENT_TIMESTAMP
WHERE id = ?;