SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `Keyword_Candidate`
-- ----------------------------
DROP TABLE IF EXISTS `Keyword_Candidate`;
CREATE TABLE `Keyword_Candidate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(511) NOT NULL,
  `essence` varchar(511) DEFAULT NULL,
  `essence2` varchar(511) DEFAULT NULL,
  `essence3` varchar(511) DEFAULT NULL,
  `group` varchar(511) DEFAULT NULL,
  `campaign_name` varchar(511) DEFAULT NULL,
  `budget_name` varchar(255) DEFAULT NULL,
  `bid_amount` int(11) DEFAULT NULL,
  `is_created` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `is_created` (`is_created`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `Text_Ad_Template`
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
  UNIQUE KEY `unique_key` (`headline`,`description1`,`description2`,`displayUrl`,`actionUrl`,`group`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;