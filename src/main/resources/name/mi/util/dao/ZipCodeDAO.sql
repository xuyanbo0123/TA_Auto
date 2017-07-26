-- #[GET_CITY_BY_ZIP_CODE]:
SELECT City
FROM Zip_Code
WHERE Zipcode = ?;

-- #[GET_STATE_BY_ZIP_CODE]:
SELECT State
FROM Zip_Code
WHERE Zipcode = ?;