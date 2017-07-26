DROP TABLE IF EXISTS `Email_Record_Property_Field`;

CREATE TABLE `Email_Record_Property_Field` (
`id`  int(11) NOT NULL ,
`email_record_property_field`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;