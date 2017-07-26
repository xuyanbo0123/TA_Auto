-- #[INSERT_INTO_TEXT_AD]:
INSERT INTO Text_Ad(id, created_ts, updated_ts, headline, description1, description2, displayUrl, actionUrl)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
?,
?
);

-- #[GET_TEXT_AD_BY_CONTENT]:
SELECT id, created_ts, updated_ts, headline, description1, description2, displayUrl, actionUrl
FROM Text_Ad
WHERE headline = ? AND description1 = ? AND description2 = ? AND displayUrl = ? AND actionUrl = ?;

-- #[GET_ALL_TEXT_ADS]:
SELECT id, created_ts, updated_ts, headline, description1, description2, displayUrl, actionUrl FROM Text_Ad;

-- #[BATCH_INSERT_OR_UPDATE_TEXT_AD_BY_TEXT]:
INSERT INTO Text_Ad(id, created_ts, updated_ts, headline, description1, description2, displayUrl, actionUrl)
VALUES (NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?)
ON DUPLICATE KEY
UPDATE
`updated_ts` = CURRENT_TIMESTAMP;

-- #[GET_TEXT_AD_BY_ID]:
SELECT id, created_ts, updated_ts, headline, description1, description2, displayUrl, actionUrl
FROM Text_Ad
WHERE id = ?