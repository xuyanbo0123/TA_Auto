/*
Navicat MySQL Data Transfer

Source Server         : localhost-mimactest
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : All

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2014-03-28 21:18:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for Mail_Chimp_Information
-- ----------------------------
DROP TABLE IF EXISTS `Mail_Chimp_Information`;
CREATE TABLE `Mail_Chimp_Information` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `email_address` varchar(127) NOT NULL,
  `email_domain` varchar(63) NOT NULL,
  `first_name` varchar(63) NOT NULL,
  `last_name` varchar(63) NOT NULL,
  `lead_request_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `email_domain` (`email_domain`) USING BTREE,
  KEY `lead_request_id` (`lead_request_id`) USING BTREE,
  CONSTRAINT `mailchimp_information_ibfk_1` FOREIGN KEY (`lead_request_id`) REFERENCES `Lead_Request` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Mail_Chimp_Template
-- ----------------------------
DROP TABLE IF EXISTS `Mail_Chimp_Template`;
CREATE TABLE `Mail_Chimp_Template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `site_name` varchar(127) NOT NULL,
  `type` enum('THANK_YOU') NOT NULL,
  `subject` varchar(127) NOT NULL,
  `content` text NOT NULL,
  `from_address` varchar(127) NOT NULL,
  `from_name` varchar(127) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `site_name` (`site_name`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Mail_Chimp_Template
-- ----------------------------
INSERT INTO `Mail_Chimp_Template` VALUES (null, '2014-03-28 16:30:56', '0000-00-00 00:00:00', 'Quotes2Compare', 'THANK_YOU', 'Thank You - Your Request Has Been Received', '<div style=\"margin-left:auto; margin-right:auto; text-align:center;\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #000; font-family:Arial; font-size:15px; margin-left:auto; margin-right:auto; text-align: left;\">\r\n        <thead>\r\n            <tr style=\"background-color:#EEEEEE;\">\r\n                <td style=\"padding:0px; margin:0px; height:42px;\" colspan=\"4\">\r\n                    <img src=\"[[AUTO_INSURANCE_LOGO_IMG_URL]]\" alt=\"Quotes2Compare Logo\" style=\"margin: 5px;\" />\r\n                </td>\r\n            </tr>\r\n        </thead>\r\n        <tbody>\r\n            <tr>\r\n                <td style=\"padding:10px;\" colspan=\"4\">\r\n                    <p style=\"font-size:18px; font-weight:bold;\">Dear [[FIRST_NAME]] [[LAST_NAME]],</p>\r\n                    <p>Thank you for using Quotes2Compare.com. Your request has been submitted and you should be contacted by email or phone by a professional who can assist you with finding a plan that suits your needs.</p>\r\n                    <p>Once again, thank you for choosing Quotes2Compare.com and we hope to have the pleasure of serving you again in the future!</p>\r\n\r\n                    <p><strong>Cheers,</strong><br />\r\n                    The Quotes2Compare Team</p>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td style=\"margin:5px; background-color:#0084C6; text-align:center; border-right: 1px solid #fff;\">\r\n                    <a href=\"[[AUTO_INSURANCE_HOME_PAGE_URL]]\" style=\"color:#fff; display:block; text-decoration:underline;\">Visit Us</a>\r\n                </td>\r\n                <td style=\"margin:5px; background-color:#0084C6; text-align:center;border-right: 1px solid #fff;\">\r\n                    <a href=\"[[AUTO_INSURANCE_INSURANCE_LIBRARY_URL]]\" style=\"color:#fff; text-decoration:underline;\">Insurance Library</a>\r\n                </td>\r\n                <td style=\"margin:5px; background-color:#0084C6; text-align:center;\">\r\n                    <a href=\"[[AUTO_INSURANCE_CONTACT_US_URL]]\" style=\"color:#fff; text-decoration:underline;\">Contact Us</a>\r\n                </td>\r\n            </tr>\r\n\r\n        </tbody>\r\n    </table>\r\n</div>\r\n<p style=\"margin:10px; height:20px;font-family:Arial; text-align:center; font-size:10px; color:#999; line-height:18px;\">\r\n    Quotes2Compare respects your privacy. For more information, please review our <a href=\"[[AUTO_INSURANCE_PRIVACY_STATEMENT_URL]]\">Privacy Policy</a>\r\n</p>', 'support@quotes2compare.com', 'Quotes2Compare Auto Insurance');
INSERT INTO `Mail_Chimp_Template` VALUES (null, '2014-03-28 21:18:00', '0000-00-00 00:00:00', 'FetchTheQuote', 'THANK_YOU', 'Thank You - Your Request Has Been Received', '<div style=\"margin-left:auto; margin-right:auto; text-align:center;\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #000; font-family:Arial; font-size:15px; margin-left:auto; margin-right:auto; text-align: left;\">\r\n        <thead>\r\n        <tr style=\"background-color:#EEEEEE;\">\r\n            <td style=\"padding:0px; margin:0px; height:42px;\" colspan=\"4\">\r\n                <img src=\"[[AUTO_INSURANCE_LOGO_IMG_URL]]\" alt=\"FetchTheQuote Logo\" style=\"margin: 5px;\" />\r\n            </td>\r\n        </tr>\r\n        </thead>\r\n        <tbody>\r\n        <tr>\r\n            <td style=\"padding:10px;\" colspan=\"4\">\r\n                <p style=\"font-size:18px; font-weight:bold;\">Dear [[FIRST_NAME]] [[LAST_NAME]],</p>\r\n                <p>Thank you for using FetchTheQuote.com. Your request has been submitted and you should be contacted by email or phone by a professional who can assist you with finding a plan that suits your needs.</p>\r\n                <p>Once again, thank you for choosing FetchTheQuote.com and we hope to have the pleasure of serving you again in the future!</p>\r\n\r\n                <p><strong>Cheers,</strong><br />\r\n                    The FetchTheQuote Team</p>\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td style=\"margin:5px; background-color:#0084C6; text-align:center; border-right: 1px solid #fff;\">\r\n                <a href=\"[[AUTO_INSURANCE_HOME_PAGE_URL]]\" style=\"color:#fff; display:block; text-decoration:underline;\">Visit Us</a>\r\n            </td>\r\n            <td style=\"margin:5px; background-color:#0084C6; text-align:center;border-right: 1px solid #fff;\">\r\n                <a href=\"[[AUTO_INSURANCE_INSURANCE_LIBRARY_URL]]\" style=\"color:#fff; text-decoration:underline;\">Insurance Library</a>\r\n            </td>\r\n            <td style=\"margin:5px; background-color:#0084C6; text-align:center;\">\r\n                <a href=\"[[AUTO_INSURANCE_CONTACT_US_URL]]\" style=\"color:#fff; text-decoration:underline;\">Contact Us</a>\r\n            </td>\r\n        </tr>\r\n\r\n        </tbody>\r\n    </table>\r\n</div>\r\n<p style=\"margin:10px; height:20px;font-family:Arial; text-align:center; font-size:10px; color:#999; line-height:18px;\">\r\n    FetchTheQuote respects your privacy. For more information, please review our <a href=\"[[AUTO_INSURANCE_PRIVACY_STATEMENT_URL]]\">Privacy Policy</a>\r\n</p>', 'support@fetchthequote.com', 'FetchTheQuote');
