-- #[DAILY_REPORT]:
SELECT
  CONCAT(Traffic_Source.`name`,"(",Traffic_Source.id,")") as source,
  CONCAT(Traffic_Campaign.`name`,"(",Traffic_Campaign.id,")") as campaign,
  CONCAT(Ad_Group.`name`,"(",Ad_Group.id,")") as ad_group,
  CONCAT(Keyword.text,"(",Ad_Group_Keyword.id,")") as keyword,
  Keyword.text as text,
  Ad_Group_Keyword_Daily_Spending.`day`,
  Ad_Group_Keyword_Daily_Spending.impression_count as impression,
  Ad_Group_Keyword_Daily_Spending.click_count as click,
  Ad_Group_Keyword_Daily_Spending.arrival_count as arrival,
  Ad_Group_Keyword_Daily_Spending.total_spending as cost,
  Ad_Group_Keyword_Daily_Spending.avg_position as adpos
FROM
	Ad_Group_Keyword_Daily_Spending
JOIN
    Ad_Group_Keyword ON (Ad_Group_Keyword_Daily_Spending.ad_group_keyword_id = Ad_Group_Keyword.id)
JOIN
    Keyword ON (Ad_Group_Keyword.keyword_id = Keyword.id)
JOIN
    Ad_Group ON (Ad_Group.id = Ad_Group_Keyword.ad_group_id)
JOIN
    Traffic_Campaign ON (Traffic_Campaign.id = Ad_Group.traffic_campaign_id)
JOIN
    Traffic_Source ON (Traffic_Source.id = Traffic_Campaign.sid)

-- #[DAILY_REPORT_FULL]:
SELECT
  CONCAT(Traffic_Source.`name`,"(",Traffic_Source.id,")") as source,
  CONCAT(Traffic_Campaign.`name`,"(",Traffic_Campaign.id,")") as campaign,
  CONCAT(Ad_Group.`name`,"(",Ad_Group.id,")") as ad_group,
  CONCAT(Keyword.text,"(",Ad_Group_Keyword.id,")") as keyword,
  Keyword.text as text,
  Ad_Group_Keyword_Daily_Spending.`day`,
  Ad_Group_Keyword_Daily_Spending.impression_count as impression,
  Ad_Group_Keyword_Daily_Spending.click_count as click,
  Ad_Group_Keyword_Daily_Spending.arrival_count as arrival,
  Ad_Group_Keyword_Daily_Spending.total_spending as cost,
  Ad_Group_Keyword_Daily_Spending.avg_position as adpos,
  Ad_Group_Keyword_Daily_Revenue.conversion_count as conversion,
  Ad_Group_Keyword_Daily_Revenue.lead_count as `lead`,
  Ad_Group_Keyword_Daily_Revenue.ad_click_count as clickout,
  Ad_Group_Keyword_Daily_Revenue.ad_impression_count as adimp,
  (Ad_Group_Keyword_Daily_Revenue.total_lead_revenue+Ad_Group_Keyword_Daily_Revenue.total_ad_click_revenue) as `value`,
  Ad_Group_Keyword_Daily_Revenue.total_lead_revenue,
  Ad_Group_Keyword_Daily_Revenue.total_ad_click_revenue
FROM
	Ad_Group_Keyword_Daily_Spending
JOIN
    Ad_Group_Keyword_Daily_Revenue ON (Ad_Group_Keyword_Daily_Spending.ad_group_keyword_id = Ad_Group_Keyword_Daily_Revenue.ad_group_keyword_id AND Ad_Group_Keyword_Daily_Spending.`day` = Ad_Group_Keyword_Daily_Revenue.`day`)
JOIN
    Ad_Group_Keyword ON (Ad_Group_Keyword_Daily_Spending.ad_group_keyword_id = Ad_Group_Keyword.id)
JOIN
    Keyword ON (Ad_Group_Keyword.keyword_id = Keyword.id)
JOIN
    Ad_Group ON (Ad_Group.id = Ad_Group_Keyword.ad_group_id)
JOIN
    Traffic_Campaign ON (Traffic_Campaign.id = Ad_Group.traffic_campaign_id)
JOIN
    Traffic_Source ON (Traffic_Source.id = Traffic_Campaign.sid)



