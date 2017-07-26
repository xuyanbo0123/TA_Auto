-- #[INSERT_INTO_TRAFFIC_SOURCE]:
INSERT INTO Traffic_Source(id, created_ts, updated_ts, traffic_provider_id, traffic_type, `name`)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?
);

-- #[GET_TRAFFIC_SOURCE_BY_ID]:
SELECT id, created_ts, updated_ts, traffic_provider_id, traffic_type, `name`
FROM Traffic_Source
WHERE id = ?;