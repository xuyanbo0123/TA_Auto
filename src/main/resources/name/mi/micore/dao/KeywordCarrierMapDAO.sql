-- #[GET_CARRIER_TAG_BY_KEYWORD]:
SELECT tag
FROM Carrier_List
  JOIN Keyword_Carrier_Map ON Carrier_List.id = Keyword_Carrier_Map.carrier_list_id
  AND Keyword_Carrier_Map.keyword = ?
  AND Keyword_Carrier_Map.enabled = 1;

-- #[GET_CARRIER_NAME_BY_KEYWORD]:
SELECT name
FROM Carrier_List
  JOIN Keyword_Carrier_Map ON Carrier_List.id = Keyword_Carrier_Map.carrier_list_id
  AND Keyword_Carrier_Map.keyword = ?
  AND Keyword_Carrier_Map.enabled = 1;

-- #[GET_ALL_KEYWORD_CARRIER_MAP]:
SELECT id, created_ts, updated_ts, keyword, carrier_list_id FROM Keyword_Carrier_Map WHERE enabled = 1;