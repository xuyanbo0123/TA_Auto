/*
Navicat MySQL Data Transfer

Source Server         : 144
Source Server Version : 50531
Source Host           : 192.168.1.144:3306
Source Database       : All

Target Server Type    : MYSQL
Target Server Version : 50531
File Encoding         : 65001

Date: 2013-06-30 19:07:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `QDB_Current_QSet`
-- ----------------------------
DROP TABLE IF EXISTS `QDB_Current_QSet`;
CREATE TABLE `QDB_Current_QSet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_id` int(11) DEFAULT NULL,
  `qset` varchar(511) DEFAULT NULL,
  `status` varchar(511) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `state` varchar(127) DEFAULT NULL,
  `elapsed_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of QDB_Current_QSet
-- ----------------------------

-- ----------------------------
-- Table structure for `QDB_Record`
-- ----------------------------
DROP TABLE IF EXISTS `QDB_Record`;
CREATE TABLE `QDB_Record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `user_answer` int(11) DEFAULT NULL,
  `is_correct` tinyint(1) DEFAULT NULL,
  `elapsed_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`),
  CONSTRAINT `question_id` FOREIGN KEY (`question_id`) REFERENCES `QDB_Questions` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of QDB_Record
-- ----------------------------
