-- #[GET_ENVIRONMENT_VARIABLE_BY_NAME]:
SELECT `value`
FROM Environment_Variable
WHERE `name` = ?;
