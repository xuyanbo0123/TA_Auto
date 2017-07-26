-- #[INSERT_INTO_AD_GROUP_AD]:
INSERT INTO Ad_Group_Ad(id, created_ts, updated_ts, ad_group_id, ad_id, provider_supplied_id, uploaded_ts, local_status, provider_status, approval_status, is_uploaded)
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

-- #[GET_AD_GROUP_AD_ID_BY_AD_GROUP_AND_PROVIDER_SUPPLIED_ID]:
SELECT Ad_Group_Ad.id FROM Ad_Group_Ad
  JOIN Ad_Group ON Ad_Group_Ad.ad_group_id = Ad_Group.id
WHERE Ad_Group.provider_supplied_id = ? and Ad_Group_Ad.provider_supplied_id = ?;

-- #[UPDATE_AD_GROUP_AD_LOCAL_STATUS_BY_ID]:
UPDATE Ad_Group_Ad
SET
    local_status = ?,
    updated_ts = CURRENT_TIMESTAMP
WHERE
    id = ?
AND
    local_status != 'deleted';

-- #[BATCH_INSERT_OR_UPDATE_AD_GROUP_AD_BY_PROVIDER_SUPPLIED_ID]:
INSERT INTO Ad_Group_Ad (id, created_ts, updated_ts, ad_group_id, ad_id, provider_supplied_id, uploaded_ts, local_status, provider_status, approval_status, is_uploaded)
VALUES
(
null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
?,
(SELECT id FROM Text_Ad WHERE headline = ? AND description1 = ? AND description2 = ? AND displayUrl = ? AND actionUrl = ?),
?, ?, ?, ?, ?, ?
)
ON DUPLICATE KEY
UPDATE
`updated_ts` = CURRENT_TIMESTAMP,
`ad_id` = VALUES(`ad_id`),
`uploaded_ts` = VALUES(`uploaded_ts`),
`provider_status` = VALUES(`provider_status`),
`approval_status` = VALUES(`approval_status`),
`is_uploaded` = VALUES(`is_uploaded`);

-- #[GET_AD_GROUP_AD_BY_AD_GROUP_ID_AND_AD_ID]:
SELECT id, created_ts, updated_ts, ad_group_id, ad_id, provider_supplied_id, uploaded_ts, local_status, provider_status, approval_status, is_uploaded
FROM Ad_Group_Ad
WHERE ad_group_id = ?
AND ad_id = ?;


-- #[GET_AD_GROUP_ADS_BY_AD_GROUP_ID]:
SELECT id, created_ts, updated_ts, ad_group_id, ad_id, provider_supplied_id, uploaded_ts, local_status, provider_status, approval_status, is_uploaded
FROM Ad_Group_Ad
WHERE ad_group_id = ?;

-- #[GET_PENDING_SYNC_AD_GROUP_ADS]:
SELECT id, created_ts, updated_ts, ad_group_id, ad_id, provider_supplied_id, uploaded_ts, local_status, provider_status, approval_status, is_uploaded
FROM Ad_Group_Ad
WHERE is_uploaded = false AND (local_status != 'disabled' OR provider_status != 'disabled');

-- #[SET_IS_UPLOADED_FOR_ALL_AD_GROUP_ADS]:
UPDATE Ad_Group_Ad SET is_uploaded = ?;

-- #[UPDATE_AD_GROUP_AD_PROVIDER_SUPPLIED_ID_BY_ID]:
UPDATE Ad_Group_Ad
SET provider_supplied_id = ?, updated_ts = CURRENT_TIMESTAMP
WHERE id = ?;

-- #[RESET_PROVIDER_SUPPLIED_ID_BEFORE_REACTIVE_AD_GROUP_ADS]:
UPDATE Ad_Group_Ad
SET provider_supplied_id = null
WHERE is_uploaded = false AND local_status = 'enabled' AND provider_status = 'disabled';