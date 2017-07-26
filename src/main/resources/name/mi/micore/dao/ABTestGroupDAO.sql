-- #[INSERT_INTO_AB_TEST_GROUP]:
INSERT INTO AB_Test_Group
      (id, created_ts, updated_ts, name, web_page_id, status, description)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
?
);

-- #[GET_AB_TEST_GROUP_BY_ID]:
SELECT id, created_ts, updated_ts, name, web_page_id, status, description
FROM AB_Test_Group
WHERE id = ?;


-- #[GET_ALL_AB_TEST]:
SELECT id, created_ts, updated_ts, name, ab_test_group_id, status, description
FROM AB_Test;

-- #[GET_ALL_AB_TEST_BY_STATUS]:
SELECT id, created_ts, updated_ts, name, ab_test_group_id, status, description
FROM AB_Test
WHERE AB_Test.status = ?;

-- #[GET_AB_TEST_BY_ID]:
SELECT id, created_ts, updated_ts, name, ab_test_group_id, status, description
FROM AB_Test
WHERE ab_test_group_id = ?;

-- #[GET_AB_TEST_BY_ID_STATUS]:
SELECT id, created_ts, updated_ts, name, ab_test_group_id, status, description
FROM AB_Test
WHERE ab_test_group_id = ?
AND AB_Test.status = ?;

-- #[GET_AB_TEST_BY_WEBPAGE_ID]:
SELECT a.id AS rid, a.created_ts AS rcreated_ts, a.updated_ts AS rupdated_ts, a.name AS rname, a.ab_test_group_id AS rab_test_group_id, a.status AS rstatus, a.description AS rdescription
FROM AB_Test AS a
INNER JOIN AB_Test_Group AS b ON a.ab_test_group_id = b.id
WHERE b.web_page_id = ?;

-- #[GET_AB_TEST_BY_WEBPAGE_ID_AB_TEST_GROUP_ID_ADDON]:
AND b.id = ?;

-- #[GET_AB_TEST_BY_WEBPAGE_ID_STATUS_ADDON]:
AND b.status = ? AND a.status = ?;