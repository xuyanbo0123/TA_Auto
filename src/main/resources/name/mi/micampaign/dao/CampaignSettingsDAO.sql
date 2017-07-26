-- #[VIEW_KEYWORD_FULL]:
SELECT
  Ad_Group_Keyword.id AS keyword_id,
  Ad_Group_Keyword.`match_type`,
  Ad_Group_Keyword.`local_status` AS keyword_l_status,
  Ad_Group_Keyword.provider_supplied_id AS keyword_ps_id,
  Ad_Group_Keyword.`provider_status` AS keyword_ps_status,
  Ad_Group_Keyword.`serving_status` AS keyword_sv_status,
  Ad_Group_Keyword.`approval_status` AS keyword_ap_status,
  Ad_Group_Keyword.`criterion_use` AS keyword_criterion_use,
  Ad_Group_Keyword.`bid_type` AS keyword_bid_type,
  Ad_Group_Keyword.`bid_amount` AS keyword_bid_amount,
  Ad_Group_Keyword.uploaded_ts AS keyword_uploaded_ts,
  Keyword.text AS keyword_text,
  Ad_Group_Keyword.ad_group_id,
  Ad_Group.`name` AS ad_group_name,
  Ad_Group.`local_status` AS ad_group_l_status,
  Ad_Group.provider_supplied_id AS ad_group_ps_id,
  Ad_Group.`provider_status` AS ad_group_ps_status,
  Ad_Group.traffic_campaign_id AS campaign_id,
  Traffic_Campaign.`name` AS campaign_name,
  Traffic_Campaign.`local_status` AS campaign_l_status,
  Traffic_Campaign.provider_supplied_id AS campaign_ps_id,
  Traffic_Campaign.`provider_status` AS campaign_ps_status,
  Traffic_Source.id AS source_id,
  Traffic_Source.`name` AS source_name,
  Traffic_Provider.id AS provider_id,
  Traffic_Provider.`name` AS provider_name
FROM Ad_Group_Keyword
  JOIN Keyword ON (Ad_Group_Keyword.keyword_id = Keyword.id)
  JOIN Ad_Group ON (Ad_Group_Keyword.ad_group_id = Ad_Group.id)
  JOIN Traffic_Campaign ON (Ad_Group.traffic_campaign_id = Traffic_Campaign.id)
  JOIN Traffic_Source ON (Traffic_Campaign.sid = Traffic_Source.id)
  JOIN Traffic_Provider ON (Traffic_Source.traffic_provider_id = Traffic_Provider.id)

-- #[VIEW_AD_FULL]:
SELECT
  Ad_Group_Ad.id AS ad_id,
  Ad_Group_Ad.`local_status` AS ad_l_status,
  Ad_Group_Ad.provider_supplied_id AS ad_ps_id,
  Ad_Group_Ad.`provider_status` AS ad_ps_status,
  Ad_Group_Ad.`approval_status` AS ad_ap_status,
  Ad_Group_Ad.uploaded_ts AS ad_uploaded_ts,
  Ad_Group_Ad.ad_id AS text_ad_id,
  Text_Ad.headline,
  Text_Ad.description1,
  Text_Ad.description2,
  Text_Ad.displayUrl,
  Text_Ad.actionUrl,
  Ad_Group_Ad.ad_group_id,
  Ad_Group.`name` AS ad_group_name,
  Ad_Group.`local_status` AS ad_group_l_status,
  Ad_Group.provider_supplied_id AS ad_group_ps_id,
  Ad_Group.`provider_status` AS ad_group_ps_status,
  Ad_Group.traffic_campaign_id AS campaign_id,
  Traffic_Campaign.`name` AS campaign_name,
  Traffic_Campaign.`local_status` AS campaign_l_status,
  Traffic_Campaign.provider_supplied_id AS campaign_ps_id,
  Traffic_Campaign.`provider_status` AS campaign_ps_status,
  Traffic_Source.id AS source_id,
  Traffic_Source.`name` AS source_name,
  Traffic_Provider.id AS provider_id,
  Traffic_Provider.`name` AS provider_name
FROM Ad_Group_Ad
  JOIN Text_Ad ON (Ad_Group_Ad.ad_id = Text_Ad.id)
  JOIN Ad_Group ON (Ad_Group_Ad.ad_group_id = Ad_Group.id)
  JOIN Traffic_Campaign ON (Ad_Group.traffic_campaign_id = Traffic_Campaign.id)
  JOIN Traffic_Source ON (Traffic_Campaign.sid = Traffic_Source.id)
  JOIN Traffic_Provider ON (Traffic_Source.traffic_provider_id = Traffic_Provider.id)

-- #[VIEW_AD_GROUP_FULL]:
SELECT
  Ad_Group.id AS ad_group_id,
  Ad_Group.`name` AS ad_group_name,
  Ad_Group.`local_status` AS ad_group_l_status,
  Ad_Group.provider_supplied_id AS ad_group_ps_id,
  Ad_Group.`provider_status` AS ad_group_ps_status,
  Ad_Group.traffic_campaign_id AS campaign_id,
  Traffic_Campaign.`name` AS campaign_name,
  Traffic_Campaign.`local_status` AS campaign_l_status,
  Traffic_Campaign.provider_supplied_id AS campaign_ps_id,
  Traffic_Campaign.`provider_status` AS campaign_ps_status,
  Traffic_Source.id AS source_id,
  Traffic_Source.`name` AS source_name,
  Traffic_Provider.id AS provider_id,
  Traffic_Provider.`name` AS provider_name
FROM Ad_Group
  JOIN Traffic_Campaign ON (Ad_Group.traffic_campaign_id = Traffic_Campaign.id)
  JOIN Traffic_Source ON (Traffic_Campaign.sid = Traffic_Source.id)
  JOIN Traffic_Provider ON (Traffic_Source.traffic_provider_id = Traffic_Provider.id)

-- #[VIEW_CAMPAIGN_FULL]:
SELECT
  Traffic_Campaign.id AS campaign_id,
  Traffic_Campaign.`name` AS campaign_name,
  Traffic_Campaign.`local_status` AS campaign_l_status,
  Traffic_Campaign.provider_supplied_id AS campaign_ps_id,
  Traffic_Campaign.`provider_status` AS campaign_ps_status,
  Traffic_Source.id AS source_id,
  Traffic_Source.`name` AS source_name,
  Traffic_Provider.id AS provider_id,
  Traffic_Provider.`name` AS provider_name
FROM Traffic_Campaign
  JOIN Traffic_Source ON (Traffic_Campaign.sid = Traffic_Source.id)
  JOIN Traffic_Provider ON (Traffic_Source.traffic_provider_id = Traffic_Provider.id)

-- #[VIEW_SOURCE_FULL]:
SELECT
  Traffic_Source.id AS source_id,
  Traffic_Source.`name` AS source_name,
  Traffic_Source.`traffic_type` AS traffic_type,
  Traffic_Provider.id AS provider_id,
  Traffic_Provider.`name` AS provider_name
FROM Traffic_Source
  JOIN Traffic_Provider ON Traffic_Source.traffic_provider_id = Traffic_Provider.id
