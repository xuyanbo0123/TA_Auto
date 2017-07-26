/*
Navicat MySQL Data Transfer

Source Server         : Amazon_Slave_Box
Source Server Version : 50531
Source Host           : localhost:3306
Source Database       : FetchTheQuote

Target Server Type    : MYSQL
Target Server Version : 50531
File Encoding         : 65001

Date: 2014-04-01 14:03:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for Arrival_Tracking_Parameters
-- ----------------------------
DROP TABLE IF EXISTS `Arrival_Tracking_Parameters`;
CREATE TABLE `Arrival_Tracking_Parameters` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url_name` varchar(63) NOT NULL,
  `db_name` varchar(127) NOT NULL,
  `comment` text,
  PRIMARY KEY (`id`,`url_name`,`db_name`),
  UNIQUE KEY `url_name` (`url_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of Arrival_Tracking_Parameters
-- ----------------------------
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('1', 'tap004', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('2', 'tap016', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('3', 'tap089', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('4', 'tap186', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('5', 'tap867', 'keyword', 'Google keyword');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('6', 'tap249', 'keyword', 'Google keyword');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('7', 'tap009', 'keyword', 'Google keyword');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('8', 'tap601', 'keyword', 'Google keyword');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('9', 'tap056', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('10', 'tap193', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('11', 'tap399', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('12', 'tap823', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('13', 'tap443', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('14', 'tap206', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('15', 'tap496', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('16', 'tap952', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('17', 'tap387', 'match_type', 'Google match type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('18', 'tap798', 'match_type', 'Google match type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('19', 'tap883', 'match_type', 'Google match type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('20', 'tap571', 'match_type', 'Google match type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('21', 'tap711', 'adposition', 'Google adposition');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('22', 'tap784', 'adposition', 'Google adposition');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('23', 'tap777', 'adposition', 'Google adposition');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('24', 'tap709', 'adposition', 'Google adposition');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('25', 'tap927', 'src_type', 'Google src type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('26', 'tap933', 'src_type', 'Google src type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('27', 'tap979', 'src_type', 'Google src type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('28', 'tap968', 'src_type', 'Google src type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('29', 'tap011', 'network', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('30', 'tap014', 'network', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('31', 'tap017', 'network', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('32', 'tap019', 'network', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('33', 'tap021', 'device', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('34', 'tap024', 'device', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('35', 'tap028', 'device', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('36', 'tap029', 'device', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('37', 'tap032', 'devicemodel', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('38', 'tap033', 'devicemodel', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('39', 'tap037', 'devicemodel', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('40', 'tap038', 'devicemodel', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('41', 'tap041', 'ifmobile', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('42', 'tap043', 'ifmobile', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('43', 'tap044', 'ifmobile', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('44', 'tap045', 'ifmobile', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('45', 'tap051', 'placement', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('46', 'tap053', 'placement', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('47', 'tap057', 'placement', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('48', 'tap058', 'placement', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('49', 'tap064', 'target', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('50', 'tap065', 'target', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('51', 'tap066', 'target', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('52', 'tap069', 'target', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('53', 'tap071', 'aceid', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('54', 'tap074', 'aceid', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('55', 'tap077', 'aceid', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('56', 'tap078', 'aceid', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('57', 'tap092', 'sid', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('58', 'tap098', 'sid', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('59', 'tap095', 'sid', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('60', 'tap094', 'sid', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('61', 'tap114', 'subid', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('62', 'tap117', 'subid', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('63', 'tap113', 'subid', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('64', 'tap118', 'subid', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('65', 'tap891', 'campaign_google_id', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('66', 'tap737', 'ad_group_google_id', null);
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('67', 'tap097', 'keyword_google_id', null);
