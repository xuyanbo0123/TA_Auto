-- #[INSERT_INTO_AD_GROUP_KEYWORD_DAILY_SPENDING]:
INSERT INTO Ad_Group_Keyword_Daily_Spending(id, created_ts, updated_ts,
ad_group_keyword_id, `day`, impression_count, click_count, total_spending, avg_position, quality_score, visits, bounces, goal1, goal2)
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

-- #[GET_AD_GROUP_KEYWORD_DAILY_SPENDING_BY_ID]:
SELECT id, created_ts, updated_ts, ad_group_keyword_id, `day`, impression_count, click_count, total_spending, avg_position, quality_score, visits, bounces, goal1, goal2
FROM Ad_Group_Keyword_Daily_Spending WHERE id = ?;



-- #[GET_SPENDINGS_BY_AD_GROUP_KEYWORD_ID]:
SELECT id, created_ts, updated_ts, ad_group_keyword_id, `day`, impression_count, click_count, total_spending, avg_position, quality_score, visits, bounces, goal1, goal2
FROM Ad_Group_Keyword_Daily_Spending
WHERE ad_group_keyword_id = ?
AND `day` BETWEEN ? AND ?;



-- #[INSERT_OR_UPDATE_AD_GROUP_KEYWORD_DAILY_SPENDING_GOOGLE_AD_WORDS_PART]:
INSERT INTO Ad_Group_Keyword_Daily_Spending(id, created_ts, updated_ts, ad_group_keyword_id, `day`, impression_count, click_count, total_spending, avg_position, quality_score, visits, bounces, goal1, goal2)
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
    ?)
ON DUPLICATE KEY
UPDATE `updated_ts`=CURRENT_TIMESTAMP(),
  `impression_count`=VALUES(`impression_count`),
  `click_count`=VALUES(`click_count`),
  `total_spending`=VALUES(`total_spending`),
  `avg_position`=VALUES(`avg_position`),
  `quality_score`=VALUES(`quality_score`);

-- #[INSERT_OR_UPDATE_AD_GROUP_KEYWORD_DAILY_SPENDING_GOOGLE_ANALYTICAL_PART]:
INSERT INTO Ad_Group_Keyword_Daily_Spending(id, created_ts, updated_ts, ad_group_keyword_id, `day`, impression_count, click_count, total_spending, avg_position, quality_score, visits, bounces, goal1, goal2)
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
    ?)
ON DUPLICATE KEY
UPDATE `updated_ts`=CURRENT_TIMESTAMP(),
  `visits`=VALUES(`visits`),
  `bounces`=VALUES(`bounces`),
  `goal1`=VALUES(`goal1`),
  `goal2`=VALUES(`goal2`);

