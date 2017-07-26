-- ----------------------------
-- Table structure for Address_Verify_Melissa_Data_Accounts
-- ----------------------------
DROP TABLE IF EXISTS `Address_Verify_Melissa_Data_Accounts`;
CREATE TABLE `Address_Verify_Melissa_Data_Accounts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Address_Verify_Melissa_Data_Accounts
-- ----------------------------
INSERT INTO `Address_Verify_Melissa_Data_Accounts` VALUES ('1', 'xavierk@trendanalytical.com', '7R8d2B_.Ika&DXs');
INSERT INTO `Address_Verify_Melissa_Data_Accounts` VALUES ('2', 'alexk@trendanalytical.com', '7R8d2B_.Ika&DXs');
INSERT INTO `Address_Verify_Melissa_Data_Accounts` VALUES ('3', 'joshuak@trendanalytical.com', '7R8d2B_.Ika&DXs');



-- ----------------------------
-- Table structure for Address_Verify_Melissa_Data_Records
-- ----------------------------
DROP TABLE IF EXISTS `Address_Verify_Melissa_Data_Records`;
CREATE TABLE `Address_Verify_Melissa_Data_Records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `melissa_data_account_id` bigint(20) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `melissa_data_account_id` (`melissa_data_account_id`,`date`) USING BTREE,
  CONSTRAINT `address_verify_melissa_data_records_ibfk_1` FOREIGN KEY (`melissa_data_account_id`) REFERENCES `Address_Verify_Melissa_Data_Accounts` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;