CREATE TABLE `AB_Test_Property` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`ab_test_id`  int(11) NOT NULL DEFAULT 0 ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`description`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`ab_test_id`) REFERENCES `AB_Test` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `ab_test_property_ab_test_id_fkey` (`ab_test_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE `AB_Test_Property_Value` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`ab_test_property_id`  int(11) NOT NULL DEFAULT 0 ,
`value`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`description`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`ab_test_property_id`) REFERENCES `AB_Test_Property` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `ab_test_property_value_ab_test_property_id_fkey` (`ab_test_property_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

