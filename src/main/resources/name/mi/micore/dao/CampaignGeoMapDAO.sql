-- #[GET_CAMPAIGN_GEO_MAP_BY_TRAFFIC_CAMPAIGN_ID]:
SELECT id, created_ts, updated_ts, traffic_campaign_id, city, state, state_abbr
FROM Campaign_Geo_Map
WHERE traffic_campaign_id = ?;