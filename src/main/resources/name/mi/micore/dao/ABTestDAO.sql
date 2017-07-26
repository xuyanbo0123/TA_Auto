-- #[INSERT_INTO_AB_TEST]:
INSERT INTO AB_Test(id, created_ts, updated_ts, `name`, ab_test_group_id, status, description)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
?
);

-- #[GET_AB_TEST_BY_ID]:
SELECT id, created_ts, updated_ts, `name`, ab_test_group_id, status, description
FROM AB_Test
WHERE id = ?;

-- #[GET_AB_TEST_OPTION_BY_AB_TEST_ID]:
SELECT id, created_ts, updated_ts, `option`, weight, status, description
FROM AB_Test_Option
WHERE ab_test_id = ?;
