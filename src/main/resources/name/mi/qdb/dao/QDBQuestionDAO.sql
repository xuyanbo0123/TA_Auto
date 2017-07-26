-- #[GET_QDB_QUESTION_BY_ID]:
SELECT id , postal_state, question, answer1, answer2, answer3, answer4, hint, pic
FROM QDB_Questions
WHERE id = ?;

-- #[GET_FIXED_QDB_QUESTION_IDS]:
SELECT id
FROM qdb_questions
WHERE postal_state=?
ORDER BY id
LIMIT ?,?

-- #[GET_RANDOM_QDB_QUESTION_IDS]:
SELECT id
FROM qdb_questions
WHERE postal_state = ?
ORDER BY RAND()
LIMIT ?

-- #[GET_QDB_QUESTION_SIZE_BY_STATE]:
SELECT COUNT(*) AS `size`
FROM QDB_Questions
WHERE postal_state = ?

