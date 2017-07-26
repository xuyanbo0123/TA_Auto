-- ----------------------------
-- Table structure for Email_Correction_Extension
-- ----------------------------
DROP TABLE IF EXISTS `Email_Correction_Extension`;
CREATE TABLE `Email_Correction_Extension` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `extension` varchar(511) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Email_Correction_Extension
-- ----------------------------
INSERT INTO `Email_Correction_Extension` VALUES ('1', '');
INSERT INTO `Email_Correction_Extension` VALUES ('2', 'com');

-- ----------------------------
-- Table structure for Email_Correction_Hostname
-- ----------------------------
DROP TABLE IF EXISTS `Email_Correction_Hostname`;
CREATE TABLE `Email_Correction_Hostname` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `host` varchar(511) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Email_Correction_Hostname
-- ----------------------------
INSERT INTO `Email_Correction_Hostname` VALUES ('1', '');
INSERT INTO `Email_Correction_Hostname` VALUES ('2', 'gmail');
INSERT INTO `Email_Correction_Hostname` VALUES ('3', 'yahoo');
INSERT INTO `Email_Correction_Hostname` VALUES ('4', 'hotmail');
INSERT INTO `Email_Correction_Hostname` VALUES ('5', 'msn');

-- ----------------------------
-- Table structure for Email_Correction_Variation
-- ----------------------------
DROP TABLE IF EXISTS `Email_Correction_Variation`;
CREATE TABLE `Email_Correction_Variation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `variation` varchar(127) DEFAULT NULL,
  `variation_type` enum('Extension','Hostname') NOT NULL DEFAULT 'Hostname',
  `hostname_id` bigint(20) NOT NULL,
  `extension_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`,`hostname_id`,`extension_id`),
  UNIQUE KEY `variation_2` (`variation`,`variation_type`,`hostname_id`,`extension_id`) USING BTREE,
  KEY `hostname_id` (`hostname_id`),
  KEY `extension_id` (`extension_id`),
  KEY `variation_type` (`variation_type`) USING BTREE,
  KEY `variation` (`variation`) USING BTREE,
  CONSTRAINT `email_correction_variation_ibfk_1` FOREIGN KEY (`hostname_id`) REFERENCES `Email_Correction_Hostname` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `email_correction_variation_ibfk_2` FOREIGN KEY (`extension_id`) REFERENCES `Email_Correction_Extension` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Email_Correction_Variation
-- ----------------------------
INSERT INTO `Email_Correction_Variation` VALUES ('6', 'gailm', 'Hostname', '2', '1');
INSERT INTO `Email_Correction_Variation` VALUES ('3', 'gamil', 'Hostname', '2', '1');
INSERT INTO `Email_Correction_Variation` VALUES ('1', 'gmai', 'Hostname', '2', '1');
INSERT INTO `Email_Correction_Variation` VALUES ('13', 'gmaill', 'Hostname', '2', '1');
INSERT INTO `Email_Correction_Variation` VALUES ('2', 'gmal', 'Hostname', '2', '1');
INSERT INTO `Email_Correction_Variation` VALUES ('10', 'gmali', 'Hostname', '2', '1');
INSERT INTO `Email_Correction_Variation` VALUES ('15', 'gmil', 'Hostname', '2', '1');
INSERT INTO `Email_Correction_Variation` VALUES ('11', 'homail', 'Hostname', '4', '1');
INSERT INTO `Email_Correction_Variation` VALUES ('12', 'hotmai', 'Hostname', '4', '1');
INSERT INTO `Email_Correction_Variation` VALUES ('9', 'htmail', 'Hostname', '4', '1');
INSERT INTO `Email_Correction_Variation` VALUES ('7', 'jmail', 'Hostname', '2', '1');
INSERT INTO `Email_Correction_Variation` VALUES ('14', 'msm', 'Hostname', '5', '1');
INSERT INTO `Email_Correction_Variation` VALUES ('5', 'yah00', 'Hostname', '3', '1');
INSERT INTO `Email_Correction_Variation` VALUES ('4', 'yahho', 'Hostname', '3', '1');
INSERT INTO `Email_Correction_Variation` VALUES ('8', 'yhoo', 'Hostname', '3', '1');
