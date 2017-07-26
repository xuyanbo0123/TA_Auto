DROP TABLE IF EXISTS Buyer_Account_Config;

CREATE TABLE `Buyer_Account_Config` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`buyer_account_id`  bigint(20) NOT NULL ,
`type`  enum('LEAD_RULE','CLICK_RULE') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`rule`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`send_to`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`limit`  int(11) NOT NULL ,
`count`  int(11) NOT NULL ,
`priority`  int(11) NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`buyer_account_id`) REFERENCES `Buyer_Account` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `buyer_account_id` (`buyer_account_id`) USING BTREE ,
INDEX `type` (`type`) USING BTREE ,
INDEX `limit` (`limit`) USING BTREE ,
INDEX `count` (`count`) USING BTREE ,
INDEX `priority` (`priority`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;
