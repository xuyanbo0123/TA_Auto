SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `Email_Replacement_Text`
-- ----------------------------
DROP TABLE IF EXISTS `Email_Replacement_Text`;
CREATE TABLE `Email_Replacement_Text` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parameter_name` varchar(255) NOT NULL,
  `replacement_text` varchar(2047) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `parameter_name` (`parameter_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Email_Replacement_Text
-- ----------------------------
INSERT INTO `Email_Replacement_Text` VALUES ('1', 'AUTO_INSURANCE_LOGO_IMG_URL', 'http://www.quotes2compare.com/images/neo/quotes2compare-logo.png');
INSERT INTO `Email_Replacement_Text` VALUES ('2', 'AUTO_INSURANCE_CONTACT_US_URL', 'http://www.quotes2compare.com/contact-us');
INSERT INTO `Email_Replacement_Text` VALUES ('3', 'AUTO_INSURANCE_HOME_PAGE_URL', 'http://www.quotes2compare.com');
INSERT INTO `Email_Replacement_Text` VALUES ('4', 'AUTO_INSURANCE_INSURANCE_LIBRARY_URL', 'http://www.quotes2compare.com/insurance-library');
INSERT INTO `Email_Replacement_Text` VALUES ('5', 'AUTO_INSURANCE_PRIVACY_STATEMENT_URL', 'http://www.quotes2compare.com/privacy-policy');