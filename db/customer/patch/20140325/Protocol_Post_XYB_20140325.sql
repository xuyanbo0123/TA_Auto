/*
Navicat MySQL Data Transfer

Source Server         : localhost-mimactest
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : All

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2014-03-25 23:41:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for Protocol_Post
-- ----------------------------
DROP TABLE IF EXISTS `Protocol_Post`;
CREATE TABLE `Protocol_Post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `arrival_id` bigint(20) NOT NULL,
  `hit_type` enum('_TRANSACTION','_EVENT') NOT NULL,
  `payload` varchar(2047) DEFAULT NULL,
  `response` varchar(2047) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `arrival_id` (`arrival_id`),
  KEY `hit_type` (`hit_type`),
  CONSTRAINT `protocol_post_ibfk_1` FOREIGN KEY (`arrival_id`) REFERENCES `Arrival` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
