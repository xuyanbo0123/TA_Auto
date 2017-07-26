-- #[INSERT_INTO_AD_GROUP_AD_PERFORMANCE]:
INSERT INTO Ad_Group_Ad_Performance(id, created_ts, updated_ts, ad_group_ad_id, start_at, end_at, impression_count, click_count, total_spending, avg_position)
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
?
);

-- #[GET_AD_GROUP_AD_PERFORMANCE_BY_ID]:
SELECT id, created_ts, updated_ts, ad_group_ad_id, start_at, end_at, impression_count, click_count, total_spending, avg_position
FROM Ad_Group_Ad_Performance
WHERE id = ?;


-- #[GET_AD_GROUP_AD_PERFORMANCE_ID_BY_AD_GROUP_AD_ID_AND_DATES]:
SELECT id
FROM Ad_Group_Ad_Performance
WHERE ad_group_ad_id = ?
  AND start_at = ?
  AND end_at = ?;

-- #[UPDATE_AD_GROUP_AD_PERFORMANCE_BY_ID]:
UPDATE Ad_Group_Ad_Performance
SET
  impression_count = ?,
  click_count = ?,
  total_spending = ?,
  avg_position = ?,
  updated_ts = CURRENT_TIMESTAMP()
WHERE id = ?;

