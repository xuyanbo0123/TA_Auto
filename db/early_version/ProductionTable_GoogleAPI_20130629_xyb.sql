DROP TABLE IF EXISTS Traffic_Campaign_Budget_Map;
DROP TABLE IF EXISTS Campaign_Budget;

CREATE TABLE `Campaign_Budget` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`traffic_provider_id`  int(11) NOT NULL ,
`provider_supplied_id`  int(11) NULL DEFAULT NULL ,
`name`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`local_status`  enum('active','deleted','unknown') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`provider_status`  enum('active','deleted','unknown', 'pending_upload') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`period`  enum('daily') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`delivery_method`  enum('standard','accelerated') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`amount`  decimal(10,2) NOT NULL ,
`uploaded_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
PRIMARY KEY (`id`),
FOREIGN KEY (`traffic_provider_id`) REFERENCES `Traffic_Provider` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `Campaign_Budget_ibfk_1` (`traffic_provider_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Traffic_Campaign_Budget_Map` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`traffic_campaign_id`  int(11) NOT NULL ,
`campaign_budget_id`  int(11) NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`campaign_budget_id`) REFERENCES `Campaign_Budget` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (`traffic_campaign_id`) REFERENCES `Traffic_Campaign` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `Traffic_Campaign_Budget_Map_ibfk_1` (`traffic_campaign_id`) USING BTREE ,
INDEX `Traffic_Campaign_Budget_Map_ibfk_2` (`campaign_budget_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

