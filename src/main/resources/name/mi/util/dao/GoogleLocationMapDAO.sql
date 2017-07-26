-- #[GET_GOOGLE_LOCATION_MAP_BY_CRITERIA_ID]:
SELECT id, criteria_id, name, canonical_name, parent_id, country_code, target_type
FROM Google_Location_Map
WHERE criteria_id = ?;

-- #[GET_CRITERIA_ID_BY_NAME]:
SELECT criteria_id
FROM Google_Location_Map
WHERE `name` = ?;

