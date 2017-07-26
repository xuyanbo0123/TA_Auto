DROP TABLE IF EXISTS Email_Program_Step_Rule_Map;
DROP TABLE IF EXISTS Email_Rule;
DROP TABLE IF EXISTS Email_Replacement_Text;

CREATE TABLE `Email_Rule` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`name`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`value`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`),
INDEX `created_ts` (`created_ts`) USING BTREE ,
INDEX `updated_ts` (`updated_ts`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=4
ROW_FORMAT=COMPACT
;

CREATE TABLE `Email_Program_Step_Rule_Map` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`email_program_step_id`  int(11) NOT NULL ,
`email_rule_id`  int(11) NOT NULL ,
`status`  enum('active','paused','closed') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`email_program_step_id`) REFERENCES `email_program_step` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`email_rule_id`) REFERENCES `email_rule` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
UNIQUE INDEX `email_program_step_id` (`email_program_step_id`, `email_rule_id`) USING BTREE ,
INDEX `created_ts` (`created_ts`) USING BTREE ,
INDEX `updated_ts` (`updated_ts`) USING BTREE ,
INDEX `email_rule_id` (`email_rule_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=4
ROW_FORMAT=COMPACT
;

CREATE TABLE `Email_Replacement_Text` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`parameter_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`replacement_text`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`),
UNIQUE INDEX `parameter_name` (`parameter_name`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=7
ROW_FORMAT=COMPACT
;
