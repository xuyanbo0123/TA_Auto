-- #[INSERT_INTO_ARRIVAL_AB_TEST_OPTION]:
INSERT INTO Arrival_AB_Test_Option(id, created_ts, updated_ts, arrival_id, ab_test_option_id)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?
);

-- #[GET_ARRIVAL_AB_TEST_OPTION_BY_ID]:
SELECT id, created_ts, updated_ts, arrival_id, ab_test_option_id
FROM Arrival_AB_Test_Option
WHERE id = ?;