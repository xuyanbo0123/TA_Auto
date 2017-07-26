-- Change Lead_Property 'value' from NOT NULL to DEFAULT NULL by RequiemOfSouls on 20130303 7:07PM
-- Update Arrival_AB_Test_Option 'arrival_id' as foreign key by RequiemOfSouls on 20130304 1:22PM
-- Add 'uuid' field into Arrival by Tiger on 20130310 5:00PM
-- Add 'web_uri' field into Arrival_Tracking by RequiemOfSouls on 20130315 9:18PM
-- Add 'os' and 'browser' field into Arrival by RequiemOfSouls on 20130315 10:08 PM

DROP TABLE IF EXISTS Traffic_Source;
DROP TABLE IF EXISTS Traffic_Provider_Property;
DROP TABLE IF EXISTS Traffic_Provider;


DROP TABLE IF EXISTS Arrival_AB_Test_Option;
DROP TABLE IF EXISTS AB_Test_Option;
DROP TABLE IF EXISTS AB_Test;
DROP TABLE IF EXISTS AB_Test_Group;

DROP TABLE IF EXISTS Lead_Sell;
DROP TABLE IF EXISTS Click_Out;
DROP TABLE IF EXISTS Click_AD;
DROP TABLE IF EXISTS Click_Impression;
DROP TABLE IF EXISTS Click_Impression_Request;
DROP TABLE IF EXISTS Buyer_Account_Config;
DROP TABLE IF EXISTS Buyer_Account;
DROP TABLE IF EXISTS Buyer;
DROP TABLE IF EXISTS Lead_Property;
DROP TABLE IF EXISTS Lead;
DROP TABLE IF EXISTS Lead_Type;
DROP TABLE IF EXISTS Arrival_Tracking;
DROP TABLE IF EXISTS Web_Page;
DROP TABLE IF EXISTS Web_Form_Property;
DROP TABLE IF EXISTS Web_Form;
DROP TABLE IF EXISTS Arrival_Property;
DROP TABLE IF EXISTS Arrival;

CREATE TABLE `Traffic_Provider` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;



