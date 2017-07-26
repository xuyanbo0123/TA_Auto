--add 'UNIQUE INDEX url_name (url_name)' into Arrival_Tracking_Parameters by xyb on 20130615

DROP TABLE IF EXISTS `Arrival_Tracking_Parameters`;

CREATE TABLE `Arrival_Tracking_Parameters` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`url_name`  varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`db_name`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`comment`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
PRIMARY KEY (`id`, `url_name`, `db_name`),
UNIQUE INDEX url_name (url_name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;
