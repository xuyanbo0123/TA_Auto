-- #[INSERT_INTO_TRAFFIC_CAMPAIGN_SCHEDULE]:
INSERT INTO Traffic_Campaign_Schedule(id, created_ts, updated_ts, traffic_campaign_id, schedule_type, start_time, end_time, provider_supplied_id, uploaded_ts)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
?,
?,
?
);

-- #[GET_TRAFFIC_CAMPAIGN_SCHEDULE_BY_ID]:
SELECT id, created_ts, updated_ts, traffic_campaign_id, schedule_type, start_time, end_time, provider_supplied_id, uploaded_ts
FROM Traffic_Campaign_Schedule
WHERE id = ?;