-- #[INSERT_INTO_REDIRECT_ACTION]:
INSERT INTO Redirect_Action(id, redirect_id, `action_ts`, impression_zone, click_ad_position)
VALUES (
NULL,
?,
CURRENT_TIMESTAMP(),
?,
?
);

-- #[GET_REDIRECT_ACTION_BY_ID]:
SELECT id, redirect_id, `action_ts`, impression_zone, click_ad_position
FROM Redirect_Action
WHERE id = ?;