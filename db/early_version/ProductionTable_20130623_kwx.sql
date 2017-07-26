-- Note:
-- Delete key(uri) from Web_Page table due to 'Specified key was too long; max key length is 767 bytes'
-- Delete KEY (`street`) from Health_Form table due to 'Specified key was too long; max key length is 767 bytes'
-- Change raw_request field type from varchar(4095) to text for Lead_Request table
-- Add AUTO_INCREMENT=1 into Email_Record_Property_Field table
-- Delete web_form_id field in Web_Page table
-- Change 'ON DELETE RESTRICT ON UPDATE RESTRICT' to 'ON DELETE NO ACTION ON UPDATE CASCADE' for Email_Record and Email_Program_Step_Rule_Map table

-- In table Email_Program_Step_Rule_Map, revised the dumped sql to make the two FK referred table name correct (changed from email_program_step and email_rule to Email_Program_Step and Email_Rule)

-- From TestQuery_20130525.sql

-- KS: updated the Traffic_Budget table to include all fields

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

-- From API_Tables_20130428.sql
DROP TABLE IF EXISTS Ad_Group_Keyword_Performance;
DROP TABLE IF EXISTS Ad_Group_Ad_Performance;
DROP TABLE IF EXISTS Daily_Campaign_Spending;
DROP TABLE IF EXISTS Daily_Ad_Group_Spending;
DROP TABLE IF EXISTS Ad_Group_Spending;
DROP TABLE IF EXISTS Query_Tracking;
DROP TABLE IF EXISTS Ad_Group_Ad;
DROP TABLE IF EXISTS Text_Ad;
DROP TABLE IF EXISTS Ad_Group_Keyword_Bid;
DROP TABLE IF EXISTS Ad_Group_Keyword;
DROP TABLE IF EXISTS Keyword;
DROP TABLE IF EXISTS Ad_Group_Bid;
DROP TABLE IF EXISTS Ad_Group_Property;
DROP TABLE IF EXISTS Ad_Group;
DROP TABLE IF EXISTS Traffic_Campaign_Schedule;
DROP TABLE IF EXISTS Traffic_Campaign_Geo_Target;
DROP TABLE IF EXISTS Traffic_Campaign_Bid;
DROP TABLE IF EXISTS Traffic_Campaign_Budget;
DROP TABLE IF EXISTS Traffic_Campaign_Property;
DROP TABLE IF EXISTS Traffic_Campaign;
DROP TABLE IF EXISTS Traffic_Budget;

-- From request_table.sql
DROP TABLE IF EXISTS Lead_Request;

-- From email_tables_by_xyb_20130412.sql
-- From EmailComplement_by_xyb_20130619.sql
drop table if EXISTS Email_Send_Link_Tracking;
drop table if EXISTS Email_Send;
drop table if EXISTS Email_Record_Role_Map;
drop table if EXISTS Email_Role_Property;
drop table if EXISTS Email_Role;
DROP TABLE IF EXISTS Email_Program_Step_Rule_Map;
DROP TABLE IF EXISTS Email_Rule;
drop table if EXISTS Email_Program_Step;
drop table if EXISTS Email_Program_Property;
drop table if EXISTS Email_Program;
drop table if EXISTS Email_Record_Property;
drop table if EXISTS Email_Record;
drop table if EXISTS Email_From;
drop table if EXISTS Email_Template_Link;
drop table if EXISTS Email_Template;
DROP TABLE IF EXISTS Email_Replacement_Text;

-- From Redirect.sql
drop table if EXISTS Redirect_Action;
drop table if EXISTS Redirect;

-- From tables_revenue_spending_20130503_by_xyb.sql
drop table if EXISTS Ad_Group_Keyword_Daily_Spending_Rollup;
drop table if EXISTS Ad_Group_Keyword_Daily_Spending;
drop table if EXISTS Ad_Group_Keyword_Hourly_Spending;
drop table if EXISTS Ad_Group_Keyword_Daily_Revenue_Rollup;
drop table if EXISTS Ad_Group_Keyword_Daily_Revenue;
drop table if EXISTS Ad_Group_Keyword_Hourly_Revenue;

