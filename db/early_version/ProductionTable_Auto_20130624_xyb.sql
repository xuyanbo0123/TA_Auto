DROP TABLE IF EXISTS Driver;
DROP TABLE IF EXISTS Vehicle;
DROP TABLE IF EXISTS Violation;
DROP TABLE IF EXISTS Claim;
DROP TABLE IF EXISTS Auto_Form;


CREATE TABLE `Auto_Form` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`arrival_id`  int(11) NOT NULL ,
`user_id`  int(11) NULL DEFAULT NULL ,
`desired_amount`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`if_insured`  tinyint(1) NULL DEFAULT 1 ,
`current_company`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`exp_date`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`wanted_year`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`current_body_liability`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`zipcode`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`street`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`city`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`state`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`email`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`telephone`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`arrival_id`) REFERENCES `Arrival` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `Auto_Form_ibfk_1` (`arrival_id`) USING BTREE ,
INDEX `zipcode` (`zipcode`) USING BTREE ,
INDEX `state` (`state`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Claim` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`auto_form_id`  int(11) NOT NULL ,
`incident_type`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`incident_date`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`paid`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`auto_form_id`) REFERENCES `Auto_Form` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `Claim_ibfk_1` (`auto_form_id`) USING BTREE ,
INDEX `incident_type` (`incident_type`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Violation` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`auto_form_id`  int(11) NOT NULL ,
`violation_type`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`violation_date`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`paid`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`auto_form_id`) REFERENCES `Auto_Form` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `Violation_ibfk_1` (`auto_form_id`) USING BTREE ,
INDEX `violation_type` (`violation_type`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Vehicle` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`auto_form_id`  int(11) NOT NULL ,
`year`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`make`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`model`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ownership`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`primary_use`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`annual_mileage`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`night_parking`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`auto_form_id`) REFERENCES `Auto_Form` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `Vehicle_ibfk_1` (`auto_form_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Driver` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`auto_form_id`  int(11) NOT NULL ,
`driver_type`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`first_name`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`last_name`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`birthday`  date NULL DEFAULT NULL ,
`gender`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`marital_status`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`occupation`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`education_level`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`residence`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`credit_evaluation`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`age_first_licensed`  int(11) NULL DEFAULT NULL ,
`if_suspended`  tinyint(1) NULL DEFAULT 0 ,
`if_sr22`  tinyint(1) NULL DEFAULT 0 ,
PRIMARY KEY (`id`),
FOREIGN KEY (`auto_form_id`) REFERENCES `Auto_Form` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `Driver_ibfk_1` (`auto_form_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;
