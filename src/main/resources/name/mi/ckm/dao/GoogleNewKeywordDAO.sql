-- #[GET_PENDING_PROCESS_GOOGLE_NEW_KEYWORDS]:
SELECT id, created_ts, updated_ts, budget_name, campaign_name, ad_group_name, keyword, is_biddable, match_type, bid_amount, is_created
FROM Google_New_Keyword
WHERE is_created = 0;

-- #[INSERT_INTO_GOOGLE_NEW_KEYWORD]:
INSERT INTO Google_New_Keyword (id, created_ts, updated_ts, budget_name, campaign_name, ad_group_name, keyword, is_biddable, match_type, bid_amount, is_created)
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
  ?,
  ?
);

-- #[GET_GOOGLE_NEW_KEYWORDS_UNIQUE_AD_GROUP_NAME_BY_ID_RANGE]:
SELECT DISTINCT ad_group_name
FROM Google_New_Keyword
WHERE id IN (?);