-- #[GET_REVI_POLK_VEHICLE_BY_ID]:
SELECT ReviPolk_Vehicle.id as id,
       ReviPolk_Year.`name` as `year`,
       ReviPolk_Make.`name` as `make`,
       ReviPolk_Model.`name` as `model`
FROM ReviPolk_Vehicle
  JOIN ReviPolk_Year  ON ReviPolk_Year.id  = ReviPolk_Vehicle.year_id
  JOIN ReviPolk_Make  ON ReviPolk_Make.id  = ReviPolk_Vehicle.make_id
  JOIN ReviPolk_Model ON ReviPolk_Model.id = ReviPolk_Vehicle.model_id
WHERE ReviPolk_Vehicle.id = ?;

-- #[GET_REVI_POLK_YEAR_ID_BY_NAME]:
SELECT id
FROM ReviPolk_Year
WHERE `name` = ?;

-- #[GET_REVI_POLK_MAKE_ID_BY_NAME]:
SELECT id
FROM ReviPolk_Make
WHERE `name` = ?;

-- #[GET_REVI_POLK_MODELS_BY_YEAR_MAKE_ID]:
SELECT ReviPolk_Vehicle.id as id,
       ReviPolk_Model.`name` as `model`
FROM ReviPolk_Vehicle
JOIN ReviPolk_Model ON ReviPolk_Model.id = ReviPolk_Vehicle.model_id
WHERE ReviPolk_Vehicle.year_id = ?
AND   ReviPolk_Vehicle.make_id = ? ;