-- #[GET_VALID_BUYER_ACCOUNT_CONFIG_BY_BUYER_ACCOUNT_ID_AND_TYPE]:
SELECT id, created_ts, updated_ts, buyer_account_id, `type`, `rule`, send_to, `limit`, `count`, `priority`
FROM Buyer_Account_Config
WHERE buyer_account_id = ? AND `type` = ?;

-- #[INCREASE_BUYER_ACCOUNT_CONFIG_COUNT_BY_ID]:
UPDATE Buyer_Account_Config
SET updated_ts = CURRENT_TIMESTAMP(), `count` = `count`+1
WHERE id = ?;

-- #[RESET_BUYER_ACCOUNT_CONFIG_COUNT_BY_ID]:
UPDATE Buyer_Account_Config
SET updated_ts = CURRENT_TIMESTAMP(), `count` = 0
WHERE id = ?;

-- #[GET_BUYER_ACCOUNT_CONFIG_BY_BUYER_ACCOUNT_ID]:
SELECT id, created_ts, updated_ts, buyer_account_id, `type`, `rule`, send_to, `limit`, `count`, `priority`
FROM Buyer_Account_Config
WHERE buyer_account_id = ?;