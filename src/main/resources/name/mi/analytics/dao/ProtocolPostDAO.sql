-- #[INSERT_INTO_PROTOCOL_POST]:
INSERT INTO Protocol_Post(id, created_ts, updated_ts, arrival_id, hit_type, payload, response)
VALUES (NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?);