-- Dependent Table
DROP TABLE IF EXISTS `Arrival_Tracking_Parameters`;
DROP TABLE IF EXISTS `Aggregated_Record`;
DROP TABLE IF EXISTS `Health_Form`;
drop table if EXISTS Email_Record_Unique;
DROP TABLE IF EXISTS `Email_Record_Property_Field`;





-- From TestQuery_20130525.sql
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
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
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
AUTO_INCREMENT=1
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
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts)
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
AUTO_INCREMENT=1
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
token VARCHAR(63) not null,
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
token VARCHAR(63) not null,
`position`  int(11) DEFAULT NULL,
head_line VARCHAR(1023) not null,
display_text VARCHAR(1023) not null,
logo_link VARCHAR(511) not null,
click_link VARCHAR(1023) not null,
display_link VARCHAR(255) not null,
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

-- From API_Tables_20130428.sql
CREATE TABLE Traffic_Budget(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`traffic_provider_id`  int(11) NOT NULL ,
`provider_supplied_id`  int(11) NULL DEFAULT NULL ,
`status`  enum('active','paused','deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`period`  enum('DAILY') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`amount`  bigint(20) NOT NULL ,
`delivery_method`  enum('ACCELERATED','STANDARD') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`traffic_provider_id`) REFERENCES `Traffic_Provider` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
UNIQUE INDEX `name` (`name`, `traffic_provider_id`) USING BTREE ,
INDEX `created_ts` (`created_ts`) USING BTREE ,
INDEX `updated_ts` (`updated_ts`) USING BTREE ,
INDEX `traffic_provider_id` (`traffic_provider_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE Traffic_Campaign (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
sid int(11) not null,
provider_supplied_id int(11) DEFAULT null,
`local_status` enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`provider_status` enum('pending_upload','active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`uploaded_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(uploaded_ts),
key(name),
key(sid),
FOREIGN KEY (sid) REFERENCES Traffic_Source(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Traffic_Campaign_Property(
id int(11) NOT NULL AUTO_INCREMENT,
traffic_campaign_id int(11) NOT NULL,
name varchar(127) NOT NULL,
value varchar(1023) DEFAULT NULL,
PRIMARY KEY (id),
FOREIGN KEY (traffic_campaign_id) REFERENCES Traffic_Campaign(id),
unique key(traffic_campaign_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Traffic_Campaign_Budget(
id int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
traffic_campaign_id int(11) NOT NULL,
traffic_budget_id int(11) NOT NULL,
period enum('daily', 'weekly', 'monthly') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
spend_type enum('evenly', 'accelerated', 'other') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
amount DECIMAL(10,2) DEFAULT NULL,
provider_supplied_id int(11) DEFAULT null,
uploaded_ts timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
key(uploaded_ts),
FOREIGN KEY (traffic_campaign_id) REFERENCES Traffic_Campaign(id),
FOREIGN KEY (traffic_budget_id) REFERENCES Traffic_Budget(id),
unique key(traffic_campaign_id, traffic_budget_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Traffic_Campaign_Bid(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
traffic_campaign_id int(11) not null,
bid_type enum('cpc', 'cpa', 'cpm') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
bid_amount DECIMAL(8,3) NOT NULL,
provider_supplied_id int(11) DEFAULT null,
uploaded_ts timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(uploaded_ts),
FOREIGN KEY (traffic_campaign_id) REFERENCES Traffic_Campaign(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Traffic_Campaign_Geo_Target(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
traffic_campaign_id int(11) not null,
target_type enum('positive', 'negative') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
location_type enum('state', 'zip', 'city') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
location varchar(127) NOT NULL,
provider_supplied_id int(11) DEFAULT null,
uploaded_ts timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(uploaded_ts),
unique key(traffic_campaign_id, location),
FOREIGN KEY (traffic_campaign_id) REFERENCES Traffic_Campaign(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE Traffic_Campaign_Schedule(
`id`  int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
traffic_campaign_id int(11) not null,
schedule_type varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 'Daily', 'Weekday', 'Weekend', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri','Sat', 'Sun' ...
start_time varchar(127) NOT NULL, -- 08:45
end_time varchar(127) NOT NULL, -- 22:45
provider_supplied_id int(11) DEFAULT null,
uploaded_ts timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(uploaded_ts),
FOREIGN KEY (traffic_campaign_id) REFERENCES Traffic_Campaign(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE Ad_Group(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
traffic_campaign_id int(11) not null,
name varchar(127) NOT NULL,
`local_status` enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`provider_status` enum('pending_upload','active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
provider_supplied_id bigint(20) DEFAULT null,
`uploaded_ts`  timestamp NULL DEFAULT '0000-00-00 00:00:00',
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(uploaded_ts),
unique key(traffic_campaign_id, name),
FOREIGN KEY (traffic_campaign_id) REFERENCES Traffic_Campaign(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Ad_Group_Property(
id int(11) NOT NULL AUTO_INCREMENT,
ad_group_id int(11) NOT NULL,
name varchar(127) NOT NULL,
value varchar(1023) DEFAULT NULL,
PRIMARY KEY (id),
FOREIGN KEY (ad_group_id) REFERENCES Ad_Group(id),
unique key(ad_group_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE Ad_Group_Bid(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_id int(11) not null,
bid_type enum('cpc', 'cpa', 'cpm') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
bid_amount DECIMAL(8,3) NOT NULL,
provider_supplied_id bigint(20) DEFAULT null,
uploaded_ts timestamp DEFAULT '0000-00-00 00:00:00',
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(uploaded_ts),
FOREIGN KEY (ad_group_id) REFERENCES Ad_Group(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Keyword(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`text`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
unique key(text(255))
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE Ad_Group_Keyword(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_id int(11) not null,
keyword_id int(11) not null,
match_type enum('exact', 'broad ', 'phrase', 'broad_mod', 'negative') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
provider_supplied_id bigint(20) DEFAULT null,
uploaded_ts timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`local_status` enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
provider_status enum('pending_upload', 'pending', 'approved', 'disapproved', 'paused', 'deleted', 'active') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(uploaded_ts),
key(ad_group_id, keyword_id),
unique key(ad_group_id, keyword_id, match_type),
FOREIGN KEY (ad_group_id) REFERENCES Ad_Group(id),
FOREIGN KEY (keyword_id) REFERENCES Keyword(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE Ad_Group_Keyword_Bid(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id int(11) not null,
bid_type enum('cpc', 'cpa', 'cpm') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
bid_amount DECIMAL(8,3) NOT NULL,
provider_supplied_id bigint(20) DEFAULT null,
uploaded_ts timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(uploaded_ts),
FOREIGN KEY (ad_group_keyword_id) REFERENCES Ad_Group_Keyword(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE Text_Ad(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`headline`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`description1`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`description2`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
`displayUrl`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`actionUrl`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE Ad_Group_Ad(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_id int(11) not null,
ad_id int(11) not null,
provider_supplied_id bigint(20) DEFAULT null,
uploaded_ts timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`local_status` enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
provider_status enum('pending_upload', 'pending', 'approved', 'disapproved', 'paused', 'deleted', 'active') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(ad_group_id, ad_id),
FOREIGN KEY (ad_group_id) REFERENCES Ad_Group(id),
FOREIGN KEY (ad_id) REFERENCES Text_Ad(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE Query_Tracking(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
arrival_id int(11) NOT NULL,
query_text varchar(1027) NOT NULL,
ad_group_keyword_id int(11) DEFAULT null,
ad_group_ad_id int(11) DEFAULT null,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(ad_group_keyword_id),
key(ad_group_ad_id),
FOREIGN KEY (arrival_id) REFERENCES Arrival(id),
FOREIGN KEY (ad_group_keyword_id) REFERENCES Ad_Group_Keyword(id),
FOREIGN KEY (ad_group_ad_id) REFERENCES Ad_Group_Ad(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE Ad_Group_Spending(
id int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_id int(11) NOT NULL,
start_at DATETIME NOT NULL,
end_at DATETIME NOT NULL,
impression_count int(11) DEFAULT NULL,
click_count int(11) DEFAULT NULL,
total_spending DECIMAL(10,2) DEFAULT NULL,
avg_position DECIMAL(6,2) DEFAULT NULL,
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
FOREIGN KEY (ad_group_id) REFERENCES Ad_Group(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE Daily_Ad_Group_Spending(
id int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_id int(11) NOT NULL,
day DATE NOT NULL,
impression_count int(11) DEFAULT NULL,
click_count int(11) DEFAULT NULL,
total_spending DECIMAL(10,2) DEFAULT NULL,
avg_position DECIMAL(6,2) DEFAULT NULL,
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
FOREIGN KEY (ad_group_id) REFERENCES Ad_Group(id),
unique key(ad_group_id, day)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE Daily_Campaign_Spending(
id int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
traffic_campaign_id int(11) not null,
day DATE NOT NULL,
impression_count int(11) DEFAULT NULL,
click_count int(11) DEFAULT NULL,
total_spending DECIMAL(10,2) DEFAULT NULL,
avg_position DECIMAL(6,2) DEFAULT NULL,
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
FOREIGN KEY (traffic_campaign_id) REFERENCES Traffic_Campaign(id),
unique key(traffic_campaign_id, day)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Ad_Group_Ad_Performance(
id int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_ad_id int(11) NOT NULL,
start_at DATETIME NOT NULL,
end_at DATETIME NOT NULL,
impression_count int(11) DEFAULT NULL,
click_count int(11) DEFAULT NULL,
total_spending DECIMAL(10,2) DEFAULT NULL,
avg_position DECIMAL(6,2) DEFAULT NULL,
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
FOREIGN KEY (ad_group_ad_id) REFERENCES Ad_Group_Ad(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE Ad_Group_Keyword_Performance(
id int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id int(11) NOT NULL,
start_at DATETIME NOT NULL,
end_at DATETIME NOT NULL,
impression_count int(11) DEFAULT NULL,
click_count int(11) DEFAULT NULL,
total_spending DECIMAL(10,2) DEFAULT NULL,
avg_position DECIMAL(6,2) DEFAULT NULL,
quality_score DECIMAL(6,2) DEFAULT NULL,
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
FOREIGN KEY (ad_group_keyword_id) REFERENCES Ad_Group_Keyword(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- From request_table.sql
CREATE TABLE `Lead_Request` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`raw_request` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`request_status`  enum('sold','ping_started','saved','received') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`arrival_id`  int(11) NOT NULL ,
`lead_type_id`  int(11) NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`arrival_id`) REFERENCES `Arrival` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (`lead_type_id`) REFERENCES `Lead_Type` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `arrival_id` (`arrival_id`) USING BTREE ,
INDEX `lead_type_id` (`lead_type_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

-- From email_tables_by_xyb_20130412.sql
-- From EmailComplement_by_xyb_20130619.sql
CREATE TABLE `Email_Replacement_Text` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`parameter_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`replacement_text`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`),
UNIQUE INDEX `parameter_name` (`parameter_name`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Email_Template(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
subject varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
content text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
status enum('active', 'paused', 'closed') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
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

CREATE TABLE Email_Template_Link(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_template_id int(11) NOT null,
link_name  varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
link_text varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
link_url varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
unique key(email_template_id, link_name),
FOREIGN KEY (email_template_id) REFERENCES Email_Template(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Email_From(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
from_address  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
from_text varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
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

CREATE TABLE Email_Record(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`email_address`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`email_domain`  varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`status`  enum('active','closed') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`arrival_id`  int(11) NULL DEFAULT NULL ,
`user_id`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`arrival_id`) REFERENCES `Arrival` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `created_ts` (`created_ts`) USING BTREE ,
INDEX `updated_ts` (`updated_ts`) USING BTREE ,
INDEX `email_domain` (`email_domain`) USING BTREE ,
INDEX `arrival_id` (`arrival_id`) USING BTREE ,
INDEX `email_address` (`email_address`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Email_Record_Property(
id int(11) NOT NULL AUTO_INCREMENT,
email_record_id int(11) NOT NULL,
name varchar(127) NOT NULL,
value varchar(1023) DEFAULT NULL,
PRIMARY KEY (id),
FOREIGN KEY (email_record_id) REFERENCES Email_Record(id),
unique key(email_record_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Email_Program(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
name  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
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

CREATE TABLE Email_Program_Property(
id int(11) NOT NULL AUTO_INCREMENT,
email_program_id int(11) NOT NULL,
name varchar(127) NOT NULL,
value varchar(1023) DEFAULT NULL,
PRIMARY KEY (id),
FOREIGN KEY (email_program_id) REFERENCES Email_Program(id),
unique key(email_program_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Email_Program_Step(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_program_id int(11) NOT NULL,
step_name  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
email_template_id int(11) NOT null,
email_from_id int(11) NOT null,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
unique key(email_program_id, step_name),
FOREIGN KEY (email_program_id) REFERENCES Email_Program(id),
FOREIGN KEY (email_template_id) REFERENCES Email_Template(id),
FOREIGN KEY (email_from_id) REFERENCES Email_From(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Email_Rule` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`name`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`value`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`),
INDEX `created_ts` (`created_ts`) USING BTREE ,
INDEX `updated_ts` (`updated_ts`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Email_Program_Step_Rule_Map` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`email_program_step_id`  int(11) NOT NULL ,
`email_rule_id`  int(11) NOT NULL ,
`status`  enum('active','paused','closed') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`email_program_step_id`) REFERENCES `Email_Program_Step` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (`email_rule_id`) REFERENCES `Email_Rule` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
UNIQUE INDEX `email_program_step_id` (`email_program_step_id`, `email_rule_id`) USING BTREE ,
INDEX `created_ts` (`created_ts`) USING BTREE ,
INDEX `updated_ts` (`updated_ts`) USING BTREE ,
INDEX `email_rule_id` (`email_rule_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Email_Role(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
name  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
email_program_id int(11) NOT NULL,
comments varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
FOREIGN KEY (email_program_id) REFERENCES Email_Program(id),
unique key(name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE Email_Role_Property(
id int(11) NOT NULL AUTO_INCREMENT,
email_role_id int(11) NOT NULL,
name varchar(127) NOT NULL,
value varchar(1023) DEFAULT NULL,
PRIMARY KEY (id),
FOREIGN KEY (email_role_id) REFERENCES Email_Role(id),
unique key(email_role_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Email_Record_Role_Map(
id int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_record_id int(11) NOT NULL,
email_role_id int(11) NOT NULL,
status enum ('active', 'paused', 'closed') NOT NULL,
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
FOREIGN KEY (email_record_id) REFERENCES Email_Record(id),
FOREIGN KEY (email_role_id) REFERENCES Email_Role(id),
unique key(email_record_id, email_role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Email_Send(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_record_id int(11) NOT NULL,
email_role_id int(11) NOT NULL,
email_program_step_id int(11) NOT NULL,
email_template_id int(11) NOT null,
email_from_id int(11) NOT null,
queue_ts  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
sent_ts  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
opened_ts  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
FOREIGN KEY (email_record_id) REFERENCES Email_Record(id),
FOREIGN KEY (email_role_id) REFERENCES Email_Role(id),
FOREIGN KEY (email_program_step_id) REFERENCES Email_Program_Step(id),
FOREIGN KEY (email_template_id) REFERENCES Email_Template(id),
FOREIGN KEY (email_from_id) REFERENCES Email_From(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE Email_Send_Link_Tracking(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_sent_id int(11) NOT NULL,
Email_template_link_id int(11) NOT NULL,
clicked_ts  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(clicked_ts),
FOREIGN KEY (email_sent_id) REFERENCES Email_Send(id),
FOREIGN KEY (Email_template_link_id) REFERENCES Email_Template_Link(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

-- From Redirect.sql
CREATE TABLE Redirect(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`action`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
token varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
destination_url varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Redirect_Action(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`redirect_id` int(11) NOT NULL,
`action_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
PRIMARY KEY (`id`),
FOREIGN KEY (redirect_id) REFERENCES Redirect(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

-- From tables_revenue_spending_20130503_by_xyb.sql
CREATE TABLE Ad_Group_Keyword_Hourly_Revenue(
id int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id int(11) NOT NULL,
day DATE NOT NULL,
hour int(5) NOT NULL,
lead_count int(11) DEFAULT NULL,
total_lead_revenue DECIMAL(10,2) DEFAULT NULL,
ad_impression_count int(11) DEFAULT NULL,
ad_click_count int(11) DEFAULT NULL,
total_ad_click_revenue DECIMAL(10,2) DEFAULT NULL,
conversion_count int(11) DEFAULT NULL,
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
key(day),
unique key(day, hour, ad_group_keyword_id),
FOREIGN KEY (ad_group_keyword_id) REFERENCES Ad_Group_Keyword(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Ad_Group_Keyword_Daily_Revenue(
id int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id int(11) NOT NULL,
day DATE NOT NULL,
lead_count int(11) DEFAULT NULL,
total_lead_revenue DECIMAL(10,2) DEFAULT NULL,
ad_impression_count int(11) DEFAULT NULL,
ad_click_count int(11) DEFAULT NULL,
total_ad_click_revenue DECIMAL(10,2) DEFAULT NULL,
conversion_count int(11) DEFAULT NULL,
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
key(day),
unique key(day, ad_group_keyword_id),
FOREIGN KEY (ad_group_keyword_id) REFERENCES Ad_Group_Keyword(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE Ad_Group_Keyword_Daily_Revenue_Rollup(
id int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id int(11) NOT NULL,
start_day DATE NOT NULL,
end_day DATE NOT NULL,
rollup_type enum('weekly', 'monthly', 'yearly') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
lead_count int(11) DEFAULT NULL,
total_lead_revenue DECIMAL(10,2) DEFAULT NULL,
ad_impression_count int(11) DEFAULT NULL,
ad_click_count int(11) DEFAULT NULL,
total_ad_click_revenue DECIMAL(10,2) DEFAULT NULL,
conversion_count int(11) DEFAULT NULL,
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
key(start_day),
key(end_day),
unique key(ad_group_keyword_id, end_day, start_day),
FOREIGN KEY (ad_group_keyword_id) REFERENCES Ad_Group_Keyword(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE Ad_Group_Keyword_Hourly_Spending(
id int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id int(11) NOT NULL,
day DATE NOT NULL,
hour int(5) NOT NULL,
impression_count int(11) DEFAULT NULL,
click_count int(11) DEFAULT NULL,
arrival_count int(11) DEFAULT NULL,
total_spending DECIMAL(10,2) DEFAULT NULL,
avg_position DECIMAL(10,2) DEFAULT NULL,
quality_score DECIMAL(10,2) DEFAULT NULL,
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
unique key(day, hour, ad_group_keyword_id),
FOREIGN KEY (ad_group_keyword_id) REFERENCES Ad_Group_Keyword(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Ad_Group_Keyword_Daily_Spending(
id int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id int(11) NOT NULL,
day DATE NOT NULL,
impression_count int(11) DEFAULT NULL,
click_count int(11) DEFAULT NULL,
arrival_count int(11) DEFAULT NULL,
total_spending DECIMAL(10,2) DEFAULT NULL,
avg_position DECIMAL(10,2) DEFAULT NULL,
quality_score DECIMAL(10,2) DEFAULT NULL,
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
unique key(day, ad_group_keyword_id),
FOREIGN KEY (ad_group_keyword_id) REFERENCES Ad_Group_Keyword(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Ad_Group_Keyword_Daily_Spending_Rollup(
id int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id int(11) NOT NULL,
start_day DATE NOT NULL,
end_day DATE NOT NULL,
rollup_type enum('weekly', 'monthly', 'yearly') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
impression_count int(11) DEFAULT NULL,
click_count int(11) DEFAULT NULL,
arrival_count int(11) DEFAULT NULL,
total_spending DECIMAL(10,2) DEFAULT NULL,
avg_position DECIMAL(10,2) DEFAULT NULL,
quality_score DECIMAL(10,2) DEFAULT NULL,
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
key(start_day),
key(end_day),
unique key(ad_group_keyword_id, end_day, start_day),
FOREIGN KEY (ad_group_keyword_id) REFERENCES Ad_Group_Keyword(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dependent Table
CREATE TABLE `Arrival_Tracking_Parameters` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`url_name`  varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`db_name`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`comment`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
PRIMARY KEY (`id`, `url_name`, `db_name`),
UNIQUE INDEX url_name (url_name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;


CREATE TABLE `Aggregated_Record` (
  `id`  int(11) NOT NULL AUTO_INCREMENT ,
  `created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `aggregated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  `type`  enum('daily','weekly','monthly') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
  PRIMARY KEY (`id`),
  INDEX `aggregated_ts` (`aggregated_ts`) USING BTREE ,
  INDEX `type` (`type`) USING BTREE
  )
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=1
  ROW_FORMAT=COMPACT
  ;

CREATE TABLE `Health_Form` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`arrival_id`  int(11) NOT NULL ,
`user_id`  int(11) DEFAULT NULL ,
`gender`  enum('Female','Male') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`birthdate`  date DEFAULT NULL ,
`height_ft`  int(11) DEFAULT NULL ,
`height_in`  int(11) DEFAULT NULL ,
`height_cm`  decimal(6,2) DEFAULT NULL ,
`weight_lbs`  decimal(6,2) DEFAULT NULL ,
`weight_kg`  decimal(6,2) DEFAULT NULL ,
`tobacco`  tinyint(1) NOT NULL DEFAULT 0,
`marital`  enum('Single','Married','Divorced','Widowed') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`spouse_inc`  tinyint(1) NOT NULL DEFAULT 0 ,
`spouse_first_name`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`spouse_last_name`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`spouse_gender`  enum('Female','Male') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`spouse_birthdate`  date DEFAULT NULL ,
`spouse_height_ft`  int(11) DEFAULT NULL ,
`spouse_height_in`  int(11) DEFAULT NULL ,
`spouse_height_cm`  decimal (6,2) DEFAULT NULL ,
`spouse_weight_lbs`  decimal (6,2) DEFAULT NULL ,
`spouse_weight_kg`  decimal (6,2) DEFAULT NULL ,
`spouse_tobacco`  tinyint(1) NOT NULL DEFAULT 0 ,
`children`  int(11)  DEFAULT 0 ,
`pregnant`  tinyint(1) NOT NULL DEFAULT 0 ,
`major_treatment`  tinyint(1) NOT NULL DEFAULT 0 ,
`hospitalized`  tinyint(1) NOT NULL DEFAULT 0 ,
`deny`  tinyint(1) NOT NULL DEFAULT 0 ,
`self_employ`  tinyint(1) NOT NULL DEFAULT 0 ,
`current_coverage`  tinyint(1) NOT NULL DEFAULT 0 ,
`prescription`  tinyint(1) NOT NULL DEFAULT 0 ,
`health_condition`  tinyint(1) NOT NULL DEFAULT 0 ,
`current_provider`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`prescription_list`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`health_condition_list`  varchar(2047) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`first_name`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`last_name`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`street`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`city`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`state`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`zipcode`  varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`day_phone`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`evening_phone`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
`email`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
PRIMARY KEY (`id`),
KEY (`zipcode`),
KEY (`state`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Email_Record_Unique(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_address  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
email_domain varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
status enum('opt_in', 'opt_out', 'disable') NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(email_domain),
unique key(email_address)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Email_Record_Property_Field` (
`id`  int(11) NOT NULL AUTO_INCREMENT,
`email_record_property_field`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;