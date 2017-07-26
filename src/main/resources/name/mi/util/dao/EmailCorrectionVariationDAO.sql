-- #[GET_EMAIL_CORRECTION_VARIATION_DETAILS_BY_HOSTNAME_VARIATION]:
SELECT ecv.id AS id, ecv.variation AS variation, ecv.variation_type AS variation_type, ech.id AS hostname_id, ech.`host` AS hostname, ece.id AS extension_id, ece.extension AS extension
FROM Email_Correction_Variation AS ecv, Email_Correction_Hostname AS ech, Email_Correction_Extension AS ece
WHERE ecv.hostname_id = ech.id
AND ecv.extension_id = ece.id
AND ecv.`variation_type` = "Hostname"
AND ecv.variation = ?
ORDER BY `extension` ASC
;