-- #[INSERT_INTO_WEB_PAGE]:
INSERT INTO Web_Page(id, created_ts, updated_ts, uri)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?
);

-- #[GET_WEB_PAGE_BY_ID]:
SELECT id, created_ts, updated_ts, uri
FROM Web_Page
WHERE id = ?;

-- #[GET_WEB_PAGE_ID_BY_URI]:
SELECT id
FROM Web_Page
WHERE uri = ?;