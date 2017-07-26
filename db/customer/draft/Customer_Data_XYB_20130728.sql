-- ----------------------------
-- Records of email_replacement_text
-- ----------------------------
INSERT INTO `Email_Replacement_Text` VALUES ('1', 'AUTO_INSURANCE_LOGO_IMG_URL', 'http://www.proguideautoinsurance.com/images/pgauto/ProGuideLogo.png');
INSERT INTO `Email_Replacement_Text` VALUES ('2', 'AUTO_INSURANCE_CONTACT_US_URL', 'http://www.proguideautoinsurance.com/contact-us');
INSERT INTO `Email_Replacement_Text` VALUES ('3', 'AUTO_INSURANCE_HOME_PAGE_URL', 'http://www.proguideautoinsurance.com/');
INSERT INTO `Email_Replacement_Text` VALUES ('4', 'AUTO_INSURANCE_INSURANCE_LIBRARY_URL', 'http://www.proguideautoinsurance.com/insurance-library');
INSERT INTO `Email_Replacement_Text` VALUES ('5', 'AUTO_INSURANCE_DMV_LIBRARY_URL', 'http://www.proguideautoinsurance.com/dmv-library');
INSERT INTO `Email_Replacement_Text` VALUES ('6', 'AUTO_INSURANCE_PRIVACY_STATEMENT_URL', 'http://www.proguideautoinsurance.com/privacy-policy');

-- ----------------------------
-- Records of email_template
-- ----------------------------
INSERT INTO `Email_Template` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'ThankYouTemplateForHealthInsurance', 'Thank You - Your Request Has Been Received', ' ', 'active');
INSERT INTO `Email_Template` VALUES ('2', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'ThankYouTemplateForAutoInsurance', 'Thank You - Your Request Has Been Received', '<div style=\"margin-left:auto; margin-right:auto; text-align:center;\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #000; font-family:Arial; font-size:15px; margin-left:auto; margin-right:auto; text-align: left;\">\r\n        <thead>\r\n            <tr style=\"background-color:#EEEEEE;\">\r\n                <td style=\"padding:0px; margin:0px; height:42px;\" colspan=\"4\">\r\n                    <img src=\"[[AUTO_INSURANCE_LOGO_IMG_URL]]\" alt=\"ProGuideAutoInsurance Logo\" style=\"margin: 5px;\" />\r\n                </td>\r\n            </tr>\r\n        </thead>\r\n        <tbody>\r\n            <tr>\r\n                <td style=\"padding:10px;\" colspan=\"4\">\r\n                    <p style=\"font-size:18px; font-weight:bold;\">Dear [[FIRST_NAME]] [[LAST_NAME]],</p>\r\n                    <p>Thank you for using ProGuideAutoInsurance.com. Your request has been submitted and you should be contacted by email or phone by a professional who can assist you with finding a plan that suits your needs</p>\r\n                    <p>Once again, thank you for choosing ProGuideAutoInsurance.com and we hope to have the pleasure of serving you again in the future!</p>\r\n                    \r\n                    <p><strong>Cheers,</strong><br />\r\n                    The ProGuide Auto Insurance Team</p>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td style=\"margin:5px; background-color:#0084C6; text-align:center; border-right: 1px solid #fff;\">\r\n                    <a href=\"[[AUTO_INSURANCE_HOME_PAGE_URL]]\" style=\"color:#fff; display:block; text-decoration:underline;\">Visit Us</a>\r\n                </td>\r\n                <td style=\"margin:5px; background-color:#0084C6; text-align:center;border-right: 1px solid #fff;\">\r\n                    <a href=\"[[AUTO_INSURANCE_INSURANCE_LIBRARY_URL]]\" style=\"color:#fff; text-decoration:underline;\">Insurance Library</a>\r\n                </td>\r\n                <td style=\"margin:5px; background-color:#0084C6; text-align:center;border-right: 1px solid #fff;\">\r\n                    <a href=\"[[AUTO_INSURANCE_DMV_LIBRARY_URL]]\" style=\"color:#fff; text-decoration:underline;\">DMV Library</a>\r\n                </td>\r\n                <td style=\"margin:5px; background-color:#0084C6; text-align:center;\">\r\n                    <a href=\"[[AUTO_INSURANCE_CONTACT_US_URL]]\" style=\"color:#fff; text-decoration:underline;\">Contact Us</a>\r\n                </td>\r\n            </tr>\r\n            \r\n        </tbody>\r\n    </table>\r\n</div>\r\n<p style=\"margin:10px; height:20px;font-family:Arial; text-align:center; font-size:10px; color:#999; line-height:18px;\">\r\n    ProGuide Auto Insurance respects your privacy. For more information, please review our <a href=\"[[AUTO_INSURANCE_PRIVACY_STATEMENT_URL]]\">Privacy Policy</a>\r\n</p>', 'active');
-- ----------------------------
-- Records of email_from
-- ----------------------------
INSERT INTO `Email_From` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'ProGuide Health Insurance', 'support@proguidehealthinsurance.com', 'CustomerServiceDepartment');
INSERT INTO `Email_From` VALUES ('2', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'ProGuide Auto Insurance', 'support@proguideautoinsurance.com', 'CustomerServiceDepartment');

