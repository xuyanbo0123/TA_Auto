DROP TABLE IF EXISTS `Health_Form`;
CREATE TABLE `Health_Form` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`arrival_id`  int(11) NOT NULL ,
`user_id`  int(11) DEFAULT NULL ,
`gender`  enum('Female','Male') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`birthdate`  date DEFAULT NULL ,
`height_ft`  int(11) DEFAULT NULL ,
`height_in`  int(11) DEFAULT NULL ,
`height_cm`  decimal(6,2) DEFAULT NULL ,
`weight_lbs`  decimal(6,2) DEFAULT NULL ,
`weight_kg`  decimal(6,2) DEFAULT NULL ,
`tobacco`  tinyint(1) NOT NULL DEFAULT 0,
`marital`  enum('Single','Married','Divorced','Widowed') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`spouse_inc`  tinyint(1) NOT NULL DEFAULT 0 ,
`spouse_first_name`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`spouse_last_name`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`spouse_gender`  enum('Female','Male') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`spouse_birthdate`  date DEFAULT NULL ,
`spouse_height_ft`  int(11) DEFAULT NULL ,
`spouse_height_in`  int(11) DEFAULT NULL ,
`spouse_height_cm`  decimal (6,2) DEFAULT NULL ,
`spouse_weight_lbs`  decimal (6,2) DEFAULT NULL ,
`spouse_weight_kg`  decimal (6,2) DEFAULT NULL ,
`spouse_tobacco`  tinyint(1) NOT NULL DEFAULT 0 ,
`children`  int(11)  DEFAULT 0 ,
`pregnant`  tinyint(1) NOT NULL DEFAULT 0 ,
`major_treatment`  tinyint(1) NOT NULL DEFAULT 0 ,
`hospitalized`  tinyint(1) NOT NULL DEFAULT 0 ,
`deny`  tinyint(1) NOT NULL DEFAULT 0 ,
`self_employ`  tinyint(1) NOT NULL DEFAULT 0 ,
`current_coverage`  tinyint(1) NOT NULL DEFAULT 0 ,
`prescription`  tinyint(1) NOT NULL DEFAULT 0 ,
`health_condition`  tinyint(1) NOT NULL DEFAULT 0 ,
`current_provider`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`prescription_list`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`health_condition_list`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`first_name`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`last_name`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`street`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`city`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`state`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`zipcode`  varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`day_phone`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`evening_phone`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`email`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
PRIMARY KEY (`id`),
KEY (`zipcode`),
KEY (`state`),
KEY (`city`),
KEY (`street`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

