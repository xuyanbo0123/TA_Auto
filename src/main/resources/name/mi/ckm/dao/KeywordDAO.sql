-- #[INSERT_INTO_KEYWORD]:
INSERT INTO Keyword(id, created_ts, updated_ts, text)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?
);

-- #[GET_KEYWORD_BY_ID]:
SELECT id, created_ts, updated_ts, text
FROM Keyword
WHERE id = ?;

-- #[GET_KEYWORD_BY_TEXT]:
SELECT id, created_ts, updated_ts, text
FROM Keyword
WHERE text = ?;

-- #[GET_ALL_KEYWORDS]:
SELECT id, created_ts, updated_ts, text
FROM Keyword;

-- #[BATCH_INSERT_OR_UPDATE_KEYWORD_BY_TEXT]:
INSERT INTO Keyword (id, created_ts, updated_ts, text)
VALUES (null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?)
ON DUPLICATE KEY
UPDATE
`updated_ts` = CURRENT_TIMESTAMP;