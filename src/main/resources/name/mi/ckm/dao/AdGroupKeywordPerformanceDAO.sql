-- #[INSERT_INTO_AD_GROUP_KEYWORD_PERFORMANCE]:
INSERT INTO Ad_Group_Keyword_Performance(id, created_ts, updated_ts, ad_group_keyword_id, start_at, end_at, impression_count, click_count, total_spending, avg_position, quality_score)
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
?
);

-- #[GET_AD_GROUP_KEYWORD_PERFORMANCE_BY_ID]:
SELECT id, created_ts, updated_ts, ad_group_keyword_id, start_at, end_at, impression_count, click_count, total_spending, avg_position, quality_score FROM Ad_Group_Keyword_Performance WHERE id = ?;

-- #[GET_AD_GROUP_KEYWORD_PERFORMANCE_ID_BY_AD_GROUP_KEYWORD_ID_AND_DATES]:
SELECT id FROM Ad_Group_Keyword_Performance WHERE ad_group_keyword_id = ? AND start_at = ? AND end_at = ?;

-- #[UPDATE_AD_GROUP_KEYWORD_PERFORMANCE_BY_ID]:
UPDATE Ad_Group_Keyword_Performance
SET
   impression_count = ?
 , click_count = ?
 , total_spending = ?
 , avg_position = ?
 , quality_score = ?
 , updated_ts = CURRENT_TIMESTAMP()
WHERE id = ?;

-- #[GET_AD_GROUP_KEYWORD_PERFORMANCE_BY_UPDATED_TIME_INTERVAL]:
SELECT id, created_ts, updated_ts, ad_group_keyword_id, start_at, end_at, impression_count, click_count, total_spending, avg_position, quality_score
FROM Ad_Group_Keyword_Performance
WHERE updated_ts BETWEEN ? AND ?;

-- #[COUNT_DAILY_ARRIVAL_BY_AD_GROUP_KEYWORD_PERFORMANCE]:
SELECT
		COUNT(A.id) as daily_arrival_count
FROM
		Arrival as A
JOIN
		Arrival_Property as AP
ON
		(AP.`name` = 'ad_group_keyword_id' AND AP.`value` = ?)
WHERE
		DATE(A.created_ts) = DATE(?) AND A.id = AP.arrival_id;


