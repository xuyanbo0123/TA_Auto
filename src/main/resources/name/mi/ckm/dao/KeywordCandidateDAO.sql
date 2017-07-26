-- #[GET_KEYWORD_CANDIDATE_LIST_BY_IS_CREATED]:
SELECT id, keyword, essence, essence2, essence3, geo, geo2, geo3, `group`, campaign_name, budget_name, bid_amount,  is_created
FROM Keyword_Candidate
WHERE is_created = ?;

-- #[GET_KEYWORD_CANDIDATE_LIST_BY_GROUP_AND_IS_CREATED]:
SELECT id, keyword, essence, essence2, essence3, geo, geo2, geo3, `group`, campaign_name, budget_name, bid_amount, is_created
FROM Keyword_Candidate
WHERE `group` = ? AND is_created = ?;

-- #[UPDATE_IS_CREATED_BY_ID]:
UPDATE Keyword_Candidate
SET is_created = ?
WHERE id = ?;

-- #[DELETE_KEYWORD_CANDIDATE_BY_ID]:
DELETE FROM Keyword_Candidate
WHERE id = ?;