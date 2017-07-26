-- ----------------------------
-- Table structure for `Environment_Variable`
-- ----------------------------
DROP TABLE IF EXISTS `Environment_Variable`;
CREATE TABLE `Environment_Variable` (
  `name` varchar(63) NOT NULL,
  `value` varchar(63) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Environment_Variable
-- ----------------------------
INSERT INTO `Environment_Variable` VALUES ('WORK_STATE', 'LOCAL_PRODUCTION');
INSERT INTO `Environment_Variable` VALUES ('JOB_LIST', 'ReportJob||0 0 4,20 * * ?;');