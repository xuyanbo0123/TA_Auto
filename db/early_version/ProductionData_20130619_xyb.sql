-- ----------------------------
-- Records of email_replacement_text
-- ----------------------------
INSERT INTO `Email_Replacement_Text` VALUES ('1', 'LOGO_IMG_URL', 'http://www.ezwayhealthinsurance.com/images/ezwh/EZWay_Logo.png');
INSERT INTO `Email_Replacement_Text` VALUES ('2', 'CONTACT_US_URL', 'http://www.ezwayhealthinsurance.com/contact-us');
INSERT INTO `Email_Replacement_Text` VALUES ('3', 'HOME_PAGE_URL', 'http://www.ezwayhealthinsurance.com/');
INSERT INTO `Email_Replacement_Text` VALUES ('4', 'HEALTH_INSURANCE_BASICS_URL', 'http://www.ezwayhealthinsurance.com/health-insurance-basics');
INSERT INTO `Email_Replacement_Text` VALUES ('5', 'HEALTH_INSURANCE_FAQS_URL', 'http://www.ezwayhealthinsurance.com/health-insurance-faqs');
INSERT INTO `Email_Replacement_Text` VALUES ('6', 'PRIVACY_STATEMENT_URL', 'http://www.ezwayhealthinsurance.com/privacy-policy');

-- ----------------------------
-- Records of email_template
-- ----------------------------
INSERT INTO `Email_Template` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'ThankYouTemplate', 'Thank You - Your Request Has Been Received', '<div style=\"margin-left:auto; margin-right:auto; text-align:center;\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #000; font-family:Arial; font-size:15px; margin-left:auto; margin-right:auto; text-align: left;\">\r\n        <thead>\r\n            <tr style=\"background-color:#EEEEEE;\">\r\n                <td style=\"padding:0px; margin:0px; height:42px;\" colspan=\"4\">\r\n                    <img src=\"[[LOGO_IMG_URL]]\" alt=\"EZWayHealthInsurance Logo\" style=\"margin: 5px;\" />\r\n                </td>\r\n            </tr>\r\n        </thead>\r\n        <tbody>\r\n            <tr>\r\n                <td style=\"padding:10px;\" colspan=\"4\">\r\n                    <p style=\"font-size:18px; font-weight:bold;\">Dear [[FIRST_NAME]] [[LAST_NAME]],</p>\r\n                    <p>Thank you for using EZWayHealthInsurance.com. Your request has been submitted and you should be contacted by email or phone by a professional who can assist you with finding a plan that suits your needs</p>\r\n                    <p>Once again, thank you for choosing EZWayHealthInsurance.com and we hope to have the pleasure of serving you again in the future!</p>\r\n                    \r\n                    <p><strong>Cheers,</strong><br />\r\n                    The EZWay Health Insurance Team</p>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td style=\"margin:5px; background-color:#0084C6; text-align:center; border-right: 1px solid #fff;\">\r\n                    <a href=\"[[HOME_PAGE_URL]]\" style=\"color:#fff; display:block; text-decoration:underline;\">Visit Us</a>\r\n                </td>\r\n                <td style=\"margin:5px; background-color:#0084C6; text-align:center;border-right: 1px solid #fff;\">\r\n                    <a href=\"[[HEALTH_INSURANCE_BASICS_URL]]\" style=\"color:#fff; text-decoration:underline;\">Health Insurance Basics</a>\r\n                </td>\r\n                <td style=\"margin:5px; background-color:#0084C6; text-align:center;border-right: 1px solid #fff;\">\r\n                    <a href=\"[[HEALTH_INSURANCE_FAQS_URL]]\" style=\"color:#fff; text-decoration:underline;\">Health Insurance FAQs</a>\r\n                </td>\r\n                <td style=\"margin:5px; background-color:#0084C6; text-align:center;\">\r\n                    <a href=\"[[CONTACT_US_URL]]\" style=\"color:#fff; text-decoration:underline;\">Contact Us</a>\r\n                </td>\r\n            </tr>\r\n            \r\n        </tbody>\r\n    </table>\r\n</div>\r\n<p style=\"margin:10px; height:20px;font-family:Arial; text-align:center; font-size:10px; color:#999; line-height:18px;\">\r\n    EZWay Health Insurance respects your privacy. For more information, please review our <a href=\"[[PRIVACY_STATEMENT_URL]]\">Privacy Policy</a>\r\n</p>', 'active');

-- ----------------------------
-- Records of email_from
-- ----------------------------
INSERT INTO `Email_From` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'EZWay Health Insurance', 'support@ezwayhealthinsurance.com', 'CustomerServiceDepartment');

-- ----------------------------
-- Records of email_program
-- ----------------------------
INSERT INTO `Email_Program` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'NewUserForHealthProgram');

