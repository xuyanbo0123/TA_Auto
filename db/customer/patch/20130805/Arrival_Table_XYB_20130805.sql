ALTER TABLE `Arrival`
ADD COLUMN `gclid`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `browser`,
ADD INDEX `gclid` (`gclid`) USING BTREE ;

ALTER TABLE `Arrival`
ADD COLUMN `conversion_count`  int(11) NULL AFTER `gclid`,
ADD INDEX `conversion_count` (`conversion_count`) USING BTREE ;