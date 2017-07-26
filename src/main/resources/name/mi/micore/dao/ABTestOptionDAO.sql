-- #[INSERT_INTO_AB_TEST_OPTION]:
INSERT INTO AB_Test_Option(`id`, `created_ts`, `updated_ts`, `ab_test_id`, `option`, `weight`, `status`, `description`)
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

-- #[GET_AB_TEST_OPTION_BY_ID]:
SELECT `id`, `created_ts`, `updated_ts`, `ab_test_id`, `option`, `weight`, `status`, `description`
FROM AB_Test_Option
WHERE `id` = ?;

-- #[UPDATE_AB_TEST_OPTION_STATUS_BY_ID]:
UPDATE AB_Test_Option set `status` = ?, `updated_ts` = CURRENT_TIMESTAMP()
WHERE id = ?;