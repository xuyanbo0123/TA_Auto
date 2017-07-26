-- #[BATCH_INSERT_OR_UPDATE_EMERGENCY_CAMPAIGN_RECORD_BY_SYSTEM_EMERGENCY_STATUS_ID_AND_PROVIDER_SUPPLIED_ID]:
INSERT INTO Emergency_Campaign_Record (id, created_ts, updated_ts, system_emergency_status_id, provider_supplied_id, provider_status_before_paused, provider_status, uploaded_ts, is_uploaded)
VALUES
(
NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?
)
ON DUPLICATE KEY
UPDATE
`updated_ts` = CURRENT_TIMESTAMP,
`provider_status` = VALUES(`provider_status`),
`uploaded_ts` = VALUES(`uploaded_ts`),
`is_uploaded` = VALUES(`is_uploaded`);

-- #[GET_PENDING_SYNC_EMERGENCY_CAMPAIGN_RECORDS]:
SELECT id, created_ts, updated_ts, system_emergency_status_id, provider_supplied_id, provider_status_before_paused, provider_status, uploaded_ts, is_uploaded
FROM Emergency_Campaign_Record
WHERE system_emergency_status_id = (SELECT system_emergency_status_id FROM Emergency_Campaign_Record ORDER BY system_emergency_status_id DESC LIMIT 1);