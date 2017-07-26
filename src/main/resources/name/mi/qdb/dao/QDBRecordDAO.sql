-- #[INSERT_INTO_QDB_RECORD]:
INSERT INTO QDB_Record(id, created_ts, updated_ts, user_id, qset_id, question_id, user_answer, is_correct, elapsed_time, displayed_ts, number)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
-1,
NULL,
0,
CURRENT_TIMESTAMP(),
?
);

-- #[GET_QDB_RECORD_BY_ID]:
SELECT id, created_ts, updated_ts, user_id, qset_id, question_id, user_answer, is_correct, elapsed_time, displayed_ts, number
FROM QDB_Record
WHERE id = ?;

-- #[GET_QDB_RECORD_BY_QSET_ID_AND_RECORD_NUMBER]:
SELECT id, created_ts, updated_ts, user_id, qset_id, question_id, user_answer, is_correct, elapsed_time, displayed_ts, number
FROM QDB_Record
WHERE
  qset_id = ?
AND
  number = ?;


-- #[GET_QSET_ELAPSED_TIME_BY_QSET_ID]:
SELECT SUM(elapsed_time) AS qset_elapsed_time
FROM QDB_Record
WHERE qset_id = ?;

-- #[GET_QSET_ANSWERED_BY_QSET_ID]:
SELECT COUNT(*) AS qset_answered
FROM QDB_Record
WHERE qset_id = ?
AND `user_answer`>=0;

-- #[UPDATE_QDB_RECORD_BY_ID]:
UPDATE
  QDB_Record
SET
  `user_answer` = ?,
  is_correct = ?,
  elapsed_time = elapsed_time + TIMESTAMPDIFF(SECOND, displayed_ts, CURRENT_TIMESTAMP),
  updated_ts = CURRENT_TIMESTAMP
WHERE
  id = ?;

-- #[UPDATE_QDB_RECORD_BY_QSET_ID_AND_RECORD_NUMBER]:
UPDATE
  QDB_Record
SET
  user_answer = ?,
  is_correct = ?,
  elapsed_time = elapsed_time + TIMESTAMPDIFF(SECOND, displayed_ts, CURRENT_TIMESTAMP),
  updated_ts = CURRENT_TIMESTAMP
WHERE
    qset_id = ?
AND
    number = ?;


-- #[UPDATE_QDB_RECORD_DISPLAYED_TS_BY_ID]:
UPDATE
  QDB_Record
SET
  displayed_ts = CURRENT_TIMESTAMP,
  updated_ts = CURRENT_TIMESTAMP
WHERE
  id = ?;