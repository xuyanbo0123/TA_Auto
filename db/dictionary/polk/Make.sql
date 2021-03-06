SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `Make`
-- ----------------------------
DROP TABLE IF EXISTS `Make`;
CREATE TABLE `Make` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `make` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `make` (`make`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of Make
-- ----------------------------
INSERT INTO `Make` VALUES ('45', 'ACURA');
INSERT INTO `Make` VALUES ('1', 'ALFA ROMEO');
INSERT INTO `Make` VALUES ('69', 'AM GENERAL');
INSERT INTO `Make` VALUES ('2', 'AMERICAN MOTORS');
INSERT INTO `Make` VALUES ('67', 'ASTON MARTIN');
INSERT INTO `Make` VALUES ('3', 'AUDI');
INSERT INTO `Make` VALUES ('57', 'AVANTI');
INSERT INTO `Make` VALUES ('85', 'AZURE DYNAMICS');
INSERT INTO `Make` VALUES ('41', 'BENTLEY');
INSERT INTO `Make` VALUES ('4', 'BMW');
INSERT INTO `Make` VALUES ('81', 'BUGATTI');
INSERT INTO `Make` VALUES ('5', 'BUICK');
INSERT INTO `Make` VALUES ('6', 'CADILLAC');
INSERT INTO `Make` VALUES ('7', 'CHEVROLET');
INSERT INTO `Make` VALUES ('8', 'CHRYSLER');
INSERT INTO `Make` VALUES ('73', 'DAEWOO');
INSERT INTO `Make` VALUES ('53', 'DAIHATSU');
INSERT INTO `Make` VALUES ('9', 'DATSUN');
INSERT INTO `Make` VALUES ('77', 'DCX SPRINTER');
INSERT INTO `Make` VALUES ('10', 'DODGE');
INSERT INTO `Make` VALUES ('54', 'EAGLE');
INSERT INTO `Make` VALUES ('58', 'FEB13');
INSERT INTO `Make` VALUES ('11', 'FERRARI');
INSERT INTO `Make` VALUES ('12', 'FIAT');
INSERT INTO `Make` VALUES ('86', 'FISKER');
INSERT INTO `Make` VALUES ('13', 'FORD');
INSERT INTO `Make` VALUES ('71', 'FREIGHTLINER');
INSERT INTO `Make` VALUES ('74', 'GEM');
INSERT INTO `Make` VALUES ('59', 'GEO');
INSERT INTO `Make` VALUES ('72', 'GM');
INSERT INTO `Make` VALUES ('14', 'GMC');
INSERT INTO `Make` VALUES ('60', 'HINO');
INSERT INTO `Make` VALUES ('15', 'HONDA');
INSERT INTO `Make` VALUES ('78', 'HUMMER');
INSERT INTO `Make` VALUES ('46', 'HYUNDAI');
INSERT INTO `Make` VALUES ('65', 'INFINITI');
INSERT INTO `Make` VALUES ('84', 'INTERNATIONAL');
INSERT INTO `Make` VALUES ('16', 'ISUZU');
INSERT INTO `Make` VALUES ('17', 'IVECO');
INSERT INTO `Make` VALUES ('18', 'JAGUAR');
INSERT INTO `Make` VALUES ('55', 'JEEP');
INSERT INTO `Make` VALUES ('56', 'JOHN DEERE');
INSERT INTO `Make` VALUES ('70', 'KIA');
INSERT INTO `Make` VALUES ('61', 'LAFORZA');
INSERT INTO `Make` VALUES ('49', 'LAMBORGHINI');
INSERT INTO `Make` VALUES ('19', 'LANCIA');
INSERT INTO `Make` VALUES ('50', 'LAND ROVER');
INSERT INTO `Make` VALUES ('66', 'LEXUS');
INSERT INTO `Make` VALUES ('20', 'LINCOLN');
INSERT INTO `Make` VALUES ('38', 'LOTUS');
INSERT INTO `Make` VALUES ('42', 'MASERATI');
INSERT INTO `Make` VALUES ('80', 'MAYBACH');
INSERT INTO `Make` VALUES ('21', 'MAZDA');
INSERT INTO `Make` VALUES ('87', 'MCLAREN');
INSERT INTO `Make` VALUES ('22', 'MERCEDES-BENZ');
INSERT INTO `Make` VALUES ('23', 'MERCURY');
INSERT INTO `Make` VALUES ('43', 'MERKUR');
INSERT INTO `Make` VALUES ('76', 'MINI');
INSERT INTO `Make` VALUES ('39', 'MITSUBISHI');
INSERT INTO `Make` VALUES ('51', 'MITSUBISHI FUSO');
INSERT INTO `Make` VALUES ('44', 'NISSAN');
INSERT INTO `Make` VALUES ('62', 'NISSAN DIESEL');
INSERT INTO `Make` VALUES ('24', 'OLDSMOBILE');
INSERT INTO `Make` VALUES ('63', 'OSHKOSH');
INSERT INTO `Make` VALUES ('25', 'PEUGEOT');
INSERT INTO `Make` VALUES ('26', 'PLYMOUTH');
INSERT INTO `Make` VALUES ('27', 'PONTIAC');
INSERT INTO `Make` VALUES ('28', 'PORSCHE');
INSERT INTO `Make` VALUES ('88', 'RAM');
INSERT INTO `Make` VALUES ('29', 'RENAULT');
INSERT INTO `Make` VALUES ('79', 'ROADMASTER RAIL');
INSERT INTO `Make` VALUES ('30', 'ROLLS-ROYCE');
INSERT INTO `Make` VALUES ('31', 'SAAB');
INSERT INTO `Make` VALUES ('68', 'SATURN');
INSERT INTO `Make` VALUES ('82', 'SMART');
INSERT INTO `Make` VALUES ('52', 'STERLING');
INSERT INTO `Make` VALUES ('32', 'SUBARU');
INSERT INTO `Make` VALUES ('33', 'SUZUKI');
INSERT INTO `Make` VALUES ('83', 'TESLA');
INSERT INTO `Make` VALUES ('34', 'TOYOTA');
INSERT INTO `Make` VALUES ('35', 'TRIUMPH');
INSERT INTO `Make` VALUES ('47', 'TVR');
INSERT INTO `Make` VALUES ('64', 'UTILIMASTER');
INSERT INTO `Make` VALUES ('36', 'VOLKSWAGEN');
INSERT INTO `Make` VALUES ('37', 'VOLVO');
INSERT INTO `Make` VALUES ('40', 'WINNEBAGO');
INSERT INTO `Make` VALUES ('75', 'WORKHORSE');
INSERT INTO `Make` VALUES ('48', 'YUGO');
