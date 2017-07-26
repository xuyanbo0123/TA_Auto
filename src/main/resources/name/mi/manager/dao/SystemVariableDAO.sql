-- #[GET_SYSTEM_VARIABLE_BY_NAME]:
SELECT `value`
FROM System_Variable
WHERE `name` = ?;

-- #[INSERT_OR_UPDATE_SYSTEM_VARIABLE_BY_NAME]:
INSERT INTO System_Variable
(`name`, `value`)
VALUES (  ?,  ?)
ON DUPLICATE KEY
UPDATE `name`=VALUES(`name`),
  `value`=VALUES(`value`);


