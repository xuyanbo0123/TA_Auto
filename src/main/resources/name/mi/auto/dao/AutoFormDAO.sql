-- #[INSERT_INTO_AUTO_FORM]:
INSERT INTO Auto_Form(id, created_ts, updated_ts, lead_request_id, user_id, is_currently_insured, current_company, continuous_coverage, years_with_company, expire_date, coverage_type, email, phone, street, apt, zip, city, state, years_lived, is_owned, parking)
VALUES(
NULL,
CURRENT_TIMESTAMP(),
CURRENT_TIMESTAMP(),
?,
?,
?,
?,
?,
?,
?,
?,
?,
?,
?,
?,
?,
?,
?,
?,
?,
?
);

-- #[GET_AUTO_FORM_BY_ID]:
SELECT id, created_ts, updated_ts, lead_request_id, user_id, is_currently_insured, current_company, continuous_coverage, years_with_company, expire_date, coverage_type, email, phone, street, apt, zip, city, state, years_lived, is_owned, parking
FROM Auto_Form
WHERE id = ?;

-- #[GET_AUTO_FORM_BY_LEAD_REQUEST_ID]:
SELECT id, created_ts, updated_ts, lead_request_id, user_id, is_currently_insured, current_company, continuous_coverage, years_with_company, expire_date, coverage_type, email, phone, street, apt, zip, city, state, years_lived, is_owned, parking
FROM Auto_Form
WHERE lead_request_id = ?;