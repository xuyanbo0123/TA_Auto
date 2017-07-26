-- #[GET_PENDING_PROCESS_GOOGLE_NEW_ADS]:
SELECT id, created_ts, updated_ts, budget_name, campaign_name, ad_group_name, headline, description1, description2, displayUrl, actionUrl, is_created
FROM Google_New_Ad
WHERE is_created = 0;

-- #[INSERT_INTO_GOOGLE_NEW_AD]:
INSERT INTO Google_New_Ad (id, created_ts, updated_ts, budget_name, campaign_name, ad_group_name, headline, description1, description2, displayUrl, actionUrl, is_created)
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
  ?,
  ?
);

-- #[GET_GOOGLE_NEW_ADS_BY_AD_GROUP_NAME]:
SELECT id, created_ts, updated_ts, budget_name, campaign_name, ad_group_name, headline, description1, description2, displayUrl, actionUrl, is_created
FROM Google_New_Ad
WHERE ad_group_name = ?;