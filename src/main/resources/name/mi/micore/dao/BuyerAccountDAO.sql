-- #[INSERT_INTO_BUYER_ACCOUNT]:
INSERT INTO Buyer_Account(id, created_ts, updated_ts, buyer_id, lead_type_id, account_name, account_state)
VALUES (
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
?
);

-- #[GET_BUYER_ACCOUNT_BY_ID]:
SELECT id, created_ts, updated_ts, buyer_id, lead_type_id, account_name, account_state
FROM Buyer_Account
WHERE id = ?;

-- #[GET_BUYER_ACCOUNT_BY_LEAD_TYPE_ID_AND_ACCOUNT_STATE]:
SELECT id, created_ts, updated_ts, buyer_id, lead_type_id, account_name, account_state
FROM Buyer_Account
WHERE lead_type_id = ?
  AND account_state = ?;



