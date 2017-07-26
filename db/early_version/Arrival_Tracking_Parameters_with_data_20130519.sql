/*
Navicat MySQL Data Transfer

Source Server         : MacMini-nonRoot
Source Server Version : 50524
Source Host           : 192.168.1.161:3306
Source Database       : All

Target Server Type    : MYSQL
Target Server Version : 50524
File Encoding         : 65001

Date: 2013-05-19 16:39:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `Arrival_Tracking_Parameters`
-- ----------------------------
DROP TABLE IF EXISTS `Arrival_Tracking_Parameters`;
CREATE TABLE `Arrival_Tracking_Parameters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url_name` varchar(63) NOT NULL,
  `db_name` varchar(127) NOT NULL,
  `comment` text,
  PRIMARY KEY (`id`,`url_name`,`db_name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Arrival_Tracking_Parameters
-- ----------------------------
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('1', 'ezp004', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('2', 'ezp016', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('3', 'ezp089', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('4', 'ezp186', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('5', 'ezp867', 'query', 'Google search query');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('6', 'ezp249', 'query', 'Google search query');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('7', 'ezp009', 'query', 'Google search query');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('8', 'ezp601', 'query', 'Google search query');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('9', 'ezp056', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('10', 'ezp193', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('11', 'ezp399', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('12', 'ezp823', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('13', 'ezp443', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('14', 'ezp206', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('15', 'ezp496', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('16', 'ezp952', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('17', 'ezp387', 'match_type', 'Google match type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('18', 'ezp798', 'match_type', 'Google match type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('19', 'ezp883', 'match_type', 'Google match type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('20', 'ezp571', 'match_type', 'Google match type');
