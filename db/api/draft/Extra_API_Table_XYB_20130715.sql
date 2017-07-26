DROP TABLE IF EXISTS Emergency_Campaign_Record;
DROP TABLE IF EXISTS Servlet_Activity_Record;
DROP TABLE IF EXISTS System_Emergency_Status;

CREATE TABLE `System_Emergency_Status` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`emergency_status`  tinyint(1) NOT NULL ,
PRIMARY KEY (`id`),
INDEX `emergency_status` (`emergency_status`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Servlet_Activity_Record` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`servlet_name`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`operator`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`task`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`is_succeed`  tinyint(1) NOT NULL ,
`comment`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `servlet_name` (`servlet_name`) USING BTREE ,
INDEX `operator` (`operator`) USING BTREE ,
INDEX `task` (`task`) USING BTREE ,
INDEX `is_succeed` (`is_succeed`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Emergency_Campaign_Record (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`system_emergency_status_id`  int(11) NOT NULL ,
`provider_supplied_id`  int(11) NOT NULL ,
`provider_status_before_paused`  enum('active','paused','deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`provider_status`  enum('active','paused','deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`uploaded_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`is_uploaded`  tinyint(1) NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`system_emergency_status_id`) REFERENCES `system_emergency_status` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
UNIQUE INDEX `id_sequence` (`system_emergency_status_id`, `provider_supplied_id`) USING BTREE ,
INDEX `uploaded_ts` (`uploaded_ts`) USING BTREE ,
INDEX `is_uploaded` (`is_uploaded`) USING BTREE ,
INDEX `provider_supplied_id` (`provider_supplied_id`) USING BTREE ,
INDEX `system_emergency_status_id` (`system_emergency_status_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;
