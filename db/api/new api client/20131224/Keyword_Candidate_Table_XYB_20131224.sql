-- ----------------------------
-- Table structure for `Keyword_Candidate`
-- ----------------------------
DROP TABLE IF EXISTS `Keyword_Candidate`;
CREATE TABLE `Keyword_Candidate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(511) NOT NULL,
  `essence` varchar(511) NULL DEFAULT NULL,
  `type` enum('brand','tom') NULL DEFAULT NULL,
  `is_created` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `type` (`type`),
  KEY `is_created` (`is_created`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;