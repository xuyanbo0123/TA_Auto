-- #[GET_QDB_ANSWER_BY_ID]:
SELECT id, correctIdx, explanation
FROM QDB_Questions
WHERE id = ?;