-- ----------------------------
-- Records of email_program
-- ----------------------------
INSERT INTO `Email_Program` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'NewUserForHealthInsuranceProgram');
INSERT INTO `Email_Program` VALUES ('2', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'NewUserForAutoInsuranceProgram');

-- ----------------------------
-- Records of email_program_step
-- ----------------------------
INSERT INTO `Email_Program_Step` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', '1', 'ThankYouStepForHealthInsurance', '1', '1');
INSERT INTO `Email_Program_Step` VALUES ('2', '2013-06-01 00:00:01', '0000-00-00 00:00:00', '2', 'ThankYouStepForAutoInsurance', '2', '2');

-- ----------------------------
-- Records of email_rule
-- ----------------------------
INSERT INTO `Email_Rule` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'TimeRule', '0');

-- ----------------------------
-- Records of email_program_step_rule_map
-- ----------------------------
INSERT INTO `Email_Program_Step_Rule_Map` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', '1', '1', 'active');
INSERT INTO `Email_Program_Step_Rule_Map` VALUES ('2', '2013-06-01 00:00:01', '0000-00-00 00:00:00', '2', '1', 'active');

-- ----------------------------
-- Records of email_role
-- ----------------------------
INSERT INTO `Email_Role` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'NewUserForHealthInsurance', '1', null);
INSERT INTO `Email_Role` VALUES ('2', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'NewUserForAutoInsurance', '2', null);

-- ----------------------------
-- Records of email_record_property_field
-- ----------------------------
INSERT INTO `Email_Record_Property_Field` VALUES ('1', 'FIRST_NAME');
INSERT INTO `Email_Record_Property_Field` VALUES ('2', 'LAST_NAME');

-- ----------------------------
-- Records of lead_type
-- ----------------------------
INSERT INTO `Lead_Type` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'NoLeadTypeFound', 'Used For Error');
INSERT INTO `Lead_Type` VALUES ('2', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'HealthInsurance', 'Used For Health Insurance');
INSERT INTO `Lead_Type` VALUES ('3', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'AutoInsurance', 'Used For Auto Insurance');

-- ----------------------------
-- Records of buyer
-- ----------------------------
INSERT INTO `Buyer` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'unknown', 'TestBuyer', null);
INSERT INTO `Buyer` VALUES ('2', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'aggregator', 'WebJuice', null);
INSERT INTO `Buyer` VALUES ('3', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'aggregator', 'SureHits', null);

-- ----------------------------
-- Records of buyer_account
-- ----------------------------
INSERT INTO `Buyer_Account` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', '1', '2', 'TestBuyerAccountForHealthInsurance', 'testing');
INSERT INTO `Buyer_Account` VALUES ('2', '2013-06-01 00:00:01', '0000-00-00 00:00:00', '1', '3', 'TestBuyerAccountForAutoInsurance', 'testing');
INSERT INTO `Buyer_Account` VALUES ('3', '2013-06-01 00:00:01', '0000-00-00 00:00:00', '2', '3', 'WebJuiceForAutoInsurance', 'production');
INSERT INTO `Buyer_Account` VALUES ('4', '2013-06-01 00:00:01', '0000-00-00 00:00:00', '3', '3', 'SureHitsForAutoInsurance', 'production');

