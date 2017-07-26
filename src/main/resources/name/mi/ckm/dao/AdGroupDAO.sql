-- #[INSERT_INTO_AD_GROUP]:
INSERT INTO Ad_Group(id, created_ts, updated_ts, traffic_campaign_id, name, local_status, provider_status, provider_supplied_id, uploaded_ts, is_uploaded)
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
?
);

-- #[GET_AD_GROUPS_BY_CAMPAIGN_ID]:
SELECT id, created_ts, updated_ts, traffic_campaign_id, name, local_status, provider_status, provider_supplied_id, uploaded_ts, is_uploaded
FROM Ad_Group
WHERE traffic_campaign_id = ?;

-- #[GET_AD_GROUP_BY_ID]:
SELECT id, created_ts, updated_ts, traffic_campaign_id, name, local_status, provider_status, provider_supplied_id, uploaded_ts, is_uploaded
FROM Ad_Group
WHERE id = ?;

-- #[GET_AD_GROUP_BY_PROVIDER_SUPPLIED_ID]:
SELECT id, created_ts, updated_ts, traffic_campaign_id, name, local_status, provider_status, provider_supplied_id, uploaded_ts, is_uploaded
FROM Ad_Group
WHERE provider_supplied_id = ?;

-- #[GET_AD_GROUP_BY_CAMPAIGN_ID_NAME]:
SELECT id, created_ts, updated_ts, traffic_campaign_id, name, local_status, provider_status, provider_supplied_id, uploaded_ts, is_uploaded
FROM Ad_Group
WHERE
traffic_campaign_id = ?
AND
name = ?
;

-- #[UPDATE_AD_GROUP_LOCAL_STATUS_BY_ID]:
UPDATE
      Ad_Group
SET
      local_status = ?,
      updated_ts = CURRENT_TIMESTAMP
WHERE
      id = ?
AND
      local_status != 'deleted';

-- #[GET_PENDING_SYNC_AD_GROUPS]:
SELECT id, created_ts, updated_ts, traffic_campaign_id, name, local_status, provider_status, provider_supplied_id, uploaded_ts, is_uploaded
FROM Ad_Group
WHERE is_uploaded = false ;

-- #[BATCH_INSERT_OR_UPDATE_AD_GROUP_BY_PROVIDER_SUPPLIED_ID]:
INSERT INTO Ad_Group (id, created_ts, updated_ts, traffic_campaign_id, name, local_status, provider_status, provider_supplied_id, uploaded_ts, is_uploaded)
VALUES
(
null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?)
ON DUPLICATE KEY
UPDATE
`updated_ts` = CURRENT_TIMESTAMP,
`traffic_campaign_id` = VALUES(`traffic_campaign_id`),
`name` = VALUES(`name`),
`provider_status` = VALUES(`provider_status`),
`uploaded_ts` = VALUES(`uploaded_ts`),
`is_uploaded` = VALUES(`is_uploaded`);

-- #[SET_IS_UPLOADED_FOR_ALL_AD_GROUPS]:
UPDATE Ad_Group SET is_uploaded = ?;

-- #[UPDATE_AD_GROUP_PROVIDER_SUPPLIED_ID_BY_ID]:
UPDATE Ad_Group
SET provider_supplied_id = ?, updated_ts = CURRENT_TIMESTAMP
WHERE id = ?;