-- ----------------------------
-- Records of email_program_step
-- ----------------------------
INSERT INTO `Email_Program_Step` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', '1', 'ThankYouStep', '1', '1');

-- ----------------------------
-- Records of email_rule
-- ----------------------------
INSERT INTO `Email_Rule` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'TimeRule', '0');

-- ----------------------------
-- Records of email_program_step_rule_map
-- ----------------------------
INSERT INTO `Email_Program_Step_Rule_Map` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', '1', '1', 'active');

-- ----------------------------
-- Records of email_role
-- ----------------------------
INSERT INTO `Email_Role` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'NewUserForHealth', '1', null);

-- ----------------------------
-- Records of email_record_property_field
-- ----------------------------
INSERT INTO `Email_Record_Property_Field` VALUES ('1', 'FIRST_NAME');
INSERT INTO `Email_Record_Property_Field` VALUES ('2', 'LAST_NAME');

-- ----------------------------
-- Records of lead_type
-- ----------------------------
INSERT INTO `Lead_Type` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'NoLeadTypeFound', 'used for error');
INSERT INTO `Lead_Type` VALUES ('2', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'Health', 'used for health');

-- ----------------------------
-- Records of buyer
-- ----------------------------
INSERT INTO `Buyer` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'unknown', 'TestBuyer', null);

-- ----------------------------
-- Records of buyer_account
-- ----------------------------
INSERT INTO `Buyer_Account` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', '1', '2', 'TestBuyerAccount', 'testing');

