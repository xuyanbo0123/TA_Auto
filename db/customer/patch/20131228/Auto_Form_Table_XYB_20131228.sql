ALTER TABLE `Driver`
    ADD COLUMN `marital_status` enum('_SINGLE', '_MARRIED', '_DIVORCED', '_WIDOWED', '_OTHER') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `is_married`;
ALTER TABLE `Driver`
    ADD COLUMN `lic_status` enum('_ACTIVE', '_EXPIRED', '_SUSPENDED', '_PERMIT') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `is_lic_active`;

UPDATE `Driver`
SET `marital_status` = '_MARRIED' WHERE `is_married` = true;

UPDATE `Driver`
SET `marital_status` = '_SINGLE' WHERE `is_married` = false;

UPDATE `Driver`
SET `lic_status` = '_ACTIVE' WHERE `is_lic_active` = true;

UPDATE `Driver`
SET `lic_status` = '_SUSPENDED' WHERE `is_lic_active` = false;

ALTER TABLE `Driver` DROP COLUMN `is_married`, DROP COLUMN `is_lic_active`;

ALTER TABLE `Auto_Form`
ADD COLUMN `expire_date` datetime AFTER `expire_time`;
ALTER TABLE `Auto_Form`
ADD COLUMN `parking` enum('_GARAGE', '_CARPORT', '_DRIVEWAY', '_PARKING_LOT', '_STREET') AFTER `have_garage`;

UPDATE `Auto_Form`
SET `expire_date` = ADDDATE(created_ts,31) WHERE `expire_time` = '_NOT_SURE';

UPDATE `Auto_Form`
SET `expire_date` = ADDDATE(created_ts,2) WHERE `expire_time` = '_A_FEW_DAYS';

UPDATE `Auto_Form`
SET `expire_date` = ADDDATE(created_ts,14) WHERE `expire_time` = '_2_WEEKS';

UPDATE `Auto_Form`
SET `expire_date` = ADDDATE(created_ts,30) WHERE `expire_time` = '_1_MONTH';

UPDATE `Auto_Form`
SET `expire_date` = ADDDATE(created_ts,60) WHERE `expire_time` = '_2_MONTHS';

UPDATE `Auto_Form`
SET `expire_date` = ADDDATE(created_ts,90) WHERE `expire_time` = '_3_MONTHS';

UPDATE `Auto_Form`
SET `expire_date` = ADDDATE(created_ts,180) WHERE `expire_time` = '_4_TO_6_MONTHS';

UPDATE `Auto_Form`
SET `expire_date` = ADDDATE(created_ts,200) WHERE `expire_time` = '_MORE_THAN_6_MONTHS';

UPDATE `Auto_Form`
SET `parking` = '_GARAGE' WHERE `have_garage` = true;

UPDATE `Auto_Form`
SET `parking` = '_STREET' WHERE `have_garage` = false;

ALTER TABLE `Auto_Form` DROP COLUMN `have_garage`, DROP COLUMN `expire_time`;
