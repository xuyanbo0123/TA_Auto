-- #[INSERT_INTO_TRAFFIC_PROVIDER]:
INSERT INTO Traffic_Provider(id, created_ts, updated_ts, name)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?
);

-- #[GET_TRAFFIC_PROVIDER_BY_ID]:
SELECT id, created_ts, updated_ts, `name`
FROM Traffic_Provider
WHERE id = ?;

-- #[GET_ALL_TRAFFIC_PROVIDERS]:
SELECT id, created_ts, updated_ts, `name`
FROM Traffic_Provider


-- #[GET_TRAFFIC_PROVIDER_BY_NAME]:
SELECT id, created_ts, updated_ts, `name`
FROM Traffic_Provider
WHERE `name` = ?;


