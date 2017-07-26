-- #[INSERT_INTO_INCIDENT]:
INSERT INTO Incident(id, created_ts, updated_ts, auto_form_id, `incident_type`, driver_id, estimated_date, description, damage, amount_paid, is_at_fault, happened_state)
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
    ?
  );

-- #[GET_INCIDENT_BY_ID]:
SELECT id, created_ts, updated_ts, auto_form_id, `incident_type`, driver_id, estimated_date, description, damage, amount_paid, is_at_fault, happened_state
FROM Incident
WHERE id = ?;

-- #[GET_INCIDENTS_BY_AUTO_FORM_ID]:
SELECT id, created_ts, updated_ts, auto_form_id, `incident_type`, driver_id, estimated_date, description, damage, amount_paid, is_at_fault, happened_state
FROM Incident
WHERE auto_form_id = ?;
