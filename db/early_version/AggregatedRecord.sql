DROP TABLE IF EXISTS `Aggregated_Record`;

CREATE TABLE `Aggregated_Record` (
  `id`  int(11) NOT NULL AUTO_INCREMENT ,
  `created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `aggregated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  `type`  enum('daily','weekly','monthly') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
  PRIMARY KEY (`id`),
  INDEX `aggregated_ts` (`aggregated_ts`) USING BTREE ,
  INDEX `type` (`type`) USING BTREE
  )
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=3
  ROW_FORMAT=COMPACT
  ;