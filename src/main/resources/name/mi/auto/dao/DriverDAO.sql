-- #[INSERT_INTO_DRIVER]:
INSERT INTO Driver(id, created_ts, updated_ts, auto_form_id, relationship, first_name, last_name, gender, marital_status, birthday, age_lic, education, credit, occupation, lic_status, is_sr22_required, primary_vehicle_id)
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
?
);

-- #[GET_DRIVER_BY_ID]:
SELECT id, created_ts, updated_ts, auto_form_id, relationship, first_name, last_name, gender, marital_status, birthday, age_lic, education, credit, occupation, lic_status, is_sr22_required, primary_vehicle_id
FROM Driver
WHERE id = ?;

-- #[GET_DRIVERS_BY_AUTO_FORM_ID]:
SELECT id, created_ts, updated_ts, auto_form_id, relationship, first_name, last_name, gender, marital_status, birthday, age_lic, education, credit, occupation, lic_status, is_sr22_required, primary_vehicle_id
FROM Driver
WHERE auto_form_id = ?;