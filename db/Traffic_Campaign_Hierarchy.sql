/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : All

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2013-08-20 14:42:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for Traffic_Campaign_Hierarchy
-- ----------------------------
DROP TABLE IF EXISTS `Traffic_Campaign_Hierarchy`;
CREATE TABLE `Traffic_Campaign_Hierarchy` (
  `traffic_campaign_id` bigint(20) NOT NULL,
  `group_name` varchar(50) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `level` enum('') NOT NULL,
  `criteria_id` bigint(20) NOT NULL,
  PRIMARY KEY (`traffic_campaign_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Traffic_Campaign_Hierarchy1
-- ----------------------------
