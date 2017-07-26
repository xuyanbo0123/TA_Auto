-- #[INSERT_INTO_AD_GROUP_KEYWORD]:
INSERT INTO
Ad_Group_Keyword(id, created_ts, updated_ts, ad_group_id, keyword_id, match_type, provider_supplied_id, uploaded_ts, local_status, provider_status, serving_status, approval_status, criterion_use, bid_type, bid_amount, is_uploaded)
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
?,
?,
?,
?,
?
);

-- #[GET_AD_GROUP_KEYWORD_ID_BY_AD_GROUP_AND_PROVIDER_SUPPLIED_ID]:
SELECT Ad_Group_Keyword.id FROM Ad_Group_Keyword
  JOIN Ad_Group ON Ad_Group_Keyword.ad_group_id = Ad_Group.id
WHERE Ad_Group.provider_supplied_id = ? and Ad_Group_Keyword.provider_supplied_id = ?;

-- #[GET_AD_GROUP_KEYWORD_BY_AD_GROUP_ID_AND_PROVIDER_SUPPLIED_ID]:
SELECT id, created_ts, updated_ts, ad_group_id, keyword_id, match_type, provider_supplied_id, uploaded_ts, local_status, provider_status, serving_status, approval_status, criterion_use, bid_type, bid_amount, is_uploaded
FROM Ad_Group_Keyword
WHERE ad_group_id = ? AND provider_supplied_id = ?;

-- #[GET_AD_GROUP_KEYWORD_BY_AD_GROUP_AND_PROVIDER_SUPPLIED_ID]:
SELECT * FROM Ad_Group_Keyword
  JOIN Ad_Group ON Ad_Group_Keyword.ad_group_id = Ad_Group.id
WHERE Ad_Group.provider_supplied_id = ? and Ad_Group_Keyword.provider_supplied_id = ?;

-- #[GET_AD_GROUP_KEYWORDS_BY_AD_GROUP_ID]:
SELECT id, created_ts, updated_ts, ad_group_id, keyword_id, match_type, provider_supplied_id, uploaded_ts, local_status, provider_status, serving_status, approval_status, criterion_use, bid_type, bid_amount, is_uploaded
FROM Ad_Group_Keyword
WHERE ad_group_id = ?;

-- #[GET_AD_GROUP_KEYWORDS_BY_MATCH_TYPE_CRITERION_USE]:
SELECT id, created_ts, updated_ts, ad_group_id, keyword_id, match_type, provider_supplied_id, uploaded_ts, local_status, provider_status, serving_status, approval_status, criterion_use, bid_type, bid_amount, is_uploaded
FROM Ad_Group_Keyword
WHERE
  match_type= ?
AND
  criterion_use = ?;

-- #[GET_AD_GROUP_KEYWORDS_BY_CRITERION_USE]:
SELECT id, created_ts, updated_ts, ad_group_id, keyword_id, match_type, provider_supplied_id, uploaded_ts, local_status, provider_status, serving_status, approval_status, criterion_use, bid_type, bid_amount, is_uploaded
FROM Ad_Group_Keyword
WHERE
  criterion_use = ?;

-- #[GET_AD_GROUP_KEYWORD_BY_AD_GROUP_ID_KEYWORD_ID_MATCH_TYPE_CRITERION_USE]:
SELECT id, created_ts, updated_ts, ad_group_id, keyword_id, match_type, provider_supplied_id, uploaded_ts, local_status, provider_status, serving_status, approval_status, criterion_use, bid_type, bid_amount, is_uploaded
FROM Ad_Group_Keyword
WHERE
  ad_group_id = ?
AND
  keyword_id = ?
AND
  match_type = ?
AND
  criterion_use = ?
;



-- #[GET_AD_GROUP_KEYWORD_BY_ID]:
SELECT id, created_ts, updated_ts, ad_group_id, keyword_id, match_type, provider_supplied_id, uploaded_ts, local_status, provider_status, serving_status, approval_status, criterion_use, bid_type, bid_amount, is_uploaded
FROM Ad_Group_Keyword WHERE id = ?;

-- #[UPDATE_AD_GROUP_KEYWORD_BID_TYPE_BID_AMOUNT_BY_ID]:
UPDATE
  Ad_Group_Keyword
SET
    local_status = ?,
    bid_type = ?,
    bid_amount = ?,
    is_uploaded = ?,
    updated_ts = CURRENT_TIMESTAMP
WHERE
  id = ?;

-- #[UPDATE_AD_GROUP_KEYWORD_LOCAL_STATUS_BY_ID]:
UPDATE Ad_Group_Keyword
SET
    local_status = ?,
    updated_ts = CURRENT_TIMESTAMP
WHERE
    id = ?
AND
    local_status != 'deleted';

-- #[BATCH_INSERT_OR_UPDATE_AD_GROUP_KEYWORD_BY_PROVIDER_SUPPLIED_ID]:
INSERT INTO
Ad_Group_Keyword (id, created_ts, updated_ts, ad_group_id, keyword_id, match_type, provider_supplied_id, uploaded_ts, local_status, provider_status, serving_status, approval_status, criterion_use, bid_type, bid_amount, is_uploaded)
VALUES (null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, (SELECT id FROM Keyword WHERE text = ?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
ON DUPLICATE KEY
UPDATE
`updated_ts` = CURRENT_TIMESTAMP,
`keyword_id` = VALUES(`keyword_id`),
`match_type` = VALUES(`match_type`),
`uploaded_ts` = VALUES(`uploaded_ts`),
`provider_status` = VALUES(`provider_status`),
`serving_status` = VALUES(`serving_status`),
`approval_status` = VALUES(`approval_status`),
`criterion_use` = VALUES(`criterion_use`),
`bid_type` = VALUES(`bid_type`),
`bid_amount` = VALUES(`bid_amount`),
`is_uploaded` = VALUES(`is_uploaded`);

-- #[GET_PENDING_SYNC_AD_GROUP_KEYWORDS]:
SELECT id, created_ts, updated_ts, ad_group_id, keyword_id, match_type, provider_supplied_id, uploaded_ts, local_status, provider_status, serving_status, approval_status, criterion_use, bid_type, bid_amount, is_uploaded
FROM Ad_Group_Keyword
WHERE is_uploaded = false AND (local_status != 'deleted' OR provider_status != 'deleted');

-- #[SET_IS_UPLOADED_FOR_ALL_AD_GROUP_KEYWORDS]:
UPDATE Ad_Group_Keyword SET is_uploaded = ?;

-- #[UPDATE_AD_GROUP_KEYWORD_PROVIDER_SUPPLIED_ID_BY_ID]:
UPDATE Ad_Group_Keyword
SET provider_supplied_id = ?, updated_ts = CURRENT_TIMESTAMP
WHERE id = ?;

-- #[RESET_PROVIDER_SUPPLIED_ID_BEFORE_REACTIVE_AD_GROUP_KEYWORDS]:
UPDATE Ad_Group_Keyword
SET provider_supplied_id = null
WHERE is_uploaded = false AND local_status = 'active' AND provider_status = 'deleted';