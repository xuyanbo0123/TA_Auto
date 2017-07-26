/*
Navicat MySQL Data Transfer

Source Server         : Amazon_Master_Box
Source Server Version : 50531
Source Host           : localhost:3306
Source Database       : All

Target Server Type    : MYSQL
Target Server Version : 50531
File Encoding         : 65001

Date: 2014-04-12 00:49:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for Mail_Chimp_Information
-- ----------------------------
DROP TABLE IF EXISTS `Mail_Chimp_Information`;
CREATE TABLE `Mail_Chimp_Information` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `email_address` varchar(127) NOT NULL,
  `email_domain` varchar(63) NOT NULL,
  `first_name` varchar(63) NOT NULL,
  `last_name` varchar(63) NOT NULL,
  `lead_request_id` bigint(20) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `email_domain` (`email_domain`) USING BTREE,
  KEY `lead_request_id` (`lead_request_id`) USING BTREE,
  CONSTRAINT `mailchimp_information_ibfk_1` FOREIGN KEY (`lead_request_id`) REFERENCES `Lead_Request` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24307 DEFAULT CHARSET=utf8;
