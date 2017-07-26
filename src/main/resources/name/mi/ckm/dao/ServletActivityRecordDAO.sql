-- #[INSERT_INTO_SERVLET_ACTIVITY_RECORD]:
INSERT INTO Servlet_Activity_Record(id, created_ts, servlet_name, operator, task, is_succeed, comment)
VALUES (
NULL,
CURRENT_TIMESTAMP(),
?,
?,
?,
?,
?
);