ALTER TABLE `Redirect` ADD COLUMN `position` int(11) NULL AFTER `token`, ADD INDEX `position` (`position`) USING BTREE ;

ALTER TABLE `Redirect_Action`
CHANGE COLUMN `action_position` `impression_zone`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `action_ts`,
MODIFY COLUMN `click_ad_position`  int(11) NULL DEFAULT NULL AFTER `impression_zone`,
ADD INDEX `impression_zone` (`impression_zone`) USING BTREE ,
ADD INDEX `click_ad_position` (`click_ad_position`) USING BTREE ;

ALTER TABLE `Redirect`
CHANGE COLUMN `position` `click_ad_position`  int(11) NULL DEFAULT NULL AFTER `token`;

ALTER TABLE `Redirect` DROP INDEX `position`, ADD INDEX `click_ad_position` (`click_ad_position`) USING BTREE ;