DROP TABLE IF EXISTS `Campaign_Geo_Map`;

CREATE TABLE `Campaign_Geo_Map` (
  `id`  bigint(20) NOT NULL AUTO_INCREMENT ,
  `created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  `traffic_campaign_id`  bigint(20) NOT NULL ,
  `city`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `state`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `state_abbr`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `traffic_campaign_id` (`traffic_campaign_id`) USING BTREE
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=1
  ROW_FORMAT=COMPACT
;

-- ----------------------------
-- Records of Campaign_Geo_Map
-- ----------------------------
INSERT INTO `Campaign_Geo_Map` VALUES ('1', '2013-08-08 00:00:00', '0000-00-00 00:00:00', '55', null, 'Massachusetts', 'MA');
INSERT INTO `Campaign_Geo_Map` VALUES ('2', '2013-08-08 00:00:00', '0000-00-00 00:00:00', '69', null, 'Texas', 'TX');
INSERT INTO `Campaign_Geo_Map` VALUES ('3', '2013-08-08 00:00:00', '0000-00-00 00:00:00', '71', null, 'Tennessee', 'TN');