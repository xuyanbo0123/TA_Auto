-- #[INSERT_INTO_TRAFFIC_CAMPAIGN_GEO_TARGET]:
INSERT INTO Traffic_Campaign_Geo_Target
      (id, created_ts, updated_ts, target_type, traffic_campaign_id, criteria_id, local_status, provider_status, targeting_status, uploaded_ts, is_uploaded)
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

-- #[GET_PENDING_SYNC_TRAFFIC_CAMPAIGN_GEO_TARGETS]:
SELECT id, created_ts, updated_ts, target_type, traffic_campaign_id, criteria_id, local_status, provider_status, targeting_status, uploaded_ts, is_uploaded
FROM Traffic_Campaign_Geo_Target
WHERE is_uploaded = false AND (local_status != 'remove' OR provider_status != 'remove');

-- #[BATCH_INSERT_OR_UPDATE_TRAFFIC_CAMPAIGN_GEO_TARGET_BY_TRAFFIC_CAMPAIGN_ID_AND_CRITERIA_ID]:
INSERT INTO Traffic_Campaign_Geo_Target (id, created_ts, updated_ts, target_type, traffic_campaign_id, criteria_id, local_status, provider_status, targeting_status, uploaded_ts, is_uploaded)
VALUES (NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?)
ON DUPLICATE KEY
UPDATE
`updated_ts` = CURRENT_TIMESTAMP,
`target_type` = VALUES(`target_type`),
`local_status` = VALUES(`local_status`),
`provider_status` = VALUES(`provider_status`),
`targeting_status` = VALUES(`targeting_status`),
`uploaded_ts` = VALUES(`uploaded_ts`),
`is_uploaded` = VALUES(`is_uploaded`);

-- #[SET_IS_UPLOADED_FOR_ALL_TRAFFIC_CAMPAIGN_GEO_TARGETS]:
UPDATE Traffic_Campaign_Geo_Target SET is_uploaded = ?;

-- #[GET_POSITIVE_TRAFFIC_CAMPAIGN_GEO_TARGET_BY_TRAFFIC_CAMPAIGN_ID]:
SELECT id, created_ts, updated_ts, target_type, traffic_campaign_id, criteria_id, local_status, provider_status, targeting_status, uploaded_ts, is_uploaded
FROM Traffic_Campaign_Geo_Target
WHERE traffic_campaign_id = ? AND target_type = 'positive';