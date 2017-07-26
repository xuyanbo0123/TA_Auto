-- #[INSERT_INTO_QDB_QSET]:
INSERT INTO QDB_QSet
      (id, created_ts, updated_ts, user_id, `count`, state, elapsed_time, qset_type, qset_num,record_num, answered)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
0,
?,
?,
0,
0
);

-- #[GET_QDB_QSET_BY_ID]:
SELECT id, created_ts, updated_ts, user_id, `count`, state, elapsed_time, qset_type, qset_num, record_num, answered
FROM QDB_QSet
WHERE id = ?;



-- #[UPDATE_QDB_QSET_ELAPSED_TIME_BY_ID]:
UPDATE
  QDB_QSet
SET
  elapsed_time = ?,
  updated_ts = CURRENT_TIMESTAMP
WHERE
  id = ?;

-- #[UPDATE_QDB_QSET_RECORD_NUM_BY_ID]:
UPDATE
  QDB_QSet
SET
  record_num = ?,
  updated_ts = CURRENT_TIMESTAMP
WHERE
  id = ?;

