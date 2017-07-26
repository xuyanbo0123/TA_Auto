ALTER TABLE `Click_Out` DROP FOREIGN KEY `Click_Out_ibfk_2`;
ALTER TABLE `Click_Out` DROP COLUMN `click_ad_id`, DROP INDEX `click_ad_id`;

ALTER TABLE `Click_Out` DROP INDEX `click_impression_id`, ADD UNIQUE INDEX `click_impression_id` (`click_impression_id`) USING BTREE;