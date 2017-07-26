-- #[GET_ADDRESS_VERIFY_MELISSA_DATA_RECORDS_BY_DATE_AND_COUNT_LT_WITH_ACCOUNT_INFO]:
SELECT mda.username AS musername, mda.`password` AS mpassword, mda.id AS mid, mdr.id AS mdrid
FROM Address_Verify_Melissa_Data_Records AS mdr, Address_Verify_Melissa_Data_Accounts AS mda
WHERE mdr.melissa_data_account_id = mda.id
AND mdr.`date` = ?
AND mdr.`count` < ?
ORDER BY mdr.id ASC
;

-- #[GET_ADDRESS_VERIFY_MELISSA_DATA_RECORDS_BY_DATE]:
SELECT mdr.id AS mid
FROM Address_Verify_Melissa_Data_Records AS mdr
WHERE mdr.`date` = ?
ORDER BY mdr.id ASC
;

-- #[INSERT_ALL_ADDRESS_VERIFY_MELISSA_DATA_RECORDS]:
INSERT INTO Address_Verify_Melissa_Data_Records (`date`, melissa_data_account_id, `count`)
SELECT ?, Address_Verify_Melissa_Data_Accounts.id, 0
FROM Address_Verify_Melissa_Data_Accounts
;

-- #[INSERT_OR_UPDATE_ADDRESS_VERIFY_MELISSA_DATA_RECORDS]:
INSERT INTO Address_Verify_Melissa_Data_Records(id, `date`, melissa_data_account_id, `count`)
VALUES(
NULL,
?,
?,
?
)
ON DUPLICATE KEY
UPDATE `count` = `count` + 1
;

-- #[UPDATE_ADDRESS_VERIFY_MELISSA_DATA_RECORDS_COUNT_BY_ID]:
UPDATE Address_Verify_Melissa_Data_Records SET `count` = ? WHERE id = ?;