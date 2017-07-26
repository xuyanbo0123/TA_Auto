-- #[INSERT_INTO_ARRIVAL_TRACKING]:
INSERT INTO Arrival_Tracking(id, created_ts, arrival_id, web_page_id, `action`, web_page_url)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
?,
?,
?,
?
);


-- #[GET_ARRIVAL_TRACKING_LIST_BY_ARRIVAL_ID]:
SELECT id, created_ts, arrival_id, web_page_id, `action`, web_page_url
FROM Arrival_Tracking
WHERE arrival_id = ?;

-- #[GET_ARRIVAL_TRACKING_BY_ID]:
SELECT id, created_ts, arrival_id, web_page_id, `action`, web_page_url
FROM Arrival_Tracking
WHERE id = ?;