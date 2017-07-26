ALTER TABLE `Lead_Sell`
MODIFY COLUMN `sell_state` enum('ERROR','SOLD','REJECTED','EXCEPTION','TIMEOUT') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `buyer_account_id`;

ALTER TABLE `Lead_Sell`
DROP COLUMN `amount`,
DROP COLUMN `adjusted_amount`,
ADD INDEX `sell_state` (`sell_state`) USING BTREE ;