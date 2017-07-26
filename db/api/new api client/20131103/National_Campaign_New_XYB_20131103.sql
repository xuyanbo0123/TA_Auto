/*
Navicat MySQL Data Transfer

Source Server         : 144
Source Server Version : 50531
Source Host           : 192.168.1.144:3306
Source Database       : All

Target Server Type    : MYSQL
Target Server Version : 50531
File Encoding         : 65001

Date: 2013-11-04 22:52:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for Ad_Group
-- ----------------------------
DROP TABLE IF EXISTS `Ad_Group`;
CREATE TABLE `Ad_Group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `traffic_campaign_id` bigint(20) NOT NULL,
  `name` varchar(127) NOT NULL,
  `local_status` enum('enabled','paused','deleted') NOT NULL,
  `provider_status` enum('enabled','paused','deleted') DEFAULT NULL,
  `provider_supplied_id` bigint(20) DEFAULT NULL,
  `uploaded_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_uploaded` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `provider_supplied_id` (`provider_supplied_id`) USING BTREE,
  KEY `created_ts` (`created_ts`) USING BTREE,
  KEY `updated_ts` (`updated_ts`) USING BTREE,
  KEY `uploaded_ts` (`uploaded_ts`) USING BTREE,
  KEY `is_uploaded` (`is_uploaded`) USING BTREE,
  KEY `traffic_campaign_id` (`traffic_campaign_id`) USING BTREE,
  CONSTRAINT `Ad_Group_ibfk_1` FOREIGN KEY (`traffic_campaign_id`) REFERENCES `Traffic_Campaign` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4375 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Ad_Group
-- ----------------------------
INSERT INTO `Ad_Group` VALUES ('1', '2013-08-01 00:00:00', '2013-08-19 20:00:05', '1', 'acceptance_insurance', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('2', '2013-08-01 00:00:00', '2013-08-19 20:00:05', '1', 'access_insurance', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('3', '2013-08-01 00:00:00', '2013-08-19 20:00:05', '1', 'adriana_insurance', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('4', '2013-08-01 00:00:00', '2013-08-19 20:00:05', '1', 'bristol_west', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('5', '2013-08-01 00:00:00', '2013-08-19 20:00:05', '1', 'cotton_state_insurance', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('6', '2013-08-01 00:00:00', '2013-08-19 20:00:05', '1', 'dairyland_auto_insurance', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('7', '2013-08-01 00:00:00', '2013-08-19 20:00:05', '1', 'eastwood_insurance', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('8', '2013-08-01 00:00:00', '2013-08-19 20:00:05', '1', 'erie_insurance', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('9', '2013-08-01 00:00:00', '2013-08-19 20:00:05', '1', 'insurance_liberty', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('10', '2013-08-01 00:00:00', '2013-08-19 20:00:05', '1', 'look_insurance', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('22', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', '1800_general.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('23', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'acceptance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('24', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'acceptance_quotes.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('25', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'aggressive_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('26', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'ameca.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('27', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'amica.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('28', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'amica_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('29', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'amico_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('30', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'commerce_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('31', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'cosco_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('32', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'countrywide_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('33', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'dairyland_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('34', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'dairyland_car_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('35', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'del_toro_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('36', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'encompass_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('37', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'ensure_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('38', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'estrella_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('39', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'farm_bureau_car_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('40', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'frankenmuth_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('41', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'fred_loya.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('42', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'fred_loya_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('43', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'freeway_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('44', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'gary_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('45', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'general_insurance_company_of_america.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('46', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'insure_one.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('47', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'katz_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('48', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'la_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('49', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'liberty_auto_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('50', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'liberty_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('51', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'loya_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('52', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'meemic.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('53', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'muncie_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('54', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'nationwide_auto_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('55', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'ocean_harbor_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('56', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'omni_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('57', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'premier_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('58', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'the_general.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('59', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'the_general_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('60', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'us_agencies.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('61', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'usa_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('62', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'usaa.com_legion.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('63', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'westfield_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('64', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'bristol_west.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('65', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'insurance_liberty.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('66', '2013-08-07 00:00:00', '2013-08-19 20:00:05', '1', 'dairyland_auto_insurance.20130807', 'enabled', null, null, '0000-00-00 00:00:00', '0');

-- ----------------------------
-- Table structure for Ad_Group_Ad
-- ----------------------------
DROP TABLE IF EXISTS `Ad_Group_Ad`;
CREATE TABLE `Ad_Group_Ad` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ad_group_id` bigint(20) NOT NULL,
  `ad_id` bigint(20) NOT NULL,
  `provider_supplied_id` bigint(20) DEFAULT NULL,
  `uploaded_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `local_status` enum('enabled','paused','disabled') NOT NULL,
  `provider_status` enum('enabled','paused','disabled') DEFAULT NULL,
  `approval_status` enum('approved','disapproved','family_safe','non_family_safe','porn','unchecked','unknown') DEFAULT NULL,
  `is_uploaded` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`ad_group_id`,`provider_supplied_id`) USING BTREE,
  KEY `is_uploaded` (`is_uploaded`) USING BTREE,
  KEY `ad_id` (`ad_id`) USING BTREE,
  KEY `ad_group_id_ad_id` (`ad_group_id`,`ad_id`) USING BTREE,
  KEY `uploaded_ts` (`uploaded_ts`) USING BTREE,
  KEY `ad_group_id` (`ad_group_id`) USING BTREE,
  CONSTRAINT `Ad_Group_Ad_ibfk_1` FOREIGN KEY (`ad_id`) REFERENCES `Text_Ad` (`id`),
  CONSTRAINT `Ad_Group_Ad_ibfk_2` FOREIGN KEY (`ad_group_id`) REFERENCES `Ad_Group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9513 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Ad_Group_Ad
-- ----------------------------
INSERT INTO `Ad_Group_Ad` VALUES ('1', '2013-08-01 00:00:00', '2013-08-19 20:00:15', '1', '1', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('2', '2013-08-01 00:00:00', '2013-08-19 20:00:15', '2', '2', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('3', '2013-08-01 00:00:00', '2013-08-19 20:00:15', '3', '3', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('4', '2013-08-01 00:00:00', '2013-08-19 20:00:15', '4', '4', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('5', '2013-08-01 00:00:00', '2013-08-19 20:00:15', '5', '5', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('6', '2013-08-01 00:00:00', '2013-08-19 20:00:16', '6', '4', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('7', '2013-08-01 00:00:00', '2013-08-19 20:00:16', '7', '6', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('8', '2013-08-01 00:00:00', '2013-08-19 20:00:16', '8', '7', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('9', '2013-08-01 00:00:00', '2013-08-19 20:00:16', '9', '4', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('10', '2013-08-01 00:00:00', '2013-08-19 20:00:16', '10', '8', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('22', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '22', '20', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('23', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '23', '21', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('24', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '24', '21', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('25', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '25', '22', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('30', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '30', '24', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('32', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '32', '26', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('33', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '33', '27', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('34', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '34', '27', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('35', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '35', '28', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('37', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '37', '30', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('38', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '38', '31', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('39', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '39', '32', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('40', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '40', '33', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('41', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '41', '34', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('42', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '42', '34', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('43', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '43', '35', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('44', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '44', '36', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('45', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '45', '20', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('46', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '46', '37', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('47', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '47', '38', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('48', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '48', '39', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('49', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '49', '40', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('50', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '50', '40', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('51', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '51', '41', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('52', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '52', '42', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('53', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '53', '43', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('54', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '54', '44', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('55', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '55', '45', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('56', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '56', '46', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('57', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '57', '47', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('58', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '58', '20', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('59', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '59', '20', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('60', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '60', '48', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('61', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '61', '49', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('62', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '62', '50', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('63', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '63', '51', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('64', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '64', '52', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('65', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '65', '40', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('66', '2013-08-07 00:00:00', '2013-08-19 20:00:16', '66', '27', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('224', '2013-08-07 21:55:24', '2013-08-19 20:00:16', '8', '176', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('625', '2013-08-08 16:42:16', '2013-08-19 20:00:15', '1', '577', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('650', '2013-08-08 16:42:16', '2013-08-19 20:00:16', '23', '602', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('654', '2013-08-08 16:42:16', '2013-08-19 20:00:16', '26', '606', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('656', '2013-08-08 16:42:16', '2013-08-19 20:00:16', '27', '606', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('657', '2013-08-08 16:42:16', '2013-08-19 20:00:16', '27', '609', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('659', '2013-08-08 16:42:16', '2013-08-19 20:00:16', '28', '606', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('661', '2013-08-08 16:42:16', '2013-08-19 20:00:16', '29', '606', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('664', '2013-08-08 16:42:16', '2013-08-19 20:00:16', '31', '616', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('670', '2013-08-08 16:42:16', '2013-08-19 20:00:16', '36', '622', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('689', '2013-08-08 16:42:16', '2013-08-19 20:00:16', '54', '641', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('690', '2013-08-08 16:42:16', '2013-08-19 20:00:16', '54', '642', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('691', '2013-08-08 16:42:16', '2013-08-19 20:00:16', '54', '643', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('696', '2013-08-08 16:42:16', '2013-08-19 20:00:16', '58', '648', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('700', '2013-08-08 16:42:16', '2013-08-19 20:00:16', '61', '652', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');

-- ----------------------------
-- Table structure for Ad_Group_Keyword
-- ----------------------------
DROP TABLE IF EXISTS `Ad_Group_Keyword`;
CREATE TABLE `Ad_Group_Keyword` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ad_group_id` bigint(20) NOT NULL,
  `keyword_id` bigint(20) NOT NULL,
  `match_type` enum('exact','broad','phrase') NOT NULL,
  `provider_supplied_id` bigint(20) DEFAULT NULL,
  `uploaded_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `local_status` enum('active','paused','deleted') DEFAULT NULL,
  `provider_status` enum('active','paused','deleted') DEFAULT NULL,
  `serving_status` enum('eligible','rarely_served') DEFAULT NULL,
  `approval_status` enum('approved','pending_review','under_review','disapproved') DEFAULT NULL,
  `criterion_use` enum('biddable','negative') NOT NULL,
  `bid_type` enum('cpc') DEFAULT NULL,
  `bid_amount` decimal(8,3) DEFAULT NULL,
  `is_uploaded` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`ad_group_id`,`provider_supplied_id`) USING BTREE,
  KEY `uploaded_ts` (`uploaded_ts`) USING BTREE,
  KEY `is_uploaded` (`is_uploaded`) USING BTREE,
  KEY `keyword_id` (`keyword_id`) USING BTREE,
  KEY `ad_group_id_keyword_id` (`ad_group_id`,`keyword_id`) USING BTREE,
  KEY `ad_group_id` (`ad_group_id`) USING BTREE,
  CONSTRAINT `Ad_Group_Keyword_ibfk_1` FOREIGN KEY (`keyword_id`) REFERENCES `Keyword` (`id`),
  CONSTRAINT `Ad_Group_Keyword_ibfk_2` FOREIGN KEY (`ad_group_id`) REFERENCES `Ad_Group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6793 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Ad_Group_Keyword
-- ----------------------------
INSERT INTO `Ad_Group_Keyword` VALUES ('1', '2013-08-01 00:00:00', '2013-08-19 20:00:09', '1', '1', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('2', '2013-08-01 00:00:00', '2013-08-19 20:00:09', '2', '2', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('3', '2013-08-01 00:00:00', '2013-08-19 20:00:09', '3', '3', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('4', '2013-08-01 00:00:00', '2013-08-19 20:00:09', '4', '4', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('5', '2013-08-01 00:00:00', '2013-08-19 20:00:09', '5', '5', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('6', '2013-08-01 00:00:00', '2013-08-19 20:00:09', '6', '6', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('7', '2013-08-01 00:00:00', '2013-08-19 20:00:09', '7', '7', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('8', '2013-08-01 00:00:00', '2013-08-19 20:00:09', '8', '8', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('9', '2013-08-01 00:00:00', '2013-08-19 20:00:09', '9', '9', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('10', '2013-08-01 00:00:00', '2013-08-19 20:00:09', '10', '10', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('23', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '22', '23', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('24', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '23', '24', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('25', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '24', '25', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('26', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '25', '26', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('27', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '26', '27', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('28', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '27', '28', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('29', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '28', '29', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('30', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '29', '30', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('31', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '30', '31', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('32', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '31', '32', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('33', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '32', '33', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('34', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '33', '34', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('35', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '34', '35', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('36', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '35', '36', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('37', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '36', '37', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('38', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '37', '38', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('39', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '38', '39', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('40', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '39', '40', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('41', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '40', '41', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('42', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '41', '42', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('43', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '42', '43', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('44', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '43', '44', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('45', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '44', '45', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('46', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '45', '46', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('47', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '46', '47', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('48', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '47', '48', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('49', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '48', '49', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('50', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '49', '50', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('51', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '50', '51', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('52', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '51', '52', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('53', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '52', '53', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('54', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '53', '54', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('55', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '54', '55', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('56', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '55', '56', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('57', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '56', '57', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('58', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '57', '58', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('59', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '58', '59', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('60', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '59', '60', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('61', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '60', '61', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('62', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '61', '62', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('63', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '62', '63', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('64', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '63', '64', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('65', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '64', '4', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('66', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '65', '9', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('67', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '66', '6', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '5.000', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('690', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '1', '645', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('691', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '2', '646', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('692', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '2', '647', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('693', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '2', '648', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('694', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '2', '649', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('695', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '2', '650', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('696', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '2', '651', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('697', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '2', '652', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('698', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '8', '653', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('699', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '10', '10', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('700', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '23', '655', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('701', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '23', '656', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('702', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '23', '657', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('703', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '30', '658', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('704', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '30', '659', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('705', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '30', '660', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('706', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '30', '31', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('707', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '30', '662', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('708', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '46', '663', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('709', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '47', '664', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('710', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '49', '665', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('711', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '49', '666', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('712', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '49', '667', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('713', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '50', '665', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('714', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '50', '666', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('715', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '50', '667', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('716', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '54', '671', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('717', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '54', '672', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('718', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '54', '673', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('719', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '54', '674', 'phrase', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('720', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '56', '57', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('721', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '58', '676', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('722', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '58', '677', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('723', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '58', '678', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('724', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '59', '678', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('725', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '59', '652', 'broad', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('726', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '65', '665', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('727', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '65', '666', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('728', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '65', '667', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'negative', null, null, '0');

-- ----------------------------
-- Table structure for Campaign_Budget
-- ----------------------------
DROP TABLE IF EXISTS `Campaign_Budget`;
CREATE TABLE `Campaign_Budget` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `traffic_provider_id` bigint(20) NOT NULL,
  `provider_supplied_id` bigint(20) DEFAULT NULL,
  `name` varchar(127) NOT NULL,
  `local_status` enum('active','deleted','unknown') NOT NULL,
  `provider_status` enum('active','deleted','unknown') DEFAULT NULL,
  `period` enum('daily') NOT NULL,
  `delivery_method` enum('standard','accelerated') NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `uploaded_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_uploaded` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `provider_supplied_id` (`provider_supplied_id`) USING BTREE,
  KEY `Campaign_Budget_ibfk_1` (`traffic_provider_id`) USING BTREE,
  KEY `is_uploaded` (`is_uploaded`) USING BTREE,
  CONSTRAINT `Campaign_Budget_ibfk_1` FOREIGN KEY (`traffic_provider_id`) REFERENCES `Traffic_Provider` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=193 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Campaign_Budget
-- ----------------------------
INSERT INTO `Campaign_Budget` VALUES ('1', '2013-11-04 00:00:00', '2013-08-19 20:00:01', '1', null, 'Qts2Cmp.Brands.20131104', 'active', null, 'daily', 'standard', '500.00', '0000-00-00 00:00:00', '0');

-- ----------------------------
-- Table structure for Keyword
-- ----------------------------
DROP TABLE IF EXISTS `Keyword`;
CREATE TABLE `Keyword` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `text` varchar(511) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `text` (`text`(255)) USING BTREE,
  KEY `created_ts` (`created_ts`) USING BTREE,
  KEY `updated_ts` (`updated_ts`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2080 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Keyword
-- ----------------------------
INSERT INTO `Keyword` VALUES ('1', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'acceptance insurance');
INSERT INTO `Keyword` VALUES ('2', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'access insurance');
INSERT INTO `Keyword` VALUES ('3', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'adriana insurance');
INSERT INTO `Keyword` VALUES ('4', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'bristol west');
INSERT INTO `Keyword` VALUES ('5', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'cotton state insurance');
INSERT INTO `Keyword` VALUES ('6', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'dairyland auto insurance');
INSERT INTO `Keyword` VALUES ('7', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'eastwood insurance');
INSERT INTO `Keyword` VALUES ('8', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'erie insurance');
INSERT INTO `Keyword` VALUES ('9', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'insurance liberty');
INSERT INTO `Keyword` VALUES ('10', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'look insurance');
INSERT INTO `Keyword` VALUES ('11', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'car compare insurance');
INSERT INTO `Keyword` VALUES ('12', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'auto insurance agency');
INSERT INTO `Keyword` VALUES ('13', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'auto insurance quote');
INSERT INTO `Keyword` VALUES ('14', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'classic auto insurance');
INSERT INTO `Keyword` VALUES ('15', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'low cost auto insurance');
INSERT INTO `Keyword` VALUES ('16', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'best car insurance');
INSERT INTO `Keyword` VALUES ('17', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'new car insurance');
INSERT INTO `Keyword` VALUES ('18', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'save on car insurance');
INSERT INTO `Keyword` VALUES ('19', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'low income auto insurance');
INSERT INTO `Keyword` VALUES ('20', '2013-08-01 00:00:00', '2013-08-19 20:00:09', 'low rate auto insurance');
INSERT INTO `Keyword` VALUES ('21', '2013-08-04 13:30:06', '2013-08-19 20:00:09', 'useless');
INSERT INTO `Keyword` VALUES ('22', '2013-08-04 13:30:06', '2013-08-19 20:00:09', 'ezway');
INSERT INTO `Keyword` VALUES ('23', '2013-08-07 00:00:00', '2013-08-19 20:00:09', '1800 general');
INSERT INTO `Keyword` VALUES ('24', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'acceptance');
INSERT INTO `Keyword` VALUES ('25', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'acceptance quotes');
INSERT INTO `Keyword` VALUES ('26', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'aggressive insurance');
INSERT INTO `Keyword` VALUES ('27', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'ameca');
INSERT INTO `Keyword` VALUES ('28', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'amica');
INSERT INTO `Keyword` VALUES ('29', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'amica insurance');
INSERT INTO `Keyword` VALUES ('30', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'amico insurance');
INSERT INTO `Keyword` VALUES ('31', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'commerce insurance');
INSERT INTO `Keyword` VALUES ('32', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'cosco insurance');
INSERT INTO `Keyword` VALUES ('33', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'countrywide insurance');
INSERT INTO `Keyword` VALUES ('34', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'dairyland insurance');
INSERT INTO `Keyword` VALUES ('35', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'dairyland car insurance');
INSERT INTO `Keyword` VALUES ('36', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'del toro insurance');
INSERT INTO `Keyword` VALUES ('37', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'encompass insurance');
INSERT INTO `Keyword` VALUES ('38', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'ensure insurance');
INSERT INTO `Keyword` VALUES ('39', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'estrella insurance');
INSERT INTO `Keyword` VALUES ('40', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'farm bureau car insurance');
INSERT INTO `Keyword` VALUES ('41', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'frankenmuth insurance');
INSERT INTO `Keyword` VALUES ('42', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'fred loya');
INSERT INTO `Keyword` VALUES ('43', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'fred loya insurance');
INSERT INTO `Keyword` VALUES ('44', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'freeway insurance');
INSERT INTO `Keyword` VALUES ('45', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'gary insurance');
INSERT INTO `Keyword` VALUES ('46', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'general insurance company of america');
INSERT INTO `Keyword` VALUES ('47', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'insure one');
INSERT INTO `Keyword` VALUES ('48', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'katz insurance');
INSERT INTO `Keyword` VALUES ('49', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'la insurance');
INSERT INTO `Keyword` VALUES ('50', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'liberty auto insurance');
INSERT INTO `Keyword` VALUES ('51', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'liberty insurance');
INSERT INTO `Keyword` VALUES ('52', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'loya insurance');
INSERT INTO `Keyword` VALUES ('53', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'meemic');
INSERT INTO `Keyword` VALUES ('54', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'muncie insurance');
INSERT INTO `Keyword` VALUES ('55', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'nationwide auto insurance');
INSERT INTO `Keyword` VALUES ('56', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'ocean harbor insurance');
INSERT INTO `Keyword` VALUES ('57', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'omni insurance');
INSERT INTO `Keyword` VALUES ('58', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'premier insurance');
INSERT INTO `Keyword` VALUES ('59', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'the general');
INSERT INTO `Keyword` VALUES ('60', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'the general insurance');
INSERT INTO `Keyword` VALUES ('61', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'us agencies');
INSERT INTO `Keyword` VALUES ('62', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'usa insurance');
INSERT INTO `Keyword` VALUES ('63', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'usaa.com legion');
INSERT INTO `Keyword` VALUES ('64', '2013-08-07 00:00:00', '2013-08-19 20:00:09', 'westfield insurance');
INSERT INTO `Keyword` VALUES ('645', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'www.acceptanceinsurance.com');
INSERT INTO `Keyword` VALUES ('646', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'america');
INSERT INTO `Keyword` VALUES ('647', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'american');
INSERT INTO `Keyword` VALUES ('648', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'general');
INSERT INTO `Keyword` VALUES ('649', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'company');
INSERT INTO `Keyword` VALUES ('650', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'access.com');
INSERT INTO `Keyword` VALUES ('651', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'www.access.com');
INSERT INTO `Keyword` VALUES ('652', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'general.com');
INSERT INTO `Keyword` VALUES ('653', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'group');
INSERT INTO `Keyword` VALUES ('655', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'credit');
INSERT INTO `Keyword` VALUES ('656', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'asset');
INSERT INTO `Keyword` VALUES ('657', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'admission');
INSERT INTO `Keyword` VALUES ('658', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'west');
INSERT INTO `Keyword` VALUES ('659', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'farmer');
INSERT INTO `Keyword` VALUES ('660', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'farmers');
INSERT INTO `Keyword` VALUES ('662', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'www.commerceinsurance.com');
INSERT INTO `Keyword` VALUES ('663', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'progressive');
INSERT INTO `Keyword` VALUES ('664', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'shirley');
INSERT INTO `Keyword` VALUES ('665', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'libertymutual.com');
INSERT INTO `Keyword` VALUES ('666', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'liberty.com');
INSERT INTO `Keyword` VALUES ('667', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'www.libertymutual.com');
INSERT INTO `Keyword` VALUES ('671', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'hartford');
INSERT INTO `Keyword` VALUES ('672', '2013-08-08 16:42:15', '2013-08-19 20:00:09', '.com');
INSERT INTO `Keyword` VALUES ('673', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'nationwide.com');
INSERT INTO `Keyword` VALUES ('674', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'nationwide coverage');
INSERT INTO `Keyword` VALUES ('676', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'goto');
INSERT INTO `Keyword` VALUES ('677', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'make');
INSERT INTO `Keyword` VALUES ('678', '2013-08-08 16:42:15', '2013-08-19 20:00:09', 'thegeneral.com');
INSERT INTO `Keyword` VALUES ('2072', '2013-08-09 14:23:24', '2013-08-19 20:00:09', 'cheap car insurance');
INSERT INTO `Keyword` VALUES ('2073', '2013-08-09 14:23:24', '2013-08-19 20:00:09', 'cheap auto insurance');
INSERT INTO `Keyword` VALUES ('2074', '2013-08-09 14:23:24', '2013-08-19 20:00:09', 'cheap car insurance online');
INSERT INTO `Keyword` VALUES ('2075', '2013-08-09 14:23:24', '2013-08-19 20:00:09', 'cheap auto insurance online');
INSERT INTO `Keyword` VALUES ('2076', '2013-08-09 14:23:24', '2013-08-19 20:00:09', 'cheap online car insurance');
INSERT INTO `Keyword` VALUES ('2077', '2013-08-09 14:23:24', '2013-08-19 20:00:09', 'cheap online auto insurance');
INSERT INTO `Keyword` VALUES ('2078', '2013-08-09 14:23:24', '2013-08-19 20:00:09', 'cheap auto insurance quotes');
INSERT INTO `Keyword` VALUES ('2079', '2013-08-09 14:23:24', '2013-08-19 20:00:09', 'cheap car insurance quotes');

-- ----------------------------
-- Table structure for Text_Ad
-- ----------------------------
DROP TABLE IF EXISTS `Text_Ad`;
CREATE TABLE `Text_Ad` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `headline` varchar(25) NOT NULL,
  `description1` varchar(35) NOT NULL,
  `description2` varchar(35) DEFAULT NULL,
  `displayUrl` varchar(35) NOT NULL,
  `actionUrl` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`headline`,`description1`,`description2`,`displayUrl`,`actionUrl`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8503 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Text_Ad
-- ----------------------------
INSERT INTO `Text_Ad` VALUES ('1', '2013-08-01 00:00:00', '2013-08-19 20:00:15', 'Acceptance Auto Insurance', 'Free Quotes & Save up to 75%!', 'Lowest Rates from $19/Month', 'accept.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('2', '2013-08-01 00:00:00', '2013-08-19 20:00:15', 'Access Auto Insurance', 'Free Quotes & Save up to 75%!', 'Lowest Rates from $19/Month', 'access.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('3', '2013-08-01 00:00:00', '2013-08-19 20:00:15', 'Adriana Auto Insurance', 'Free Quotes & Save up to 75%!', 'Lowest Rates from $19/Month', 'adriana.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('4', '2013-08-01 00:00:00', '2013-08-19 20:00:15', '$19 Cheap Auto Insurance', 'Free Quotes & Save up to 75%!', 'Lowest Rates from $19/Month', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('5', '2013-08-01 00:00:00', '2013-08-19 20:00:15', 'Cotton States Insurance', 'Free Quotes & Save up to 75%!', 'Lowest Rates from $19/Month', 'cotton.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6', '2013-08-01 00:00:00', '2013-08-19 20:00:15', 'Eastwood Auto Insurance', 'Free Quotes & Save up to 75%!', 'Lowest Rates from $19/Month', 'eastwood.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7', '2013-08-01 00:00:00', '2013-08-19 20:00:15', 'Erie Auto Insurance', 'Free Quotes & Save up to 75%!', 'Lowest Rates from $19/Month', 'erie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8', '2013-08-01 00:00:00', '2013-08-19 20:00:15', 'Look Auto Insurance', 'Free Quotes & Save up to 75%!', 'Lowest Rates from $19/Month', 'look.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('20', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'General Auto Insurance', 'Get Instant Quotes & Save Hundreds!', 'Lowest Rates from $19/Month', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('21', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Acceptance Auto Insurance', 'Free Quotes & Save up to 75%!', 'Lowest Rates from $19/Month', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('22', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Aggresive Auto Insurance', '$19/Month Free Aggresive Quotes!', 'Instantly Save up to 75%', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('23', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Amica Auto Insurance', 'Get Online Quotes from $19/Month!', 'Instant Discounts - Save 50%', 'amica.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('24', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Commerce Auto Insurance', 'Commerce Quotes from $19/Month!', 'Request Quotes Online and Save', 'commerce.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('25', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Costco Auto Insurance', 'Save up to 75% with Online Quotes!', 'Instant Discounts up to 75%', 'costco.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('26', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Countrywide Insurance', 'Free Online Quotes from $19/Month!', 'Request Quotes Now and Save', 'country.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('27', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Dairy Auto Insurance', 'Get Online Quotes from $19/Month!', 'Request Quotes Now and Save', 'dairy-land.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('28', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Del Toro Insurance', 'Get Online Quotes from $19/Month!', 'Save on Del Toro Insurance', 'deltoro.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('29', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Encompass Auto Insurance', 'Get Online Quotes from $19/Month!', 'Instant Discounts - Save 50%', 'encompass.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('30', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Ensure Auto Insurance', 'Get Ensure Quotes from $19/Month!', 'Instantly Save up to 75%', 'ensure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('31', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Estrella Auto Insurance', 'Get Cheapest Estralla Quotes Online', 'Quotes from $19/Month!', 'estrella.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('32', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Farm Auto Insurance', 'Get Online Quotes from $19/Month!', 'Instant Discounts - Save 50%', 'farm.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('33', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Frankenmuth Car Insurance', 'Get Frankenmuth Quotes from $19/mo!', 'Instant Online Savings up to 50%', 'frankenmuth.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('34', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Fred Car Insurance', 'Save 50% on Loya Car Insurance', 'Lowest Rates from $19/Month!', 'fred.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('35', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Free Way Online Quotes', 'Free Online Quotes from $19/Month!', 'Request Quotes Now and Save', 'free.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('36', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Gary Auto Insurance', 'Get Gary Insurance Quotes Online', 'Cheapest Quotes from $19/mo!', 'gary.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('37', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Insure Auto Insurance', 'Save up to 70% in 5 Minutes!', 'Lowest Rates from $19/Month', 'insure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('38', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Katz Auto Insurance', 'Get Online Quotes from $19/Month!', 'Request Quotes Now and Save', 'katz.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('39', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'LA Auto Insurance', 'Get Online Quotes from $19/Month!', 'Request Quotes Now and Save', 'la.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('40', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Liberty Quotes Online', 'Get Online Quotes from $19/Month!', 'Request Quotes Now and Save', 'liberty.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('41', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Loya Auto Insurance', 'Get Instant Quotes & Save Hundreds!', 'Lowest Rates from $19/Month', 'loya.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('42', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Meemic Car Insurance', 'Get Instant Quotes & Save Hundreds!', 'Lowest Rates from $19/Month', 'meemic.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('43', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Muncie Auto Insurance', 'Get Instant Quotes & Save Hundreds!', 'Lowest Rates from $19/Month', 'muncie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('44', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'National Auto Insurance', 'Get Instant Quotes & Save Hundreds!', 'Lowest Rates from $19/Month', 'national.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('45', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Ocean Harbor Insurance', 'Get Instant Quotes & Save Hundreds!', 'Lowest Rates from $19/Month', 'oceanharbor.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('46', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Omni Auto Insurance', 'Get Instant Quotes & Save Hundreds!', 'Lowest Rates from $19/Month', 'omni.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('47', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Premier Auto Insurance', 'Get Instant Quotes & Save Hundreds!', 'Lowest Rates from $19/Month', 'premier.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('48', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'US Agencies Car Insurance', 'Free Online Quotes from $19/Month!', 'Request Quotes Now and Save', 'usagencies.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('49', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'USA Auto Insurance', 'Get Online Quotes from $19/Month!', 'Instant Discounts - Save 50%', 'usa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('50', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'USA Car Insurance', 'Get Online Quotes from $19/Month!', 'Instant Discounts - Save 50%', 'usaa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('51', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Westfield Insurance', 'Free Online Quotes from $19/Month!', 'Request Quotes Now and Save', 'westfield.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('52', '2013-08-07 00:00:00', '2013-08-19 20:00:15', 'Bristol Auto Insurance', 'Free Online Quotes from $19/Month!', 'Request Quotes Now and Save', 'bristol.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('168', '2013-08-07 21:55:24', '2013-08-19 20:00:15', '$19 Acceptance Insurance', 'Save 67% on Acceptance Insurance!', 'Lowest Rates from $19/Month', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('176', '2013-08-07 21:55:24', '2013-08-19 20:00:15', 'Cheap Erie Auto Insurance', 'Cheapest Rates from $19/Month!', 'Save 67% on Erie Auto Insurance', 'erie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('577', '2013-08-08 16:42:16', '2013-08-19 20:00:15', '$19 Acceptance Insurance', 'Save 67% on Acceptance Insurance!', 'Lowest Rates from $19/Month', 'accept.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('602', '2013-08-08 16:42:16', '2013-08-19 20:00:15', 'Acceptance Auto Insurance', 'Save 67% Online Instantly!', 'Lowest Rates from $19/Month', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('606', '2013-08-08 16:42:16', '2013-08-19 20:00:15', '$19/Month Auto Insurance', 'Compare Online Quotes and Save', 'Instant Discounts - Save up to 67%', 'amica.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('609', '2013-08-08 16:42:16', '2013-08-19 20:00:15', 'Ameca Auto Insurance', 'Compare Online Quotes and Save', 'Instant Discounts - Save up to 67%', 'ameca.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('616', '2013-08-08 16:42:16', '2013-08-19 20:00:15', 'Cosco Auto Insurance', 'Save up to 75% with Online Quotes!', 'Instant Discounts up to 75%', 'costco.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('622', '2013-08-08 16:42:16', '2013-08-19 20:00:15', 'En compass Auto Insurance', 'Get Online Quotes from $19/Month!', 'Instant Discounts - Save 50%', 'encompass.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('641', '2013-08-08 16:42:16', '2013-08-19 20:00:15', 'Nation Wide Car Insurance', 'Get Instant Quotes & Save 67%!', 'Lowest Rates from $19/Month', 'nation.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('642', '2013-08-08 16:42:16', '2013-08-19 20:00:15', 'Nation-wide Car Insurance', 'Get Instant Quotes & Save 67%!', 'Lowest Rates from $19/Month', 'nation.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('643', '2013-08-08 16:42:16', '2013-08-19 20:00:15', 'Nation Car Insurance', 'Get Instant Quotes & Save 67%!', 'Lowest Rates from $19/Month', 'nation.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('648', '2013-08-08 16:42:16', '2013-08-19 20:00:15', 'General Auto Insurance', 'Get Instant Quotes & Save 67%!', 'Lowest Rates from $19/Month', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('652', '2013-08-08 16:42:16', '2013-08-19 20:00:15', 'USA Auto Insurance', 'Get Online Quotes from $19/Month!', 'Instant Discounts - Save 67%', 'usa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1179', '2013-08-09 01:15:04', '2013-08-19 20:00:15', '$19 Acceptance Insurance', 'Save 67% on Acceptance Insurance!', 'MA Residents Exclusive', 'accept.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1180', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Acceptance Auto Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Exclusive', 'accept.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1182', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'MA Access Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'access.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1183', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'MA Access Auto Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'access.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1185', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Adriana Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'adriana.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1186', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Adriana Auto Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'adriana.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1188', '2013-08-09 01:15:04', '2013-08-19 20:00:15', '$19 Cheap Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1189', '2013-08-09 01:15:04', '2013-08-19 20:00:15', '$19 Cheap Auto Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1191', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Cotton States Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'cotton.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1192', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Cotton States Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'cotton.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1194', '2013-08-09 01:15:04', '2013-08-19 20:00:15', '$19 Dairy Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1195', '2013-08-09 01:15:04', '2013-08-19 20:00:15', '$19 Dairy Auto Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1198', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Eastwood Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'eastwood.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1199', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Eastwood Auto Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'eastwood.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1202', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Erie Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'erie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1203', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Cheap Erie Auto Insurance', 'Cheapest Rates from $19/Month!', 'MA Residents Only', 'erie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1208', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Look Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'look.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1209', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Look Auto Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'look.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1211', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'General Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1212', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'General Auto Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1215', '2013-08-09 01:15:04', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Save 67% Online Instantly!', 'Exclusive Offer for MA Residents', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1216', '2013-08-09 01:15:04', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Save 67% Online Instantly!', 'MA Residents Only', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1218', '2013-08-09 01:15:04', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1219', '2013-08-09 01:15:04', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1221', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Aggresive Auto Insurance', '$19/Month Free Aggresive Quotes!', 'Exclusive Offer for MA Residents', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1222', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Aggresive Auto Insurance', '$19/Month Free Aggresive Quotes!', 'MA Residents Only', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1224', '2013-08-09 01:15:04', '2013-08-19 20:00:15', '$19/Month Auto Insurance', 'Instant Discounts - Save up to 67%!', 'Exclusive Offer for MA Residents', 'amica.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1225', '2013-08-09 01:15:04', '2013-08-19 20:00:15', '$19/Month Auto Insurance', 'Instant Discounts - Save up to 67%!', 'MA Residents Only', 'amica.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1237', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Commerce Auto Insurance', 'Commerce Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'commerce.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1238', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Commerce Auto Insurance', 'Commerce Quotes from $19/Month!', 'MA Residents Only', 'commerce.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1240', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Cosco Auto Insurance', 'Save up to 67% with Online Quotes!', 'Exclusive Offer for MA Residents', 'costco.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1241', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Cosco Auto Insurance', 'Save up to 67% with Online Quotes!', 'MA Residents Only', 'costco.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1243', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Countrywide Insurance', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'country.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1244', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Countrywide Insurance', 'Free Online Quotes from $19/Month!', 'MA Residents Only', 'country.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1246', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Dairy Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'dairy-land.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1247', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Dairy Auto Insurance', 'Get Online Quotes from $19/Month!', 'MA Residents Only', 'dairy-land.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1252', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Del Toro Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'deltoro.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1253', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Del Toro Insurance', 'Get Online Quotes from $19/Month!', 'MA Residents Only', 'deltoro.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1255', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'En compass Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'encompass.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1256', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'En compass Auto Insurance', 'Get Online Quotes from $19/Month!', 'MA Residents Only', 'encompass.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1258', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Ensure Auto Insurance', 'Get Ensure Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'ensure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1259', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Ensure Auto Insurance', 'Get Ensure Quotes from $19/Month!', 'MA Residents Only', 'ensure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1261', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Estrella Auto Insurance', 'Get Cheapest Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'estrella.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1262', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Estrella Auto Insurance', 'Get Cheapest Quotes from $19/Month!', 'MA Residents Only', 'estrella.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1264', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Farm Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'farm.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1265', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Farm Auto Insurance', 'Get Online Quotes from $19/Month!', 'MA Residents Only', 'farm.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1267', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Frankenmuth Car Insurance', 'Get Frankenmuth Quotes from $19/mo!', 'Exclusive Offer for MA Residents', 'frankenmuth.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1268', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Frankenmuth Car Insurance', 'Get Frankenmuth Quotes from $19/mo!', 'MA Residents Only', 'frankenmuth.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1270', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Fred Car Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'fred.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1271', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Fred Car Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'fred.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1276', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Free Way Online Quotes', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'free.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1277', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Free Way Online Quotes', 'Free Online Quotes from $19/Month!', 'MA Residents Only', 'free.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1279', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Gary Auto Insurance', 'Cheapest Quotes from $19/mo!', 'Exclusive Offer for MA Residents', 'gary.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1280', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Gary Auto Insurance', 'Cheapest Quotes from $19/mo!', 'MA Residents Only', 'gary.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1285', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Insure Auto Insurance', 'Save up to 70% in 5 Minutes!', 'Exclusive Offer for MA Residents', 'insure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1286', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Insure Auto Insurance', 'Save up to 70% in 5 Minutes!', 'MA Residents Only', 'insure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1288', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Katz Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'katz.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1289', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Katz Auto Insurance', 'Get Online Quotes from $19/Month!', 'MA Residents Only', 'katz.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1291', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'LA Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'la.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1292', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'LA Auto Insurance', 'Get Online Quotes from $19/Month!', 'MA Residents Only', 'la.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1294', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'MA Liberty Quotes Online', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'liberty.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1295', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'MA Liberty Quotes Online', 'Get Online Quotes from $19/Month!', 'MA Residents Only', 'liberty.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1300', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Loya Auto Insurance', 'Lowest Rates from $19/Month', 'Exclusive Offer for MA Residents', 'loya.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1301', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Loya Auto Insurance', 'Lowest Rates from $19/Month', 'MA Residents Only', 'loya.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1303', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Meemic Car Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'meemic.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1304', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Meemic Car Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'meemic.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1306', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Muncie Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'muncie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1307', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Muncie Auto Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'muncie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1312', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Nation Car Insurance', 'Get Instant Quotes & Save 67%!', 'Exclusive Offer for MA Residents', 'nation.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1313', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'National Auto Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'national.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1316', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Ocean Harbor Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'oceanharbor.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1317', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Ocean Harbor Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'oceanharbor.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1319', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Omni Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'omni.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1320', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Omni Auto Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'omni.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1322', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Premier Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for MA Residents', 'premier.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1323', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Premier Auto Insurance', 'Lowest Rates from $19/Month!', 'MA Residents Only', 'premier.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1326', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'General Auto Insurance', 'Get Instant Quotes & Save 67%!', 'Exclusive Offer for MA Residents', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1332', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'US Agencies Car Insurance', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'usagencies.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1333', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'US Agencies Car Insurance', 'Free Online Quotes from $19/Month!', 'MA Residents Only', 'usagencies.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1336', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'USA Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'usa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1337', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'USA Auto Insurance', 'Instant Discounts - Save 67%', 'MA Residents Only', 'usa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1339', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'USA Car Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'usaa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1340', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'USA Car Insurance', 'Get Online Quotes from $19/Month!', 'MA Residents Only', 'usaa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1342', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Westfield Insurance', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for MA Residents', 'westfield.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1343', '2013-08-09 01:15:04', '2013-08-19 20:00:15', 'Westfield Insurance', 'Free Online Quotes from $19/Month!', 'MA Residents Only', 'westfield.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1466', '2013-08-09 01:56:14', '2013-08-19 20:00:15', '$19 Acceptance Insurance', 'Save 67% on Acceptance Insurance!', 'TN Residents Exclusive', 'accept.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1467', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Acceptance Auto Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Exclusive', 'accept.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1468', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'TN Access Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'access.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1469', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'TN Access Auto Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'access.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1470', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Adriana Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'adriana.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1471', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Adriana Auto Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'adriana.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1472', '2013-08-09 01:56:14', '2013-08-19 20:00:15', '$19 Cheap Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1473', '2013-08-09 01:56:14', '2013-08-19 20:00:15', '$19 Cheap Auto Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1474', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Cotton States Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'cotton.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1475', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Cotton States Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'cotton.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1476', '2013-08-09 01:56:14', '2013-08-19 20:00:15', '$19 Dairy Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1477', '2013-08-09 01:56:14', '2013-08-19 20:00:15', '$19 Dairy Auto Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1478', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Eastwood Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'eastwood.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1479', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Eastwood Auto Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'eastwood.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1480', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Erie Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'erie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1481', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Cheap Erie Auto Insurance', 'Cheapest Rates from $19/Month!', 'TN Residents Only', 'erie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1482', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Look Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'look.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1483', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Look Auto Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'look.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1484', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'General Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1485', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'General Auto Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1486', '2013-08-09 01:56:14', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Save 67% Online Instantly!', 'Exclusive Offer for TN Residents', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1487', '2013-08-09 01:56:14', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Save 67% Online Instantly!', 'TN Residents Only', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1488', '2013-08-09 01:56:14', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1489', '2013-08-09 01:56:14', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1490', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Aggresive Auto Insurance', '$19/Month Free Aggresive Quotes!', 'Exclusive Offer for TN Residents', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1491', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Aggresive Auto Insurance', '$19/Month Free Aggresive Quotes!', 'TN Residents Only', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1492', '2013-08-09 01:56:14', '2013-08-19 20:00:15', '$19/Month Auto Insurance', 'Instant Discounts - Save up to 67%!', 'Exclusive Offer for TN Residents', 'amica.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1493', '2013-08-09 01:56:14', '2013-08-19 20:00:15', '$19/Month Auto Insurance', 'Instant Discounts - Save up to 67%!', 'TN Residents Only', 'amica.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1494', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Commerce Auto Insurance', 'Commerce Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'commerce.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1495', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Commerce Auto Insurance', 'Commerce Quotes from $19/Month!', 'TN Residents Only', 'commerce.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1496', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Cosco Auto Insurance', 'Save up to 67% with Online Quotes!', 'Exclusive Offer for TN Residents', 'costco.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1497', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Cosco Auto Insurance', 'Save up to 67% with Online Quotes!', 'TN Residents Only', 'costco.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1498', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Countrywide Insurance', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'country.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1499', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Countrywide Insurance', 'Free Online Quotes from $19/Month!', 'TN Residents Only', 'country.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1500', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Dairy Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'dairy-land.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1501', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Dairy Auto Insurance', 'Get Online Quotes from $19/Month!', 'TN Residents Only', 'dairy-land.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1502', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Del Toro Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'deltoro.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1503', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Del Toro Insurance', 'Get Online Quotes from $19/Month!', 'TN Residents Only', 'deltoro.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1504', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'En compass Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'encompass.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1505', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'En compass Auto Insurance', 'Get Online Quotes from $19/Month!', 'TN Residents Only', 'encompass.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1506', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Ensure Auto Insurance', 'Get Ensure Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'ensure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1507', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Ensure Auto Insurance', 'Get Ensure Quotes from $19/Month!', 'TN Residents Only', 'ensure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1508', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Estrella Auto Insurance', 'Get Cheapest Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'estrella.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1509', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Estrella Auto Insurance', 'Get Cheapest Quotes from $19/Month!', 'TN Residents Only', 'estrella.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1510', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Farm Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'farm.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1511', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Farm Auto Insurance', 'Get Online Quotes from $19/Month!', 'TN Residents Only', 'farm.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1512', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Frankenmuth Car Insurance', 'Get Frankenmuth Quotes from $19/mo!', 'Exclusive Offer for TN Residents', 'frankenmuth.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1513', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Frankenmuth Car Insurance', 'Get Frankenmuth Quotes from $19/mo!', 'TN Residents Only', 'frankenmuth.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1514', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Fred Car Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'fred.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1515', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Fred Car Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'fred.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1516', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Free Way Online Quotes', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'free.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1517', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Free Way Online Quotes', 'Free Online Quotes from $19/Month!', 'TN Residents Only', 'free.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1518', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Gary Auto Insurance', 'Cheapest Quotes from $19/mo!', 'Exclusive Offer for TN Residents', 'gary.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1519', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Gary Auto Insurance', 'Cheapest Quotes from $19/mo!', 'TN Residents Only', 'gary.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1520', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Insure Auto Insurance', 'Save up to 70% in 5 Minutes!', 'Exclusive Offer for TN Residents', 'insure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1521', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Insure Auto Insurance', 'Save up to 70% in 5 Minutes!', 'TN Residents Only', 'insure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1522', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Katz Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'katz.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1523', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Katz Auto Insurance', 'Get Online Quotes from $19/Month!', 'TN Residents Only', 'katz.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1524', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'LA Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'la.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1525', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'LA Auto Insurance', 'Get Online Quotes from $19/Month!', 'TN Residents Only', 'la.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1526', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'TN Liberty Quotes Online', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'liberty.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1527', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'TN Liberty Quotes Online', 'Get Online Quotes from $19/Month!', 'TN Residents Only', 'liberty.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1528', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Loya Auto Insurance', 'Lowest Rates from $19/Month', 'Exclusive Offer for TN Residents', 'loya.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1529', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Loya Auto Insurance', 'Lowest Rates from $19/Month', 'TN Residents Only', 'loya.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1530', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Meemic Car Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'meemic.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1531', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Meemic Car Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'meemic.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1532', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Muncie Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'muncie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1533', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Muncie Auto Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'muncie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1534', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Nation Car Insurance', 'Get Instant Quotes & Save 67%!', 'Exclusive Offer for TN Residents', 'nation.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1535', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'National Auto Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'national.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1536', '2013-08-09 01:56:14', '2013-08-19 20:00:15', 'Ocean Harbor Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'oceanharbor.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1537', '2013-08-09 01:56:15', '2013-08-19 20:00:15', 'Ocean Harbor Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'oceanharbor.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1538', '2013-08-09 01:56:15', '2013-08-19 20:00:15', 'Omni Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'omni.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1539', '2013-08-09 01:56:15', '2013-08-19 20:00:15', 'Omni Auto Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'omni.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1540', '2013-08-09 01:56:15', '2013-08-19 20:00:15', 'Premier Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TN Residents', 'premier.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1541', '2013-08-09 01:56:15', '2013-08-19 20:00:15', 'Premier Auto Insurance', 'Lowest Rates from $19/Month!', 'TN Residents Only', 'premier.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1542', '2013-08-09 01:56:15', '2013-08-19 20:00:15', 'General Auto Insurance', 'Get Instant Quotes & Save 67%!', 'Exclusive Offer for TN Residents', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1543', '2013-08-09 01:56:15', '2013-08-19 20:00:15', 'US Agencies Car Insurance', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'usagencies.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1544', '2013-08-09 01:56:15', '2013-08-19 20:00:15', 'US Agencies Car Insurance', 'Free Online Quotes from $19/Month!', 'TN Residents Only', 'usagencies.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1545', '2013-08-09 01:56:15', '2013-08-19 20:00:15', 'USA Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'usa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1546', '2013-08-09 01:56:15', '2013-08-19 20:00:15', 'USA Auto Insurance', 'Instant Discounts - Save 67%', 'TN Residents Only', 'usa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1547', '2013-08-09 01:56:15', '2013-08-19 20:00:15', 'USA Car Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'usaa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1548', '2013-08-09 01:56:15', '2013-08-19 20:00:15', 'USA Car Insurance', 'Get Online Quotes from $19/Month!', 'TN Residents Only', 'usaa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1549', '2013-08-09 01:56:15', '2013-08-19 20:00:15', 'Westfield Insurance', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for TN Residents', 'westfield.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1550', '2013-08-09 01:56:15', '2013-08-19 20:00:15', 'Westfield Insurance', 'Free Online Quotes from $19/Month!', 'TN Residents Only', 'westfield.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1551', '2013-08-09 01:57:17', '2013-08-19 20:00:15', '$19 Acceptance Insurance', 'Save 67% on Acceptance Insurance!', 'TX Residents Exclusive', 'accept.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1552', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Acceptance Auto Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Exclusive', 'accept.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1553', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'TX Access Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'access.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1554', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'TX Access Auto Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'access.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1555', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Adriana Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'adriana.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1556', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Adriana Auto Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'adriana.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1557', '2013-08-09 01:57:17', '2013-08-19 20:00:15', '$19 Cheap Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1558', '2013-08-09 01:57:17', '2013-08-19 20:00:15', '$19 Cheap Auto Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1559', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Cotton States Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'cotton.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1560', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Cotton States Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'cotton.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1561', '2013-08-09 01:57:17', '2013-08-19 20:00:15', '$19 Dairy Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1562', '2013-08-09 01:57:17', '2013-08-19 20:00:15', '$19 Dairy Auto Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1563', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Eastwood Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'eastwood.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1564', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Eastwood Auto Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'eastwood.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1565', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Erie Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'erie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1566', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Cheap Erie Auto Insurance', 'Cheapest Rates from $19/Month!', 'TX Residents Only', 'erie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1567', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Look Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'look.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1568', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Look Auto Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'look.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1569', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'General Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1570', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'General Auto Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1571', '2013-08-09 01:57:17', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Save 67% Online Instantly!', 'Exclusive Offer for TX Residents', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1572', '2013-08-09 01:57:17', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Save 67% Online Instantly!', 'TX Residents Only', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1573', '2013-08-09 01:57:17', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1574', '2013-08-09 01:57:17', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1575', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Aggresive Auto Insurance', '$19/Month Free Aggresive Quotes!', 'Exclusive Offer for TX Residents', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1576', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Aggresive Auto Insurance', '$19/Month Free Aggresive Quotes!', 'TX Residents Only', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1577', '2013-08-09 01:57:17', '2013-08-19 20:00:15', '$19/Month Auto Insurance', 'Instant Discounts - Save up to 67%!', 'Exclusive Offer for TX Residents', 'amica.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1578', '2013-08-09 01:57:17', '2013-08-19 20:00:15', '$19/Month Auto Insurance', 'Instant Discounts - Save up to 67%!', 'TX Residents Only', 'amica.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1579', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Commerce Auto Insurance', 'Commerce Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'commerce.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1580', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Commerce Auto Insurance', 'Commerce Quotes from $19/Month!', 'TX Residents Only', 'commerce.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1581', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Cosco Auto Insurance', 'Save up to 67% with Online Quotes!', 'Exclusive Offer for TX Residents', 'costco.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1582', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Cosco Auto Insurance', 'Save up to 67% with Online Quotes!', 'TX Residents Only', 'costco.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1583', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Countrywide Insurance', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'country.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1584', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Countrywide Insurance', 'Free Online Quotes from $19/Month!', 'TX Residents Only', 'country.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1585', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Dairy Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'dairy-land.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1586', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Dairy Auto Insurance', 'Get Online Quotes from $19/Month!', 'TX Residents Only', 'dairy-land.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1587', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Del Toro Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'deltoro.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1588', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Del Toro Insurance', 'Get Online Quotes from $19/Month!', 'TX Residents Only', 'deltoro.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1589', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'En compass Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'encompass.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1590', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'En compass Auto Insurance', 'Get Online Quotes from $19/Month!', 'TX Residents Only', 'encompass.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1591', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Ensure Auto Insurance', 'Get Ensure Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'ensure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1592', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Ensure Auto Insurance', 'Get Ensure Quotes from $19/Month!', 'TX Residents Only', 'ensure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1593', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Estrella Auto Insurance', 'Get Cheapest Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'estrella.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1594', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Estrella Auto Insurance', 'Get Cheapest Quotes from $19/Month!', 'TX Residents Only', 'estrella.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1595', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Farm Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'farm.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1596', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Farm Auto Insurance', 'Get Online Quotes from $19/Month!', 'TX Residents Only', 'farm.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1597', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Frankenmuth Car Insurance', 'Get Frankenmuth Quotes from $19/mo!', 'Exclusive Offer for TX Residents', 'frankenmuth.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1598', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Frankenmuth Car Insurance', 'Get Frankenmuth Quotes from $19/mo!', 'TX Residents Only', 'frankenmuth.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1599', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Fred Car Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'fred.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1600', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Fred Car Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'fred.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1601', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Free Way Online Quotes', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'free.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1602', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Free Way Online Quotes', 'Free Online Quotes from $19/Month!', 'TX Residents Only', 'free.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1603', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Gary Auto Insurance', 'Cheapest Quotes from $19/mo!', 'Exclusive Offer for TX Residents', 'gary.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1604', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Gary Auto Insurance', 'Cheapest Quotes from $19/mo!', 'TX Residents Only', 'gary.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1605', '2013-08-09 01:57:17', '2013-08-19 20:00:15', 'Insure Auto Insurance', 'Save up to 70% in 5 Minutes!', 'Exclusive Offer for TX Residents', 'insure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1606', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Insure Auto Insurance', 'Save up to 70% in 5 Minutes!', 'TX Residents Only', 'insure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1607', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Katz Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'katz.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1608', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Katz Auto Insurance', 'Get Online Quotes from $19/Month!', 'TX Residents Only', 'katz.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1609', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'LA Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'la.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1610', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'LA Auto Insurance', 'Get Online Quotes from $19/Month!', 'TX Residents Only', 'la.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1611', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'TX Liberty Quotes Online', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'liberty.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1612', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'TX Liberty Quotes Online', 'Get Online Quotes from $19/Month!', 'TX Residents Only', 'liberty.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1613', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Loya Auto Insurance', 'Lowest Rates from $19/Month', 'Exclusive Offer for TX Residents', 'loya.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1614', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Loya Auto Insurance', 'Lowest Rates from $19/Month', 'TX Residents Only', 'loya.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1615', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Meemic Car Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'meemic.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1616', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Meemic Car Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'meemic.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1617', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Muncie Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'muncie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1618', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Muncie Auto Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'muncie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1619', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Nation Car Insurance', 'Get Instant Quotes & Save 67%!', 'Exclusive Offer for TX Residents', 'nation.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1620', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'National Auto Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'national.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1621', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Ocean Harbor Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'oceanharbor.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1622', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Ocean Harbor Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'oceanharbor.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1623', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Omni Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'omni.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1624', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Omni Auto Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'omni.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1625', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Premier Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for TX Residents', 'premier.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1626', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Premier Auto Insurance', 'Lowest Rates from $19/Month!', 'TX Residents Only', 'premier.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1627', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'General Auto Insurance', 'Get Instant Quotes & Save 67%!', 'Exclusive Offer for TX Residents', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1628', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'US Agencies Car Insurance', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'usagencies.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1629', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'US Agencies Car Insurance', 'Free Online Quotes from $19/Month!', 'TX Residents Only', 'usagencies.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1630', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'USA Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'usa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1631', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'USA Auto Insurance', 'Instant Discounts - Save 67%', 'TX Residents Only', 'usa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1632', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'USA Car Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'usaa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1633', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'USA Car Insurance', 'Get Online Quotes from $19/Month!', 'TX Residents Only', 'usaa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1634', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Westfield Insurance', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for TX Residents', 'westfield.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('1635', '2013-08-09 01:57:18', '2013-08-19 20:00:15', 'Westfield Insurance', 'Free Online Quotes from $19/Month!', 'TX Residents Only', 'westfield.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('2221', '2013-08-09 14:15:09', '2013-08-19 20:00:15', '$19_Cheap_Car_Insurance', 'Get Cheapest Quotes Online', 'You Could Save up to 67%!', 'quotes.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('2222', '2013-08-09 14:15:09', '2013-08-19 20:00:15', '$19_Cheap_Auto_Insurance', 'Get Cheapest Quotes Online', 'You Could Save up to 67%!', 'quotes.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6982', '2013-08-12 20:23:30', '2013-08-19 20:00:15', '$19 Acceptance Insurance', 'Save 67% on Acceptance Insurance!', 'NY Residents Exclusive', 'accept.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6983', '2013-08-12 20:23:30', '2013-08-19 20:00:15', 'Acceptance Auto Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Exclusive', 'accept.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6984', '2013-08-12 20:23:30', '2013-08-19 20:00:15', 'NY Access Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'access.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6985', '2013-08-12 20:23:30', '2013-08-19 20:00:15', 'NY Access Auto Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'access.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6986', '2013-08-12 20:23:30', '2013-08-19 20:00:15', 'Adriana Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'adriana.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6987', '2013-08-12 20:23:30', '2013-08-19 20:00:15', 'Adriana Auto Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'adriana.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6988', '2013-08-12 20:23:30', '2013-08-19 20:00:15', '$19 Cheap Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6989', '2013-08-12 20:23:30', '2013-08-19 20:00:15', '$19 Cheap Auto Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6990', '2013-08-12 20:23:30', '2013-08-19 20:00:15', 'Cotton States Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'cotton.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6991', '2013-08-12 20:23:30', '2013-08-19 20:00:15', 'Cotton States Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'cotton.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6992', '2013-08-12 20:23:30', '2013-08-19 20:00:15', '$19 Dairy Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6993', '2013-08-12 20:23:30', '2013-08-19 20:00:15', '$19 Dairy Auto Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6994', '2013-08-12 20:23:30', '2013-08-19 20:00:15', 'Eastwood Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'eastwood.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6995', '2013-08-12 20:23:30', '2013-08-19 20:00:15', 'Eastwood Auto Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'eastwood.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6996', '2013-08-12 20:23:30', '2013-08-19 20:00:15', 'Erie Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'erie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6997', '2013-08-12 20:23:30', '2013-08-19 20:00:15', 'Cheap Erie Auto Insurance', 'Cheapest Rates from $19/Month!', 'NY Residents Only', 'erie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6998', '2013-08-12 20:23:30', '2013-08-19 20:00:15', 'Look Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'look.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('6999', '2013-08-12 20:23:30', '2013-08-19 20:00:15', 'Look Auto Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'look.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7000', '2013-08-12 20:23:30', '2013-08-19 20:00:15', 'General Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7001', '2013-08-12 20:23:30', '2013-08-19 20:00:15', 'General Auto Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7002', '2013-08-12 20:23:30', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Save 67% Online Instantly!', 'Exclusive Offer for NY Residents', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7003', '2013-08-12 20:23:31', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Save 67% Online Instantly!', 'NY Residents Only', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7004', '2013-08-12 20:23:31', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7005', '2013-08-12 20:23:31', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7006', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Aggresive Auto Insurance', '$19/Month Free Aggresive Quotes!', 'Exclusive Offer for NY Residents', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7007', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Aggresive Auto Insurance', '$19/Month Free Aggresive Quotes!', 'NY Residents Only', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7008', '2013-08-12 20:23:31', '2013-08-19 20:00:15', '$19/Month Auto Insurance', 'Instant Discounts - Save up to 67%!', 'Exclusive Offer for NY Residents', 'amica.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7009', '2013-08-12 20:23:31', '2013-08-19 20:00:15', '$19/Month Auto Insurance', 'Instant Discounts - Save up to 67%!', 'NY Residents Only', 'amica.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7010', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Commerce Auto Insurance', 'Commerce Quotes from $19/Month!', 'Exclusive Offer for NY Residents', 'commerce.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7011', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Commerce Auto Insurance', 'Commerce Quotes from $19/Month!', 'NY Residents Only', 'commerce.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7012', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Cosco Auto Insurance', 'Save up to 67% with Online Quotes!', 'Exclusive Offer for NY Residents', 'costco.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7013', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Cosco Auto Insurance', 'Save up to 67% with Online Quotes!', 'NY Residents Only', 'costco.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7014', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Dairy Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for NY Residents', 'dairy-land.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7015', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Dairy Auto Insurance', 'Get Online Quotes from $19/Month!', 'NY Residents Only', 'dairy-land.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7016', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Del Toro Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for NY Residents', 'deltoro.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7017', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Del Toro Insurance', 'Get Online Quotes from $19/Month!', 'NY Residents Only', 'deltoro.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7018', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'En compass Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for NY Residents', 'encompass.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7019', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'En compass Auto Insurance', 'Get Online Quotes from $19/Month!', 'NY Residents Only', 'encompass.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7020', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Ensure Auto Insurance', 'Get Ensure Quotes from $19/Month!', 'Exclusive Offer for NY Residents', 'ensure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7021', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Ensure Auto Insurance', 'Get Ensure Quotes from $19/Month!', 'NY Residents Only', 'ensure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7022', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Estrella Auto Insurance', 'Get Cheapest Quotes from $19/Month!', 'Exclusive Offer for NY Residents', 'estrella.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7023', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Estrella Auto Insurance', 'Get Cheapest Quotes from $19/Month!', 'NY Residents Only', 'estrella.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7024', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Farm Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for NY Residents', 'farm.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7025', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Farm Auto Insurance', 'Get Online Quotes from $19/Month!', 'NY Residents Only', 'farm.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7026', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Frankenmuth Car Insurance', 'Get Frankenmuth Quotes from $19/mo!', 'Exclusive Offer for NY Residents', 'frankenmuth.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7027', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Frankenmuth Car Insurance', 'Get Frankenmuth Quotes from $19/mo!', 'NY Residents Only', 'frankenmuth.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7028', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Fred Car Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'fred.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7029', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Fred Car Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'fred.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7030', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Free Way Online Quotes', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for NY Residents', 'free.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7031', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Free Way Online Quotes', 'Free Online Quotes from $19/Month!', 'NY Residents Only', 'free.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7032', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Gary Auto Insurance', 'Cheapest Quotes from $19/mo!', 'Exclusive Offer for NY Residents', 'gary.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7033', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Gary Auto Insurance', 'Cheapest Quotes from $19/mo!', 'NY Residents Only', 'gary.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7034', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Insure Auto Insurance', 'Save up to 70% in 5 Minutes!', 'Exclusive Offer for NY Residents', 'insure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7035', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Insure Auto Insurance', 'Save up to 70% in 5 Minutes!', 'NY Residents Only', 'insure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7036', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Katz Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for NY Residents', 'katz.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7037', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Katz Auto Insurance', 'Get Online Quotes from $19/Month!', 'NY Residents Only', 'katz.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7038', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'LA Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for NY Residents', 'la.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7039', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'LA Auto Insurance', 'Get Online Quotes from $19/Month!', 'NY Residents Only', 'la.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7040', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'NY Liberty Quotes Online', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for NY Residents', 'liberty.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7041', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'NY Liberty Quotes Online', 'Get Online Quotes from $19/Month!', 'NY Residents Only', 'liberty.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7042', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Loya Auto Insurance', 'Lowest Rates from $19/Month', 'Exclusive Offer for NY Residents', 'loya.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7043', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Loya Auto Insurance', 'Lowest Rates from $19/Month', 'NY Residents Only', 'loya.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7044', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Meemic Car Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'meemic.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7045', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Meemic Car Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'meemic.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7046', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Muncie Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'muncie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7047', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Muncie Auto Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'muncie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7048', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Nation Car Insurance', 'Get Instant Quotes & Save 67%!', 'Exclusive Offer for NY Residents', 'nation.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7049', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'National Auto Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'national.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7050', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Ocean Harbor Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'oceanharbor.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7051', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Ocean Harbor Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'oceanharbor.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7052', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Omni Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'omni.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7053', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Omni Auto Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'omni.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7054', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Premier Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for NY Residents', 'premier.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7055', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Premier Auto Insurance', 'Lowest Rates from $19/Month!', 'NY Residents Only', 'premier.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7056', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'General Auto Insurance', 'Get Instant Quotes & Save 67%!', 'Exclusive Offer for NY Residents', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7057', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'US Agencies Car Insurance', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for NY Residents', 'usagencies.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7058', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'US Agencies Car Insurance', 'Free Online Quotes from $19/Month!', 'NY Residents Only', 'usagencies.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7059', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'USA Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for NY Residents', 'usa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7060', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'USA Auto Insurance', 'Instant Discounts - Save 67%', 'NY Residents Only', 'usa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7061', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'USA Car Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for NY Residents', 'usaa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7062', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'USA Car Insurance', 'Get Online Quotes from $19/Month!', 'NY Residents Only', 'usaa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7063', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Westfield Insurance', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for NY Residents', 'westfield.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('7064', '2013-08-12 20:23:31', '2013-08-19 20:00:15', 'Westfield Insurance', 'Free Online Quotes from $19/Month!', 'NY Residents Only', 'westfield.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8420', '2013-08-12 20:31:18', '2013-08-19 20:00:15', '$19 Acceptance Insurance', 'Save 67% on Acceptance Insurance!', 'FL Residents Exclusive', 'accept.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8421', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Acceptance Auto Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Exclusive', 'accept.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8422', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'FL Access Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'access.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8423', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'FL Access Auto Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'access.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8424', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Adriana Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'adriana.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8425', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Adriana Auto Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'adriana.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8426', '2013-08-12 20:31:18', '2013-08-19 20:00:15', '$19 Cheap Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8427', '2013-08-12 20:31:18', '2013-08-19 20:00:15', '$19 Cheap Auto Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8428', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Cotton States Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'cotton.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8429', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Cotton States Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'cotton.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8430', '2013-08-12 20:31:18', '2013-08-19 20:00:15', '$19 Dairy Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8431', '2013-08-12 20:31:18', '2013-08-19 20:00:15', '$19 Dairy Auto Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'www.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8432', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Eastwood Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'eastwood.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8433', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Eastwood Auto Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'eastwood.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8434', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Erie Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'erie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8435', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Cheap Erie Auto Insurance', 'Cheapest Rates from $19/Month!', 'FL Residents Only', 'erie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8436', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Look Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'look.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8437', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Look Auto Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'look.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8438', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'General Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8439', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'General Auto Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8440', '2013-08-12 20:31:18', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Save 67% Online Instantly!', 'Exclusive Offer for FL Residents', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8441', '2013-08-12 20:31:18', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Save 67% Online Instantly!', 'FL Residents Only', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8442', '2013-08-12 20:31:18', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8443', '2013-08-12 20:31:18', '2013-08-19 20:00:15', '$19 Accept Auto Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8444', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Aggresive Auto Insurance', '$19/Month Free Aggresive Quotes!', 'Exclusive Offer for FL Residents', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8445', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Aggresive Auto Insurance', '$19/Month Free Aggresive Quotes!', 'FL Residents Only', 'acceptance.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8446', '2013-08-12 20:31:18', '2013-08-19 20:00:15', '$19/Month Auto Insurance', 'Instant Discounts - Save up to 67%!', 'Exclusive Offer for FL Residents', 'amica.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8447', '2013-08-12 20:31:18', '2013-08-19 20:00:15', '$19/Month Auto Insurance', 'Instant Discounts - Save up to 67%!', 'FL Residents Only', 'amica.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8448', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Commerce Auto Insurance', 'Commerce Quotes from $19/Month!', 'Exclusive Offer for FL Residents', 'commerce.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8449', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Commerce Auto Insurance', 'Commerce Quotes from $19/Month!', 'FL Residents Only', 'commerce.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8450', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Cosco Auto Insurance', 'Save up to 67% with Online Quotes!', 'Exclusive Offer for FL Residents', 'costco.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8451', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Cosco Auto Insurance', 'Save up to 67% with Online Quotes!', 'FL Residents Only', 'costco.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8452', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Dairy Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for FL Residents', 'dairy-land.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8453', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Dairy Auto Insurance', 'Get Online Quotes from $19/Month!', 'FL Residents Only', 'dairy-land.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8454', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Del Toro Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for FL Residents', 'deltoro.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8455', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Del Toro Insurance', 'Get Online Quotes from $19/Month!', 'FL Residents Only', 'deltoro.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8456', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'En compass Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for FL Residents', 'encompass.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8457', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'En compass Auto Insurance', 'Get Online Quotes from $19/Month!', 'FL Residents Only', 'encompass.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8458', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Ensure Auto Insurance', 'Get Ensure Quotes from $19/Month!', 'Exclusive Offer for FL Residents', 'ensure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8459', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Ensure Auto Insurance', 'Get Ensure Quotes from $19/Month!', 'FL Residents Only', 'ensure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8460', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Estrella Auto Insurance', 'Get Cheapest Quotes from $19/Month!', 'Exclusive Offer for FL Residents', 'estrella.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8461', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Estrella Auto Insurance', 'Get Cheapest Quotes from $19/Month!', 'FL Residents Only', 'estrella.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8462', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Farm Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for FL Residents', 'farm.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8463', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Farm Auto Insurance', 'Get Online Quotes from $19/Month!', 'FL Residents Only', 'farm.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8464', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Frankenmuth Car Insurance', 'Get Frankenmuth Quotes from $19/mo!', 'Exclusive Offer for FL Residents', 'frankenmuth.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8465', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Frankenmuth Car Insurance', 'Get Frankenmuth Quotes from $19/mo!', 'FL Residents Only', 'frankenmuth.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8466', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Fred Car Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'fred.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8467', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Fred Car Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'fred.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8468', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Free Way Online Quotes', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for FL Residents', 'free.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8469', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Free Way Online Quotes', 'Free Online Quotes from $19/Month!', 'FL Residents Only', 'free.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8470', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Gary Auto Insurance', 'Cheapest Quotes from $19/mo!', 'Exclusive Offer for FL Residents', 'gary.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8471', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Gary Auto Insurance', 'Cheapest Quotes from $19/mo!', 'FL Residents Only', 'gary.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8472', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Insure Auto Insurance', 'Save up to 70% in 5 Minutes!', 'Exclusive Offer for FL Residents', 'insure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8473', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Insure Auto Insurance', 'Save up to 70% in 5 Minutes!', 'FL Residents Only', 'insure.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8474', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Katz Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for FL Residents', 'katz.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8475', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'Katz Auto Insurance', 'Get Online Quotes from $19/Month!', 'FL Residents Only', 'katz.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8476', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'LA Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for FL Residents', 'la.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8477', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'LA Auto Insurance', 'Get Online Quotes from $19/Month!', 'FL Residents Only', 'la.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8478', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'FL Liberty Quotes Online', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for FL Residents', 'liberty.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8479', '2013-08-12 20:31:18', '2013-08-19 20:00:15', 'FL Liberty Quotes Online', 'Get Online Quotes from $19/Month!', 'FL Residents Only', 'liberty.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8480', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'Loya Auto Insurance', 'Lowest Rates from $19/Month', 'Exclusive Offer for FL Residents', 'loya.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8481', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'Loya Auto Insurance', 'Lowest Rates from $19/Month', 'FL Residents Only', 'loya.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8482', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'Meemic Car Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'meemic.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8483', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'Meemic Car Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'meemic.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8484', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'Muncie Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'muncie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8485', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'Muncie Auto Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'muncie.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8486', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'Nation Car Insurance', 'Get Instant Quotes & Save 67%!', 'Exclusive Offer for FL Residents', 'nation.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8487', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'National Auto Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'national.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8488', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'Ocean Harbor Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'oceanharbor.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8489', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'Ocean Harbor Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'oceanharbor.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8490', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'Omni Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'omni.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8491', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'Omni Auto Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'omni.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8492', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'Premier Auto Insurance', 'Lowest Rates from $19/Month!', 'Exclusive Offer for FL Residents', 'premier.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8493', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'Premier Auto Insurance', 'Lowest Rates from $19/Month!', 'FL Residents Only', 'premier.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8494', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'General Auto Insurance', 'Get Instant Quotes & Save 67%!', 'Exclusive Offer for FL Residents', 'general.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8495', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'US Agencies Car Insurance', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for FL Residents', 'usagencies.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8496', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'US Agencies Car Insurance', 'Free Online Quotes from $19/Month!', 'FL Residents Only', 'usagencies.pgautoinsurance.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8497', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'USA Auto Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for FL Residents', 'usa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8498', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'USA Auto Insurance', 'Instant Discounts - Save 67%', 'FL Residents Only', 'usa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8499', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'USA Car Insurance', 'Get Online Quotes from $19/Month!', 'Exclusive Offer for FL Residents', 'usaa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8500', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'USA Car Insurance', 'Get Online Quotes from $19/Month!', 'FL Residents Only', 'usaa.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8501', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'Westfield Insurance', 'Free Online Quotes from $19/Month!', 'Exclusive Offer for FL Residents', 'westfield.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');
INSERT INTO `Text_Ad` VALUES ('8502', '2013-08-12 20:31:19', '2013-08-19 20:00:15', 'Westfield Insurance', 'Free Online Quotes from $19/Month!', 'FL Residents Only', 'westfield.Quotes2Compare.com', 'http://www.quotes2compare.com/welcome');

-- ----------------------------
-- Table structure for Traffic_Campaign
-- ----------------------------
DROP TABLE IF EXISTS `Traffic_Campaign`;
CREATE TABLE `Traffic_Campaign` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `name` varchar(255) NOT NULL,
  `sid` bigint(20) NOT NULL,
  `provider_supplied_id` bigint(20) DEFAULT NULL,
  `local_status` enum('active','paused','deleted') NOT NULL,
  `provider_status` enum('active','paused','deleted') DEFAULT NULL,
  `uploaded_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_uploaded` tinyint(1) NOT NULL,
  `campaign_budget_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `provider_supplied_id` (`provider_supplied_id`) USING BTREE,
  KEY `uploaded_ts` (`uploaded_ts`) USING BTREE,
  KEY `name` (`name`) USING BTREE,
  KEY `sid` (`sid`) USING BTREE,
  KEY `is_uploaded` (`is_uploaded`) USING BTREE,
  KEY `traffic_campaign_ibfk_2` (`campaign_budget_id`) USING BTREE,
  CONSTRAINT `Traffic_Campaign_ibfk_1` FOREIGN KEY (`campaign_budget_id`) REFERENCES `Campaign_Budget` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `Traffic_Campaign_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `Traffic_Source` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Traffic_Campaign
-- ----------------------------
INSERT INTO `Traffic_Campaign` VALUES ('1', '2013-11-04 00:00:00', '2013-08-19 20:00:02', 'Qts2Cmp.Brands.20131104', '1', null, 'active', null, '0000-00-00 00:00:00', '0', '1');

-- ----------------------------
-- Table structure for Traffic_Campaign_Geo_Target
-- ----------------------------
DROP TABLE IF EXISTS `Traffic_Campaign_Geo_Target`;
CREATE TABLE `Traffic_Campaign_Geo_Target` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `target_type` enum('positive','negative') NOT NULL,
  `traffic_campaign_id` bigint(20) NOT NULL,
  `criteria_id` bigint(20) NOT NULL,
  `local_status` enum('add','remove') NOT NULL,
  `provider_status` enum('add','remove') DEFAULT NULL,
  `targeting_status` enum('active','obsolete','phasing_out') DEFAULT NULL,
  `uploaded_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_uploaded` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `campaign_criteria` (`traffic_campaign_id`,`criteria_id`) USING BTREE,
  KEY `target_type` (`target_type`) USING BTREE,
  KEY `traffic_campaign_id` (`traffic_campaign_id`) USING BTREE,
  KEY `criteria_id` (`criteria_id`) USING BTREE,
  KEY `is_uploaded` (`is_uploaded`) USING BTREE,
  CONSTRAINT `Traffic_Campaign_Geo_Target_ibfk_1` FOREIGN KEY (`traffic_campaign_id`) REFERENCES `Traffic_Campaign` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=364 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Traffic_Campaign_Geo_Target
-- ----------------------------
INSERT INTO `Traffic_Campaign_Geo_Target` VALUES ('1', '2013-11-04 00:00:00', '2013-08-19 20:00:03', 'positive', '1', '2840', 'add', null, null, '0000-00-00 00:00:00', '0');
