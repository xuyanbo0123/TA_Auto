-- #[INSERT_INTO_CLICK_IMPRESSION_REQUEST]:
INSERT INTO Click_Impression_Request(id, created_ts, updated_ts, arrival_id, location, lead_type_id, postal_state, zip_code)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
?,
?
);

-- #[GET_CLICK_IMPRESSION_REQUEST_BY_ID]:
SELECT id, created_ts, updated_ts, arrival_id, location, lead_type_id, postal_state, zip_code
FROM Click_Impression_Request
WHERE id = ?;


-- #[GET_CLICK_IMPRESSION_REQUEST_BY_ARRIVAL_ID]:
SELECT id, created_ts, updated_ts, arrival_id, location, lead_type_id, postal_state, zip_code
FROM Click_Impression_Request
WHERE arrival_id = ?;

