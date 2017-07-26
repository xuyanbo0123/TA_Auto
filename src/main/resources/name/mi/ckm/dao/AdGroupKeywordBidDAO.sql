-- #[INSERT_INTO_AD_GROUP_KEYWORD_BID]:
INSERT INTO Ad_Group_Keyword_Bid(id, created_ts, updated_ts, ad_group_keyword_id, bid_amount)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?
);

-- #[GET_AD_GROUP_KEYWORD_BID_BY_ID]:
SELECT id, created_ts, updated_ts, ad_group_keyword_id, bid_amount
FROM Ad_Group_Keyword_Bid
WHERE id = ?;

-- #[GET_AD_GROUP_KEYWORD_BID_BY_AD_GROUP_KEYWORD_ID]:
SELECT id, created_ts, updated_ts, ad_group_keyword_id, bid_amount
FROM Ad_Group_Keyword_Bid
WHERE ad_group_keyword_id = ?
ORDER BY updated_ts DESC;