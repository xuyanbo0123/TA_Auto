/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : All

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2013-08-18 20:41:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for Text_Ad_Template
-- ----------------------------
DROP TABLE IF EXISTS `Text_Ad_Template`;
CREATE TABLE `Text_Ad_Template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `headline` varchar(50) NOT NULL,
  `description1` varchar(60) NOT NULL,
  `description2` varchar(60) DEFAULT NULL,
  `displayUrl` varchar(60) NOT NULL,
  `actionUrl` varchar(100) NOT NULL,
  `priority` int(11) DEFAULT NULL,
  `group` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`headline`,`description1`,`description2`,`displayUrl`,`actionUrl`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8503 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Text_Ad_Template
-- ----------------------------
INSERT INTO `Text_Ad_Template` VALUES ('1', '2013-08-13 02:35:49', '0000-00-00 00:00:00', 'test ##brand## ##state##', 'test ##brand## ##state## ##city##', 'test ##brand## ##state## ##city## ', 'www.test.com', 'http://www.test.com', '1000', 'testGroup');
