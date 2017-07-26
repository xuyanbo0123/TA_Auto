DROP TABLE IF EXISTS `Keyword_Carrier_Map`;

CREATE TABLE `Keyword_Carrier_Map` (
  `id`  bigint(20) NOT NULL AUTO_INCREMENT ,
  `created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  `keyword`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `carrier_list_id`  bigint(20) NOT NULL ,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`carrier_list_id`) REFERENCES `Carrier_List` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  INDEX `Keyword_Carrier_Map_ibfk_1` (`carrier_list_id`) USING BTREE
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=1
  ROW_FORMAT=COMPACT
;

-- ----------------------------
-- Records of Keyword_Carrier_Map
-- ----------------------------
INSERT INTO `Keyword_Carrier_Map` VALUES ('1', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'bristol west', '38');
INSERT INTO `Keyword_Carrier_Map` VALUES ('2', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'eastwood insurance', '39');
INSERT INTO `Keyword_Carrier_Map` VALUES ('3', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'erie insurance', '12');
INSERT INTO `Keyword_Carrier_Map` VALUES ('4', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'insurance liberty', '19');
INSERT INTO `Keyword_Carrier_Map` VALUES ('5', '2013-08-08 00:00:00', '0000-00-00 00:00:00', '1800 general', '31');
INSERT INTO `Keyword_Carrier_Map` VALUES ('6', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'ameca', '7');
INSERT INTO `Keyword_Carrier_Map` VALUES ('7', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'amica', '7');
INSERT INTO `Keyword_Carrier_Map` VALUES ('8', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'amica insurance', '7');
INSERT INTO `Keyword_Carrier_Map` VALUES ('9', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'amico insurance', '7');
INSERT INTO `Keyword_Carrier_Map` VALUES ('10', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'commerce insurance', '9');
INSERT INTO `Keyword_Carrier_Map` VALUES ('11', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'countrywide insurance', '10');
INSERT INTO `Keyword_Carrier_Map` VALUES ('12', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'encompass insurance', '11');
INSERT INTO `Keyword_Carrier_Map` VALUES ('13', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'farm bureau car insurance', '40');
INSERT INTO `Keyword_Carrier_Map` VALUES ('14', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'general insurance company of america', '31');
INSERT INTO `Keyword_Carrier_Map` VALUES ('15', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'liberty auto insurance', '19');
INSERT INTO `Keyword_Carrier_Map` VALUES ('16', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'liberty insurance', '19');
INSERT INTO `Keyword_Carrier_Map` VALUES ('17', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'nationwide auto insurance', '22');
INSERT INTO `Keyword_Carrier_Map` VALUES ('18', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'the general', '31');
INSERT INTO `Keyword_Carrier_Map` VALUES ('19', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'the general insurance', '31');
INSERT INTO `Keyword_Carrier_Map` VALUES ('20', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'us agencies', '35');
INSERT INTO `Keyword_Carrier_Map` VALUES ('21', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'usa insurance', '35');
INSERT INTO `Keyword_Carrier_Map` VALUES ('22', '2013-08-08 00:00:00', '0000-00-00 00:00:00', 'usaa.com legion', '35');