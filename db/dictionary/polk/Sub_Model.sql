SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `Sub_Model`
-- ----------------------------
DROP TABLE IF EXISTS `Sub_Model`;
CREATE TABLE `Sub_Model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sub_model` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sub_model` (`sub_model`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of Sub_Model
-- ----------------------------
INSERT INTO `Sub_Model` VALUES ('38', '3 DOOR COUPE');
INSERT INTO `Sub_Model` VALUES ('36', '3 DOOR EXT CAB PK');
INSERT INTO `Sub_Model` VALUES ('37', '4 DOOR EXT CAB PK');
INSERT INTO `Sub_Model` VALUES ('35', '4 DR EXT CAB / CHASS');
INSERT INTO `Sub_Model` VALUES ('34', 'BUS');
INSERT INTO `Sub_Model` VALUES ('13', 'CAB AND CHASSIS');
INSERT INTO `Sub_Model` VALUES ('17', 'CARGO VAN');
INSERT INTO `Sub_Model` VALUES ('23', 'CLUB CAB PICKUP');
INSERT INTO `Sub_Model` VALUES ('32', 'CLUB CHASSIS');
INSERT INTO `Sub_Model` VALUES ('39', 'CONVENTIONAL CAB');
INSERT INTO `Sub_Model` VALUES ('24', 'CONVERTIBLE');
INSERT INTO `Sub_Model` VALUES ('10', 'COUPE');
INSERT INTO `Sub_Model` VALUES ('30', 'COUPE 4 DOOR');
INSERT INTO `Sub_Model` VALUES ('14', 'CREW CHASSIS');
INSERT INTO `Sub_Model` VALUES ('15', 'CREW PICKUP');
INSERT INTO `Sub_Model` VALUES ('19', 'CUTAWAY');
INSERT INTO `Sub_Model` VALUES ('26', 'EXTENDED CARGO VAN');
INSERT INTO `Sub_Model` VALUES ('25', 'EXTENDED SPORT VAN');
INSERT INTO `Sub_Model` VALUES ('7', 'HATCHBACK 2 DOOR');
INSERT INTO `Sub_Model` VALUES ('16', 'HATCHBACK 4 DOOR');
INSERT INTO `Sub_Model` VALUES ('31', 'HEARSE');
INSERT INTO `Sub_Model` VALUES ('18', 'INCOMPLETE CHASSIS');
INSERT INTO `Sub_Model` VALUES ('11', 'INCOMPLETE PASSENGER');
INSERT INTO `Sub_Model` VALUES ('12', 'LIMOUSINE');
INSERT INTO `Sub_Model` VALUES ('21', 'MOTORIZED HOME');
INSERT INTO `Sub_Model` VALUES ('8', 'PICKUP');
INSERT INTO `Sub_Model` VALUES ('2', 'ROADSTER');
INSERT INTO `Sub_Model` VALUES ('1', 'SEDAN 2 DOOR');
INSERT INTO `Sub_Model` VALUES ('5', 'SEDAN 4 DOOR');
INSERT INTO `Sub_Model` VALUES ('29', 'SEDAN 5 DOOR');
INSERT INTO `Sub_Model` VALUES ('40', 'SPORT PICKUP');
INSERT INTO `Sub_Model` VALUES ('22', 'SPORT VAN');
INSERT INTO `Sub_Model` VALUES ('6', 'STATION WAGON');
INSERT INTO `Sub_Model` VALUES ('20', 'STEP VAN');
INSERT INTO `Sub_Model` VALUES ('28', 'TILT CAB');
INSERT INTO `Sub_Model` VALUES ('33', 'TRACTOR TRUCK');
INSERT INTO `Sub_Model` VALUES ('4', 'UTILITY');
INSERT INTO `Sub_Model` VALUES ('27', 'VAN CAMPER');
INSERT INTO `Sub_Model` VALUES ('3', 'WAGON 2 DOOR');
INSERT INTO `Sub_Model` VALUES ('9', 'WAGON 4 DOOR');