CREATE TABLE `Traffic_Provider_Property` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`traffic_provider_id`  int(11) NOT NULL,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`value`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`traffic_provider_id`) REFERENCES `Traffic_Provider` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
unique key(traffic_provider_id, name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;



CREATE TABLE `Traffic_Source` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`traffic_provider_id`  int(11) NOT NULL,
`traffic_type`  enum('seo','sem') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (traffic_provider_id) REFERENCES Traffic_Provider(id),
key(created_ts),
key(updated_ts)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

CREATE TABLE Arrival(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`uuid`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ip_address`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`user_agent`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`referer`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sid`  int(8) NOT NULL ,
`subid`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`os`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`browser`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`),
INDEX `created_ts` (`created_ts`) USING BTREE ,
INDEX `updated_ts` (`updated_ts`) USING BTREE ,
INDEX `sid` (`sid`) USING BTREE ,
INDEX `uuid` (`uuid`) USING BTREE ,
INDEX `os` (`os`) USING BTREE ,
INDEX `browser` (`browser`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=14
ROW_FORMAT=COMPACT
;

CREATE TABLE Arrival_Property(
  id int(11) NOT NULL AUTO_INCREMENT,
  arrival_id int(11) NOT NULL,
  name varchar(127) NOT NULL,
  value varchar(1023) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (arrival_id) REFERENCES Arrival(id),
  unique key(arrival_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Web_Form` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`arrival_id`  int(11) NOT NULL,
`form_type`  enum('health') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`form_stage`  int(11) NOT NULL DEFAULT 0,
`detail_form_id`  int(11) DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (arrival_id) REFERENCES Arrival(id),
key(created_ts),
key(updated_ts),
key(detail_form_id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Web_Form_Property` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`web_form_id`  int(11) NOT NULL DEFAULT 0 ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`value`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`web_form_id`) REFERENCES `Web_Form` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
unique key(web_form_id, name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Web_Page` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`uri`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`web_form_id`  int(11) DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`web_form_id`) REFERENCES `Web_Form` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
key(created_ts),
key(updated_ts),
key(uri)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Arrival_Tracking` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`arrival_id`  int(11) NULL DEFAULT NULL ,
`web_page_id`  int(11) NOT NULL ,
`action`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`web_page_url`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`web_page_id`) REFERENCES `Web_Page` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (`arrival_id`) REFERENCES `Arrival` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `web_page_id` (`web_page_id`) USING BTREE ,
INDEX `arrival_id` (`arrival_id`) USING BTREE ,
INDEX `created_ts` (`created_ts`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=16
ROW_FORMAT=COMPACT
;

CREATE TABLE `AB_Test_Group` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`web_page_id`  int(11) NOT NULL,
`status`  enum('on', 'off') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`description`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`web_page_id`) REFERENCES `Web_Page` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
key(created_ts),
key(updated_ts),
unique key(name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `AB_Test` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`ab_test_group_id`  int(11) NOT NULL,
`status`  enum('on', 'off') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`description`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`ab_test_group_id`) REFERENCES `AB_Test_Group` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
key(created_ts),
key(updated_ts),
unique key(ab_test_group_id, name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE `AB_Test_Option` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`ab_test_id`  int(11) NOT NULL,
`option`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`weight`  int(11) NOT NULL,
`status`  enum('on', 'off') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`description`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`ab_test_id`) REFERENCES `AB_Test` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
key(created_ts),
key(updated_ts),
unique key(ab_test_id, `option`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Arrival_AB_Test_Option` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`arrival_id`  int(11) NOT NULL ,
`ab_test_option_id`  int(11) NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`arrival_id`) REFERENCES `Arrival` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (`ab_test_option_id`) REFERENCES `AB_Test_Option` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `ab_test_option_id` (`ab_test_option_id`) USING BTREE ,
INDEX `created_ts` (`created_ts`) USING BTREE ,
INDEX `updated_ts` (`updated_ts`) USING BTREE ,
INDEX `arrival_id` (`arrival_id`, `ab_test_option_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE `Lead_Type` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`type_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`description`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
unique key(type_name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;



CREATE TABLE `Lead` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`arrival_id`  int(11) NOT NULL,
`lead_type_id`  int(11) NOT NULL,
`lead_state`  enum('received', 'saved', 'processing', 'sold', 'not_sold') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`arrival_id`) REFERENCES `Arrival` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (`lead_type_id`) REFERENCES `Lead_Type` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
key(created_ts),
key(updated_ts)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;



CREATE TABLE `Lead_Property` (
`id`  int(11) NOT NULL AUTO_INCREMENT,
`lead_id`  int(11) NOT NULL ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`value`  varchar(1023)  CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`lead_id`) REFERENCES `Lead` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
unique key(lead_id, name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;



CREATE TABLE `Buyer` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`buyer_type`  enum('direct', 'aggregator', 'unknown') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`contact_info`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
unique key(name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;



CREATE TABLE `Buyer_Account` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`buyer_id`  int(11) NOT NULL,
`lead_type_id`  int(11) NOT NULL,
`account_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`account_state`  enum('testing','pending','production','closed', 'paused') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`buyer_id`) REFERENCES `Buyer` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (`lead_type_id`) REFERENCES `Lead_Type` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
key(created_ts),
key(updated_ts),
unique key(buyer_id, account_name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;



CREATE TABLE `Buyer_Account_Config` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`buyer_account_id`  int(11) NOT NULL,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`value`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`buyer_account_id`) REFERENCES `Buyer_Account` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
key(created_ts),
key(updated_ts),
unique key(buyer_account_id, name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Lead_Sell` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`lead_id`  int(11) NOT NULL,
`buyer_account_id`  int(11) NOT NULL,
`sell_state`  enum('pending','sold', 'rejected','returned') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
`amount`  DECIMAL(9,3) DEFAULT NULL ,
`adjusted_amount`  DECIMAL(9,3) DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`lead_id`) REFERENCES `Lead` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (`buyer_account_id`) REFERENCES `Buyer_Account` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
key(created_ts),
key(updated_ts),
unique key(lead_id, buyer_account_id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;



CREATE TABLE `Click_Impression_Request` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`arrival_id`  int(11) NOT NULL DEFAULT 0 ,
`location`  enum('landing','after_form','exit_page') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`lead_type_id`  int(11) NOT NULL,
`postal_state`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
`zip_code`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`arrival_id`) REFERENCES `Arrival` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (`lead_type_id`) REFERENCES `Lead_Type` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
key(created_ts),
key(updated_ts)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;



CREATE TABLE `Click_Impression` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`click_impression_request_id`  int(11) NOT NULL,
`buyer_account_id`  int(11) NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`click_impression_request_id`) REFERENCES `Click_Impression_Request` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (`buyer_account_id`) REFERENCES `Buyer_Account` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
key(created_ts),
key(updated_ts),
key(click_impression_request_id, buyer_account_id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;



CREATE TABLE `Click_AD` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`click_impression_id`  int(11) NOT NULL,
`position`  int(11) DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`click_impression_id`) REFERENCES `Click_Impression` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
key(created_ts),
key(updated_ts)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Click_Out` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`click_impression_id`  int(11) NOT NULL,
`click_ad_id`  int(11) DEFAULT NULL,
`amount`  DECIMAL(9,3) DEFAULT NULL,
`adjusted_amount`  DECIMAL(9,3) DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`click_impression_id`) REFERENCES `Click_Impression` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (`click_ad_id`) REFERENCES `Click_AD` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
key(created_ts),
key(updated_ts)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


