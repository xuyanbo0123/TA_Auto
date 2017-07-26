SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `New_Keyword`
-- ----------------------------
DROP TABLE IF EXISTS `Google_New_Keyword`;
CREATE TABLE `Google_New_Keyword` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `budget_name` varchar(127) NOT NULL,
  `campaign_name` varchar(255) NOT NULL,
  `ad_group_name` varchar(127) NOT NULL,
  `keyword` varchar(511) NOT NULL,
  `is_biddable` tinyint(1) NOT NULL,
  `match_type` enum('exact','broad','phrase') NOT NULL,
  `bid_amount` decimal(8,3) NULL DEFAULT NULL,
  `is_created` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `campaign_name` (`campaign_name`),
  KEY `ad_group_name` (`ad_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `Google_New_Ad`
-- ----------------------------
DROP TABLE IF EXISTS `Google_New_Ad`;
CREATE TABLE `Google_New_Ad` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `budget_name` varchar(127) NOT NULL,
  `campaign_name` varchar(255) NOT NULL,
  `ad_group_name` varchar(127) NOT NULL,
  `headline` varchar(25) NOT NULL,
  `description1` varchar(35) NOT NULL,
  `description2` varchar(35) NOT NULL,
  `displayUrl` varchar(35) NOT NULL,
  `actionUrl` varchar(60) NOT NULL,
  `is_created` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;