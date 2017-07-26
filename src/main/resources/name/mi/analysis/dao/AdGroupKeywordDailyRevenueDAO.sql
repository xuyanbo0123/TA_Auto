-- #[INSERT_INTO_AD_GROUP_KEYWORD_DAILY_REVENUE]:
INSERT INTO Ad_Group_Keyword_Daily_Revenue(id, created_ts, updated_ts,
ad_group_keyword_id, day, lead_count, total_lead_revenue, ad_impression_count, ad_click_count, total_ad_click_revenue, conversion_count)
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

-- #[GET_AD_GROUP_KEYWORD_DAILY_REVENUE_BY_ID]:
SELECT id, created_ts, updated_ts, ad_group_keyword_id, day, lead_count, total_lead_revenue, ad_impression_count, ad_click_count, total_ad_click_revenue, conversion_count
FROM Ad_Group_Keyword_Daily_Revenue
WHERE id = ?;

-- #[INSERT_OR_UPDATE_AD_GROUP_KEYWORD_DAILY_REVENUE]:
INSERT INTO Ad_Group_Keyword_Daily_Revenue(id, created_ts, updated_ts,
ad_group_keyword_id, day, lead_count, total_lead_revenue, ad_impression_count, ad_click_count, total_ad_click_revenue, conversion_count)
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
)
ON DUPLICATE KEY
UPDATE `updated_ts`=CURRENT_TIMESTAMP(),
`lead_count`=VALUES(`lead_count`),
`total_lead_revenue`=VALUES(`total_lead_revenue`),
`ad_impression_count`=VALUES(`ad_impression_count`),
`ad_click_count`=VALUES(`ad_click_count`),
`total_ad_click_revenue`=VALUES(`total_ad_click_revenue`),
`conversion_count`=VALUES(`conversion_count`);

-- #[GET_CLICK_REVENUE_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_CLICK_OUT_UPDATED_TS]:
SELECT Arrival_Property.value AS ad_group_keyword_id, DATE(Arrival.created_ts) AS day, COUNT(Click_Out.id) AS ad_click_count, SUM(Click_Out.amount) AS total_ad_click_revenue
FROM Click_Out
  JOIN Click_Impression ON Click_Out.click_impression_id = Click_Impression.id
  JOIN Click_Impression_Request ON Click_Impression.click_impression_request_id = Click_Impression_Request.id
  JOIN Arrival ON Click_Impression_Request.arrival_id = Arrival.id
  JOIN Arrival_Property ON Arrival.id = Arrival_Property.arrival_id AND Arrival_Property.name = 'ad_group_keyword_id'
WHERE Click_Out.updated_ts BETWEEN ? AND ?
GROUP BY 1, 2;

-- #[GET_LEAD_REVENUE_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_LEAD_REVENUE_UPDATED_TS]:
SELECT Arrival_Property.value AS ad_group_keyword_id, DATE(Arrival.created_ts) AS day, COUNT(Lead_Revenue.id) AS lead_count, SUM(Lead_Revenue.amount) AS total_lead_revenue
FROM Lead_Revenue
  JOIN Lead_Request ON Lead_Revenue.lead_request_id = Lead_Request.id
  JOIN Arrival ON Lead_Request.arrival_id = Arrival.id
  JOIN Arrival_Property ON Arrival.id = Arrival_Property.arrival_id AND Arrival_Property.name = 'ad_group_keyword_id'
WHERE Lead_Revenue.updated_ts BETWEEN ? AND ?
GROUP BY 1, 2;

-- #[GET_CONVERSION_COUNT_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_ARRIVAL_CREATED_TS]:
SELECT Arrival_Property.value AS ad_group_keyword_id, DATE(Arrival.created_ts) AS day, COUNT(Arrival.id) AS conversion_count
FROM Arrival
  JOIN Arrival_Property ON Arrival.id = Arrival_Property.arrival_id AND Arrival_Property.name = 'ad_group_keyword_id'
WHERE Arrival.conversion_count > 0 AND Arrival.created_ts BETWEEN ? AND ?
GROUP BY 1, 2;

-- #[GET_AD_IMPRESSION_COUNT_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_ARRIVAL_CREATED_TS]:
SELECT Arrival_Property.value AS ad_group_keyword_id, DATE(Arrival.created_ts) AS day, COUNT(Click_Impression.id) AS ad_impression_count
FROM Arrival
  JOIN Arrival_Property ON Arrival.id = Arrival_Property.arrival_id AND Arrival_Property.name = 'ad_group_keyword_id'
  JOIN Click_Impression_Request ON Arrival.id = Click_Impression_Request.arrival_id
  JOIN Click_Impression ON Click_Impression_Request.id = Click_Impression.click_impression_request_id
WHERE Arrival.created_ts BETWEEN ? AND ?
GROUP BY 1, 2;

-- #[INSERT_OR_UPDATE_CONVERSION_COUNT_BY_AD_GROUP_KEYWORD_ID_AND_DAY]:
INSERT INTO Ad_Group_Keyword_Daily_Revenue(id, created_ts, updated_ts,
ad_group_keyword_id, day, lead_count, total_lead_revenue, ad_impression_count, ad_click_count, total_ad_click_revenue, conversion_count)
VALUES (NULL, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), ?, ?, ?, ?, ?, ?, ?, ?)
ON DUPLICATE KEY UPDATE
  `updated_ts`=CURRENT_TIMESTAMP(),
  `conversion_count`=VALUES(`conversion_count`);

-- #[INSERT_OR_UPDATE_CLICK_REVENUE_BY_AD_GROUP_KEYWORD_ID_AND_DAY]:
INSERT INTO Ad_Group_Keyword_Daily_Revenue(id, created_ts, updated_ts,
ad_group_keyword_id, day, lead_count, total_lead_revenue, ad_impression_count, ad_click_count, total_ad_click_revenue, conversion_count)
VALUES (NULL, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), ?, ?, ?, ?, ?, ?, ?, ?)
ON DUPLICATE KEY UPDATE
  `updated_ts`=CURRENT_TIMESTAMP(),
  `ad_click_count`=VALUES(`ad_click_count`),
  `total_ad_click_revenue`=VALUES(`total_ad_click_revenue`);

-- #[INSERT_OR_UPDATE_LEAD_REVENUE_BY_AD_GROUP_KEYWORD_ID_AND_DAY]:
INSERT INTO Ad_Group_Keyword_Daily_Revenue(id, created_ts, updated_ts,
ad_group_keyword_id, day, lead_count, total_lead_revenue, ad_impression_count, ad_click_count, total_ad_click_revenue, conversion_count)
VALUES (NULL, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), ?, ?, ?, ?, ?, ?, ?, ?)
ON DUPLICATE KEY UPDATE
  `updated_ts`=CURRENT_TIMESTAMP(),
  `lead_count`=VALUES(`lead_count`),
  `total_lead_revenue`=VALUES(`total_lead_revenue`);

-- #[INSERT_OR_UPDATE_AD_IMPRESSION_COUNT_BY_AD_GROUP_KEYWORD_ID_AND_DAY]:
INSERT INTO Ad_Group_Keyword_Daily_Revenue(id, created_ts, updated_ts,
ad_group_keyword_id, day, lead_count, total_lead_revenue, ad_impression_count, ad_click_count, total_ad_click_revenue, conversion_count)
VALUES (NULL, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), ?, ?, ?, ?, ?, ?, ?, ?)
ON DUPLICATE KEY UPDATE
  `updated_ts`=CURRENT_TIMESTAMP(),
  `ad_impression_count`=VALUES(`ad_impression_count`);