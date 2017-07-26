-- ----------------------------
-- Records of Ad_Group
-- ----------------------------
INSERT INTO `Ad_Group` VALUES ('201', '2013-11-24 00:00:00', '2013-11-24 00:00:00', '1', 'gary.20131124', 'enabled', null, null, '0000-00-00 00:00:00', '0');
INSERT INTO `Ad_Group` VALUES ('202', '2013-11-24 00:00:00', '2013-11-24 00:00:00', '1', 'frankenmuth.20131124', 'enabled', null, null, '0000-00-00 00:00:00', '0');

-- ----------------------------
-- Records of Text_Ad
-- ----------------------------
INSERT INTO `Text_Ad` VALUES ('201', '2013-11-24 00:00:00', '2013-11-24 00:00:00', 'Gary Auto Insurance', 'Get Gary Insurance Quotes Online', 'Cheapest Rates from $19/Mo!', 'Gary.Quotes2Compare.com', 'http://www.quotes2compare.com/');
INSERT INTO `Text_Ad` VALUES ('202', '2013-11-24 00:00:00', '2013-11-24 00:00:00', 'Frankenmuth Car Insurance', 'Get Frankenmuth Quotes from $19/Mo!', 'Instant Online Savings up to 75%', 'Frankenmuth.Quotes2Compare.com', 'http://www.quotes2compare.com/');

-- ----------------------------
-- Records of Ad_Group_Ad
-- ----------------------------
INSERT INTO `Ad_Group_Ad` VALUES ('301', '2013-11-24 00:00:00', '2013-11-24 00:00:00', '201', '201', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');
INSERT INTO `Ad_Group_Ad` VALUES ('302', '2013-11-24 00:00:00', '2013-11-24 00:00:00', '202', '202', null, '0000-00-00 00:00:00', 'enabled', null, null, '0');

-- ----------------------------
-- Records of Keyword
-- ----------------------------
INSERT INTO `Keyword` VALUES ('301', '2013-11-24 00:00:00', '2013-11-24 00:00:00', 'nationwide');
INSERT INTO `Keyword` VALUES ('302', '2013-11-24 00:00:00', '2013-11-24 00:00:00', 'nationwide insurance');
INSERT INTO `Keyword` VALUES ('303', '2013-11-24 00:00:00', '2013-11-24 00:00:00', 'liberty mutual');
INSERT INTO `Keyword` VALUES ('304', '2013-11-24 00:00:00', '2013-11-24 00:00:00', 'mutual liberty');
INSERT INTO `Keyword` VALUES ('305', '2013-11-24 00:00:00', '2013-11-24 00:00:00', 'liberty mutual insurance');
INSERT INTO `Keyword` VALUES ('306', '2013-11-24 00:00:00', '2013-11-24 00:00:00', 'gary\'s insurance company');

-- ----------------------------
-- Records of Ad_Group_Keyword
-- ----------------------------
INSERT INTO `Ad_Group_Keyword` VALUES ('301', '2013-11-24 00:00:00', '2013-11-24 00:00:00', '24', '301', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '8', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('302', '2013-11-24 00:00:00', '2013-11-24 00:00:00', '24', '302', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '8', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('303', '2013-11-24 00:00:00', '2013-11-24 00:00:00', '21', '303', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '8', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('304', '2013-11-24 00:00:00', '2013-11-24 00:00:00', '21', '304', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '8', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('305', '2013-11-24 00:00:00', '2013-11-24 00:00:00', '21', '305', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '8', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('306', '2013-11-24 00:00:00', '2013-11-24 00:00:00', '201', '306', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '8', '0');
INSERT INTO `Ad_Group_Keyword` VALUES ('307', '2013-11-24 00:00:00', '2013-11-24 00:00:00', '202', '187', 'exact', null, '0000-00-00 00:00:00', 'active', null, null, null, 'biddable', 'cpc', '8', '0');
