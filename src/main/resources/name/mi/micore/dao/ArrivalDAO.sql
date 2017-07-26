-- #[INSERT_INTO_ARRIVAL]:
INSERT INTO Arrival(id, created_ts, updated_ts, uuid, ip_address, user_agent, referer, device, sid, subid, os, browser, gclid, conversion_count)
VALUES (
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
?,
?
);


-- #[GET_ARRIVALS_BY_TIME_INTERVAL_WITH_GCLID]:
SELECT id, created_ts, updated_ts, uuid, ip_address, user_agent, referer, device, sid, subid, os, browser, gclid, conversion_count
FROM Arrival
WHERE
  created_ts BETWEEN ? AND ?
AND
  LENGTH(gclid)>1;

-- #[GET_ARRIVAL_BY_ID]:
SELECT id, created_ts, updated_ts, uuid, ip_address, user_agent, referer, device, sid, subid, os, browser, gclid, conversion_count
FROM Arrival
WHERE id = ?;

-- #[GET_ARRIVAL_BY_UUID]:
SELECT id, created_ts, updated_ts, uuid, ip_address, user_agent, referer, device, sid, subid, os, browser, gclid, conversion_count
FROM Arrival
WHERE uuid = ?;

-- #[TOUCH_ARRIVAL_UPDATED_TS]:
UPDATE Arrival
SET updated_ts = CURRENT_TIMESTAMP()
WHERE id = ?;

-- #[GET_ARRIVAL_PROPERTY]:
SELECT `value`
FROM Arrival_Property
WHERE arrival_id = ? AND `name` = ?;

-- #[GET_ALL_ARRIVAL_PROPERTIES]:
SELECT `name`, `value`
FROM Arrival_Property
WHERE arrival_id = ?;

-- #[INSERT_OR_UPDATE_ARRIVAL_PROPERTY]:
INSERT INTO Arrival_Property
VALUES (null, ?, ?, ?)
ON DUPLICATE KEY
UPDATE `value`=VALUES(`value`);

-- #[VERIFY_ARRIVAL_ID_AND_PROPERTY_EXISTENCE]:
SELECT id
FROM Arrival_Property
WHERE arrival_id = ?
  AND `name` = ?
  AND `value` = ?;

-- #[INCREASE_CONVERSION_COUNT_BY_ID]:
UPDATE Arrival
SET `conversion_count` = `conversion_count` + 1
WHERE id = ?;



