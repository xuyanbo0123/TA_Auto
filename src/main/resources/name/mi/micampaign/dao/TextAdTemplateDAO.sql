-- #[INSERT_INTO_TEXT_AD_TEMPLATE]:
INSERT INTO Text_Ad_Template
      (id, created_ts, updated_ts, headline, description1, description2, displayUrl, actionUrl, priority, `group`)
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
?
);

-- #[GET_TEXT_AD_TEMPLATE_BY_CONTENT]:
SELECT id, created_ts, updated_ts, headline, description1, description2, displayUrl, actionUrl, priority, `group`
FROM Text_Ad_Template
WHERE headline = ?
  AND description1 = ?
  AND description2 = ?
  AND displayUrl = ?
  AND actionUrl = ?;

-- #[GET_TEXT_AD_TEMPLATES_BY_GROUP_ORDER_BY_PRIORITY]:
SELECT id, created_ts, updated_ts, headline, description1, description2, displayUrl, actionUrl, priority, `group`
FROM Text_Ad_Template
WHERE `group` = ?
ORDER BY priority DESC;

-- #[GET_TEXT_AD_TEMPLATE_BY_ID]:
SELECT id, created_ts, updated_ts, headline, description1, description2, displayUrl, actionUrl, priority, `group`
FROM Text_Ad_Template
WHERE id = ?