-- ----------------------------
-- Records of Arrival_Tracking_Parameters
-- ----------------------------
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('1', 'ezp004', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('2', 'ezp016', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('3', 'ezp089', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('4', 'ezp186', 'ad_group_keyword_id', 'AdGroupKeyword ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('5', 'ezp867', 'query', 'Google search query');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('6', 'ezp249', 'query', 'Google search query');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('7', 'ezp009', 'query', 'Google search query');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('8', 'ezp601', 'query', 'Google search query');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('9', 'ezp056', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('10', 'ezp193', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('11', 'ezp399', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('12', 'ezp823', 'campaign_id', 'Traffic Campaign ID');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('13', 'ezp443', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('14', 'ezp206', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('15', 'ezp496', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('16', 'ezp952', 'creative_id', 'Google creative id');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('17', 'ezp387', 'match_type', 'Google match type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('18', 'ezp798', 'match_type', 'Google match type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('19', 'ezp883', 'match_type', 'Google match type');
INSERT INTO `Arrival_Tracking_Parameters` VALUES ('20', 'ezp571', 'match_type', 'Google match type');

-- ----------------------------
-- Records of Web_Page
-- ----------------------------
INSERT INTO `Web_Page` VALUES ('1', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'NoUriFound');
INSERT INTO `Web_Page` VALUES ('2', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/');
INSERT INTO `Web_Page` VALUES ('3', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics');
INSERT INTO `Web_Page` VALUES ('4', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/knowledge-of-hsa');
INSERT INTO `Web_Page` VALUES ('5', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/knowledge-of-hsa/142-why-should-i-consider-getting-an-hsa');
INSERT INTO `Web_Page` VALUES ('6', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/knowledge-of-hsa/141-what-insurance-plans-are-hsa-compatible');
INSERT INTO `Web_Page` VALUES ('7', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/knowledge-of-hsa/140-what-are-qualified-medical-expenses-for-hsa');
INSERT INTO `Web_Page` VALUES ('8', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/knowledge-of-hsa/139-what-are-my-investment-options-for-hsa');
INSERT INTO `Web_Page` VALUES ('9', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/knowledge-of-hsa/137-is-my-money-safe-in-hsa');
INSERT INTO `Web_Page` VALUES ('10', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/knowledge-of-hsa/136-how-much-can-i-contribute-to-my-hsa');
INSERT INTO `Web_Page` VALUES ('11', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/knowledge-of-hsa/135-how-do-the-tax-savings-work-for-hsa');
INSERT INTO `Web_Page` VALUES ('12', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/knowledge-of-hsa/134-how-do-i-use-the-funds-in-my-hsa');
INSERT INTO `Web_Page` VALUES ('13', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/knowledge-of-hsa/114-how-does-a-health-savings-account-work');
INSERT INTO `Web_Page` VALUES ('14', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/knowledge-of-hsa/110-what-is-an-hsa');
INSERT INTO `Web_Page` VALUES ('15', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/general-knowledge');
INSERT INTO `Web_Page` VALUES ('16', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/general-knowledge/124-what-should-i-know-if-i-have-insurance-through-work');
INSERT INTO `Web_Page` VALUES ('17', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/general-knowledge/123-what-should-i-know-if-i-have-individual-health-insurance');
INSERT INTO `Web_Page` VALUES ('18', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/general-knowledge/122-the-options-if-i-cant-get-coverage-through-work');
INSERT INTO `Web_Page` VALUES ('19', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/general-knowledge/121-what-services-are-covered-under-my-insurance-policy');
INSERT INTO `Web_Page` VALUES ('20', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/general-knowledge/120-what-you-should-know-about-filing-your-health-benefits-claim');
INSERT INTO `Web_Page` VALUES ('21', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/general-knowledge/118-the-options-for-job-based-coverage');
INSERT INTO `Web_Page` VALUES ('22', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/general-knowledge/117-types-of-plans-and-network-restrictions');
INSERT INTO `Web_Page` VALUES ('23', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/general-knowledge/112-your-insurance-company-costs-of-coverage');
INSERT INTO `Web_Page` VALUES ('24', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/general-knowledge/111-five-key-questions-to-help-you-assess-your-needs');
INSERT INTO `Web_Page` VALUES ('25', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/general-knowledge/109-five-health-insurance-terms-you-must-know');
INSERT INTO `Web_Page` VALUES ('26', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/general-knowledge/108-top-four-health-plan-types');
INSERT INTO `Web_Page` VALUES ('27', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/avoiding-pitfalls');
INSERT INTO `Web_Page` VALUES ('28', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/avoiding-pitfalls/119-whats-not-health-insurance');
INSERT INTO `Web_Page` VALUES ('29', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/knowledge-about-sbc');
INSERT INTO `Web_Page` VALUES ('30', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/knowledge-about-sbc/130-when-can-i-get-an-sbc');
INSERT INTO `Web_Page` VALUES ('31', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-basics/knowledge-about-sbc/129-whats-in-a-summary-of-benefits-and-coverage-sbc');
INSERT INTO `Web_Page` VALUES ('32', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs');
INSERT INTO `Web_Page` VALUES ('33', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/most-commonly-asked');
INSERT INTO `Web_Page` VALUES ('34', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/most-commonly-asked/161-why-you-should-compare-health-insurance-quotes');
INSERT INTO `Web_Page` VALUES ('35', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/most-commonly-asked/154-why-should-i-submit-my-application-through-ezway-health-insurance');
INSERT INTO `Web_Page` VALUES ('36', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/most-commonly-asked/153-what-tax-credits-are-available');
INSERT INTO `Web_Page` VALUES ('37', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/most-commonly-asked/151-what-if-my-plan-wont-pay-for-care-i-think-should-be-covered');
INSERT INTO `Web_Page` VALUES ('38', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/most-commonly-asked/150-what-can-i-do-if-i-was-rejected-because-of-a-pre-existing-condition-or-disability');
INSERT INTO `Web_Page` VALUES ('39', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/most-commonly-asked/147-how-much-should-my-insurance-plan-cost');
INSERT INTO `Web_Page` VALUES ('40', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/most-commonly-asked/146-how-do-i-know-if-my-plans-network-includes-high-quality-doctors-and-hospitals');
INSERT INTO `Web_Page` VALUES ('41', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/most-commonly-asked/145-how-can-i-find-out-what-is-covered-in-my-insurance-plan');
INSERT INTO `Web_Page` VALUES ('42', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/most-commonly-asked/143-can-i-keep-my-current-doctor');
INSERT INTO `Web_Page` VALUES ('43', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/most-commonly-asked/116-how-to-choose-a-plan');
INSERT INTO `Web_Page` VALUES ('44', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/most-commonly-asked/115-how-much-will-my-policy-cost-me');
INSERT INTO `Web_Page` VALUES ('45', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/additional-faqs');
INSERT INTO `Web_Page` VALUES ('46', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/additional-faqs/155-how-does-individual-and-family-coverage-compare-with-employer-sponsored-coverage');
INSERT INTO `Web_Page` VALUES ('47', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/additional-faqs/149-what-can-i-do-if-i-was-rejected-because-i-am-pregnant');
INSERT INTO `Web_Page` VALUES ('48', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/health-insurance-faqs/additional-faqs/125-what-if-im-losing-work-based-coverage');
INSERT INTO `Web_Page` VALUES ('49', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/about-us');
INSERT INTO `Web_Page` VALUES ('50', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/contact-us');
INSERT INTO `Web_Page` VALUES ('51', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/get-quote');
INSERT INTO `Web_Page` VALUES ('52', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/privacy-policy');
INSERT INTO `Web_Page` VALUES ('53', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/terms-of-use');
INSERT INTO `Web_Page` VALUES ('54', '2013-06-01 00:00:01', '0000-00-00 00:00:00', 'www.ezwayhealthinsurance.com/redirect');