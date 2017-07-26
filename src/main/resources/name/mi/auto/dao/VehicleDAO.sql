-- #[INSERT_INTO_VEHICLE]:
INSERT INTO Vehicle(id, created_ts, updated_ts, auto_form_id, `year`, make, model, trim, is_alarm_track, is_commute, commute_distance, is_leased, yearly_mileage, deductible_coll, deductible_comp)
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
?
);

-- #[GET_VEHICLE_BY_ID]:
SELECT id, created_ts, updated_ts, auto_form_id, `year`, make, model, trim, is_alarm_track, is_commute, commute_distance, is_leased, yearly_mileage, deductible_coll, deductible_comp
FROM Vehicle
WHERE id = ?;

-- #[GET_VEHICLES_BY_AUTO_FORM_ID]:
SELECT id, created_ts, updated_ts, auto_form_id, `year`, make, model, trim, is_alarm_track, is_commute, commute_distance, is_leased, yearly_mileage, deductible_coll, deductible_comp
FROM Vehicle
WHERE auto_form_id = ?;