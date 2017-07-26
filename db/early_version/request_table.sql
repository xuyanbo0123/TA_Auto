-- change the length of 'raw_request' from 1023 to 4095 by xyb on 20130602.

DROP TABLE IF EXISTS Lead_Request;

CREATE TABLE `Lead_Request` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`raw_request`  varchar(4095) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`request_status`  enum('sold','ping_started','saved','received') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`arrival_id`  int(11) NOT NULL ,
`lead_type_id`  int(11) NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`arrival_id`) REFERENCES `Arrival` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (`lead_type_id`) REFERENCES `Lead_Type` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `arrival_id` (`arrival_id`) USING BTREE ,
INDEX `lead_type_id` (`lead_type_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;
