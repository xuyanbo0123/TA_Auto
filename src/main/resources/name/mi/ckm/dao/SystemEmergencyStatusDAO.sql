-- #[INSERT_INTO_SYSTEM_EMERGENCY_STATUS]:
INSERT INTO System_Emergency_Status(id, created_ts, emergency_status)
VALUES (
NULL,
CURRENT_TIMESTAMP(),
?
);

-- #[GET_CURRENT_SYSTEM_EMERGENCY_STATUS]:
SELECT id, created_ts, emergency_status
FROM System_Emergency_Status
ORDER BY id
DESC LIMIT 1;