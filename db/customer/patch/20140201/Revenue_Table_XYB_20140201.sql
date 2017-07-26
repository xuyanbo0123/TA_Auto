DROP TABLE IF EXISTS `Revenue`;

CREATE TABLE `Revenue` (
  `id`  bigint(20) NOT NULL AUTO_INCREMENT ,
  `created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  `type`  enum('_CLICK','_LEAD') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `source`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `arrival_uuid`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `token`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `amount`  decimal(9,3) NULL DEFAULT NULL ,
  `transaction_id`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `token` (`token`) USING BTREE
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=1
  ROW_FORMAT=COMPACT
;