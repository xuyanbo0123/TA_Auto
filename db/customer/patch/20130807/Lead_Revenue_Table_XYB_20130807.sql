CREATE TABLE `Lead_Revenue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lead_request_id` bigint(20) NOT NULL,
  `amount` decimal(9,3) DEFAULT NULL,
  `adjusted_amount` decimal(9,3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lead_request_id` (`lead_request_id`) USING BTREE,
  KEY `created_ts` (`created_ts`),
  KEY `updated_ts` (`updated_ts`),
  CONSTRAINT `Lead_Revenue_ibfk_1` FOREIGN KEY (`lead_request_id`) REFERENCES `Lead_Request` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;