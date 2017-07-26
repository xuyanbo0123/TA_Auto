/*
Navicat MySQL Data Transfer

Source Server         : localhost-mimactest
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : Manager

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2014-03-29 18:17:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for System_Variable
-- ----------------------------
DROP TABLE IF EXISTS `System_Variable`;
CREATE TABLE `System_Variable` (
  `name` varchar(63) NOT NULL,
  `value` varchar(63) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of System_Variable
-- ----------------------------
-- INSERT INTO `System_Variable` VALUES ('SITE_NAME', 'Quotes2Compare');
-- INSERT INTO `System_Variable` VALUES ('SITE_NAME', 'FetchTheQuote');
