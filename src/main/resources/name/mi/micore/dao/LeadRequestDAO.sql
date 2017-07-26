-- #[INSERT_INTO_LEAD_REQUEST]:
INSERT INTO Lead_Request(id, created_ts, updated_ts, raw_request, request_status, arrival_id, lead_type_id, token, lead_id)
VALUES (
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

-- #[GET_LEAD_REQUEST_BY_ID]:
SELECT id, created_ts, updated_ts, raw_request, request_status, arrival_id, lead_type_id, token, lead_id
FROM Lead_Request
WHERE id = ?;


-- #[GET_LEAD_REQUEST_BY_ARRIVAL_ID]:
SELECT id, created_ts, updated_ts, raw_request, request_status, arrival_id, lead_type_id, token, lead_id
FROM Lead_Request
WHERE arrival_id = ?;


-- #[GET_LEAD_REQUEST_BY_TOKEN]:
SELECT id, created_ts, updated_ts, raw_request, request_status, arrival_id, lead_type_id, token, lead_id
FROM Lead_Request
WHERE token = ?;

-- #[UPDATE_LEAD_REQUEST_BY_ID]:
UPDATE Lead_Request
SET request_status = ?, updated_ts = CURRENT_TIMESTAMP
WHERE id = ?;

-- #[GET_PENDING_SELL_LEAD_REQUESTS_BY_STATUS]:
SELECT id, created_ts, updated_ts, raw_request, request_status, arrival_id, lead_type_id, token, lead_id
FROM Lead_Request
WHERE request_status = ?;

-- #[BATCH_UPDATE_REQUEST_STATUS]:
UPDATE Lead_Request
SET request_status = ?, updated_ts = CURRENT_TIMESTAMP
WHERE request_status = ?;