-- ----------------------------
-- Records of Arrival_Tracking_Parameters
-- ----------------------------
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('1', 'tap004', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('2', 'tap016', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('3', 'tap089', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('4', 'tap186', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('5', 'tap867', 'keyword', 'Google keyword');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('6', 'tap249', 'keyword', 'Google keyword');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('7', 'tap009', 'keyword', 'Google keyword');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('8', 'tap601', 'keyword', 'Google keyword');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('9', 'tap056', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('10', 'tap193', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('11', 'tap399', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('12', 'tap823', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('13', 'tap443', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('14', 'tap206', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('15', 'tap496', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('16', 'tap952', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('17', 'tap387', 'match_type', 'Google match type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('18', 'tap798', 'match_type', 'Google match type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('19', 'tap883', 'match_type', 'Google match type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('20', 'tap571', 'match_type', 'Google match type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('21', 'tap711', 'adposition', 'Google adposition');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('22', 'tap784', 'adposition', 'Google adposition');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('23', 'tap777', 'adposition', 'Google adposition');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('24', 'tap709', 'adposition', 'Google adposition');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('25', 'tap927', 'src_type', 'Google src type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('26', 'tap933', 'src_type', 'Google src type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('27', 'tap979', 'src_type', 'Google src type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('28', 'tap968', 'src_type', 'Google src type');

-- ----------------------------
-- Records of Web_Page
-- ----------------------------
INSERT INTO `Web_Page` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'NoUriFound');

-- ----------------------------
-- Records of traffic_provider
-- ----------------------------
INSERT INTO `Traffic_Provider` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'Google');

-- ----------------------------
-- Records of traffic_source
-- ----------------------------
INSERT INTO `Traffic_Source` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', '1', 'sem', 'GoogleSearch');

-- ----------------------------
-- Records of Carrier_List
-- ----------------------------
INSERT INTO `Carrier_List` VALUES ('1', '21st Century Insurance', '21st-century-insurance');
INSERT INTO `Carrier_List` VALUES ('2', 'AAA', 'aaa');
INSERT INTO `Carrier_List` VALUES ('3', 'Alfa Insurance', 'alfa-insurance');
INSERT INTO `Carrier_List` VALUES ('4', 'Allstate', 'allstate');
INSERT INTO `Carrier_List` VALUES ('5', 'American Family Insurance', 'american-family-insurance');
INSERT INTO `Carrier_List` VALUES ('6', 'Ameriprise', 'ameriprise');
INSERT INTO `Carrier_List` VALUES ('7', 'Amica Mutual', 'amica-mutual');
INSERT INTO `Carrier_List` VALUES ('8', 'Auto-Owners Insurance', 'auto-owners-insurance');
INSERT INTO `Carrier_List` VALUES ('9', 'Commerce', 'commerce');
INSERT INTO `Carrier_List` VALUES ('10', 'Country', 'country');
INSERT INTO `Carrier_List` VALUES ('11', 'Encompass', 'encompass');
INSERT INTO `Carrier_List` VALUES ('12', 'Erie Insurance', 'erie-insurance');
INSERT INTO `Carrier_List` VALUES ('13', 'Esurance', 'esurance');
INSERT INTO `Carrier_List` VALUES ('14', 'Farmers', 'farmers');
INSERT INTO `Carrier_List` VALUES ('15', 'GEICO', 'geico');
INSERT INTO `Carrier_List` VALUES ('16', 'GMAC', 'gmac');
INSERT INTO `Carrier_List` VALUES ('17', 'Grange Insurance', 'grange-insurance');
INSERT INTO `Carrier_List` VALUES ('18', 'Kemper', 'kemper');
INSERT INTO `Carrier_List` VALUES ('19', 'Liberty Mutual', 'liberty-mutual');
INSERT INTO `Carrier_List` VALUES ('20', 'Mercury', 'mercury');
INSERT INTO `Carrier_List` VALUES ('21', 'MetLife', 'metlife');
INSERT INTO `Carrier_List` VALUES ('22', 'Nationwide', 'nationwide');
INSERT INTO `Carrier_List` VALUES ('23', 'New Jersey Manufacture Insurance Co.', 'new-jersey-manufacture-insurance-co');
INSERT INTO `Carrier_List` VALUES ('24', 'Plymouth Rock Assurance', 'plymouth-rock-assurance');
INSERT INTO `Carrier_List` VALUES ('25', 'Progressive', 'progressive');
INSERT INTO `Carrier_List` VALUES ('26', 'Safe Auto', 'safe-auto');
INSERT INTO `Carrier_List` VALUES ('27', 'Safeco', 'safeco');
INSERT INTO `Carrier_List` VALUES ('28', 'Shelter', 'shelter');
INSERT INTO `Carrier_List` VALUES ('29', 'State Auto', 'state-auto');
INSERT INTO `Carrier_List` VALUES ('30', 'State Farm', 'state-farm');
INSERT INTO `Carrier_List` VALUES ('31', 'The General', 'the-general');
INSERT INTO `Carrier_List` VALUES ('32', 'The Hanover', 'the-hanover');
INSERT INTO `Carrier_List` VALUES ('33', 'The Hartford', 'the-hartford');
INSERT INTO `Carrier_List` VALUES ('34', 'Travelers', 'travelers');
INSERT INTO `Carrier_List` VALUES ('35', 'USAA', 'usaa');
INSERT INTO `Carrier_List` VALUES ('36', 'Allied', 'allied');
INSERT INTO `Carrier_List` VALUES ('37', 'Arbella', 'arbella');
INSERT INTO `Carrier_List` VALUES ('38', 'Bristol West', 'bristol-west');
INSERT INTO `Carrier_List` VALUES ('39', 'Eastwood Insurance', 'eastwood-insurance');
INSERT INTO `Carrier_List` VALUES ('40', 'Farm Bureau Financial Services', 'farm-bureau-financial-services');
INSERT INTO `Carrier_List` VALUES ('41', 'Safety Insurance', 'safety-insurance');
INSERT INTO `Carrier_List` VALUES ('42', 'Wawanesa', 'wawanesa');