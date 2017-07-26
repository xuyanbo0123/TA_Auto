SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `Email_From`
-- ----------------------------
DROP TABLE IF EXISTS `Email_From`;
CREATE TABLE `Email_From` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `name` varchar(255) NOT NULL,
  `from_address` varchar(127) NOT NULL,
  `from_text` varchar(127) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`) USING BTREE,
  KEY `created_ts` (`created_ts`) USING BTREE,
  KEY `updated_ts` (`updated_ts`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Email_From
-- ----------------------------
INSERT INTO `Email_From` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'Quotes2Compare Health Insurance', 'support@quotes2compare.com', 'CustomerServiceDepartment');
INSERT INTO `Email_From` VALUES ('2', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'Quotes2Compare Auto Insurance', 'support@quotes2compare.com', 'CustomerServiceDepartment');