-- #[GET_ZIP_CODE_BY_IP]:
SELECT zip_code
FROM ip_map
WHERE ip_from <= ? AND ? <= ip_to;