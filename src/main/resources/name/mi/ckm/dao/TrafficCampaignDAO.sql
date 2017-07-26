-- #[INSERT_INTO_TRAFFIC_CAMPAIGN]:
INSERT INTO Traffic_Campaign(id, created_ts, updated_ts, `name`, sid, provider_supplied_id, local_status, provider_status, uploaded_ts, `is_uploaded`, campaign_budget_id)
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
?
);

-- #[GET_TRAFFIC_CAMPAIGN_BY_ID]:
SELECT id, created_ts, updated_ts, `name`, sid, provider_supplied_id, local_status, provider_status, uploaded_ts, is_uploaded, campaign_budget_id
FROM Traffic_Campaign
WHERE id = ?;

-- #[GET_TRAFFIC_CAMPAIGN_BY_NAME]:
SELECT id, created_ts, updated_ts, `name`, sid, provider_supplied_id, local_status, provider_status, uploaded_ts, is_uploaded, campaign_budget_id
FROM Traffic_Campaign
WHERE `name` = ?;

-- #[GET_TRAFFIC_CAMPAIGN_BY_PROVIDER_SUPPLIED_ID]:
SELECT id, created_ts, updated_ts, `name`, sid, provider_supplied_id, local_status, provider_status, uploaded_ts, is_uploaded, campaign_budget_id
FROM Traffic_Campaign
WHERE provider_supplied_id = ?;

-- #[UPDATE_TRAFFIC_CAMPAIGN_LOCAL_STATUS_BY_ID]:
UPDATE Traffic_Campaign
SET
  `local_status` = ?,
  updated_ts = CURRENT_TIMESTAMP
WHERE
  id = ?
AND
  `local_status` != 'deleted';

-- #[GET_PENDING_SYNC_TRAFFIC_CAMPAIGNS]:
SELECT id, created_ts, updated_ts, `name`, sid, provider_supplied_id, local_status, provider_status, uploaded_ts, is_uploaded, campaign_budget_id
FROM Traffic_Campaign
WHERE is_uploaded = false;

-- #[BATCH_INSERT_OR_UPDATE_TRAFFIC_CAMPAIGN_BY_PROVIDER_SUPPLIED_ID]:
INSERT INTO Traffic_Campaign (id, created_ts, updated_ts, name, sid, provider_supplied_id, local_status, provider_status, uploaded_ts, is_uploaded, campaign_budget_id)
VALUES
(
NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?
)
ON DUPLICATE KEY
UPDATE
`updated_ts` = CURRENT_TIMESTAMP,
`name` = VALUES(`name`),
`sid` = VALUES(`sid`),
`provider_status` = VALUES(`provider_status`),
`uploaded_ts` = VALUES(`uploaded_ts`),
`is_uploaded` = VALUES(`is_uploaded`),
`campaign_budget_id` = VALUES(`campaign_budget_id`);

-- #[SET_IS_UPLOADED_FOR_ALL_TRAFFIC_CAMPAIGNS]:
UPDATE Traffic_Campaign SET is_uploaded = ?;

-- #[UPDATE_TRAFFIC_CAMPAIGN_PROVIDER_SUPPLIED_ID_BY_ID]:
UPDATE Traffic_Campaign
SET provider_supplied_id = ?, updated_ts = CURRENT_TIMESTAMP
WHERE id = ?;