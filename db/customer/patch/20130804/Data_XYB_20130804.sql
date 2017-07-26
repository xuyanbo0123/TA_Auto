INSERT INTO `Buyer` VALUES ('4', '2013-08-04 00:00:00', '0000-00-00 00:00:00', 'aggregator', 'LibertyMutual', null);

INSERT INTO `Buyer_Account` VALUES ('5', '2013-08-04 00:00:00', '0000-00-00 00:00:00', '4', '3', 'LibertyMutualBenjamin', 'testing');

INSERT INTO `Buyer_Account_Config` VALUES ('1', '2013-08-04 00:00:00', '0000-00-00 00:00:00', '5', 'LEAD_RULE', '{\"children\": [\"State==MA\",\"Marital==_MARRIED\",\"Education==_SOME_COLLEGE|_BACHELORS_DEGREE|_MASTERS_DEGREE|_DOCTORATE_DEGREE\"]}', 'mailto:Benjamin.Ward@libertymutual.com', '5', '0', '100');
INSERT INTO `Buyer_Account_Config` VALUES ('2', '2013-08-04 00:00:00', '0000-00-00 00:00:00', '5', 'LEAD_RULE', '{\"children\": [\"State==MA\"]}', 'mailto:Benjamin.Ward@libertymutual.com', '5', '0', '101');
INSERT INTO `Buyer_Account_Config` VALUES ('3', '2013-08-04 00:00:00', '0000-00-00 00:00:00', '3', 'LEAD_RULE', '{\"children\": [\"true\"]}', 'http://', '-1', '0', '200');