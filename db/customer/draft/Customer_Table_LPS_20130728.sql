-- Change Log:

-- XYB: 20130719
-- XYB: DELETE TABLE: Lead, Lead_Property
-- XYB: Change `request_status` for Table: Lead_Request
-- XYB: Redesign Lead_Sell Table
-- XYB: Change `incident_date` type from varchar to date for Table: Claim
-- XYB: Change `violation_date` type from varchar to date for Table: Violation

-- XYB: 20130720
-- XYB: DELETE TABLE: Health_Form
-- XYB: Change `arrival_id` to `lead_request_id` for Table: Auto_Form
-- XYB: Add field `token` into Table: Lead_Request (UUID)

-- XYB: 20130721
-- XYB: Add field `comment` into Table: Lead_Sell
-- XYB: Add field `post_url` into Table: Lead_Sell
-- XYB: Add field `post_entity` into Table: Lead_Sell
-- XYB: Change `request_status` for Table: Lead_Request

-- XYB: 20130722
-- XYB: Redesign Five Table: Auto_Form, Driver, Vehicle, Violation, Claim

-- XYB: 20130725
-- XYB: Redesign Five Table(Add Enum): Auto_Form, Driver, Vehicle, Violation, Claim
-- XYB: Change `sell_state` enum value for Table: Lead_Sell

-- XYB: 20130728
-- XYB: ADD Table: Carrier_List, Keyword_Carrier_Map



-- XYB: Total Count: 49

-- Email Function
DROP TABLE IF EXISTS Email_Replacement_Text;
drop table if EXISTS Email_Send_Link_Tracking;
drop table if EXISTS Email_Send;
DROP TABLE IF EXISTS Email_Program_Step_Rule_Map;
DROP TABLE IF EXISTS Email_Rule;
drop table if EXISTS Email_Program_Step;
drop table if EXISTS Email_From;
drop table if EXISTS Email_Template_Link;
drop table if EXISTS Email_Template;
drop table if EXISTS Email_Record_Role_Map;
drop table if EXISTS Email_Role_Property;
drop table if EXISTS Email_Role;
drop table if EXISTS Email_Program_Property;
drop table if EXISTS Email_Program;
DROP TABLE IF EXISTS `Email_Record_Property_Field`;
drop table if EXISTS Email_Record_Property;
drop table if EXISTS Email_Record;
drop table if EXISTS Email_Record_Unique;

-- Arrival Tracking Function
DROP TABLE IF EXISTS `Keyword_Carrier_Map`;
DROP TABLE IF EXISTS `Carrier_List`;
drop table if EXISTS Redirect_Action;
drop table if EXISTS Redirect;
DROP TABLE IF EXISTS Arrival_AB_Test_Option;
DROP TABLE IF EXISTS AB_Test_Option;
DROP TABLE IF EXISTS AB_Test;
DROP TABLE IF EXISTS AB_Test_Group;
DROP TABLE IF EXISTS Lead_Sell;
DROP TABLE IF EXISTS Click_Out;
DROP TABLE IF EXISTS Click_Ad;
DROP TABLE IF EXISTS Click_Impression;
DROP TABLE IF EXISTS Click_Impression_Request;
DROP TABLE IF EXISTS Buyer_Account_Config;
DROP TABLE IF EXISTS Buyer_Account;
DROP TABLE IF EXISTS Buyer;
DROP TABLE IF EXISTS Driver;
DROP TABLE IF EXISTS Vehicle;
DROP TABLE IF EXISTS Violation;
DROP TABLE IF EXISTS Claim;
DROP TABLE IF EXISTS Auto_Form;
DROP TABLE IF EXISTS Lead_Request;
DROP TABLE IF EXISTS Lead_Type;
DROP TABLE IF EXISTS Arrival_Tracking_Parameters;
DROP TABLE IF EXISTS Arrival_Tracking;
DROP TABLE IF EXISTS Web_Page;
DROP TABLE IF EXISTS Arrival_Property;
DROP TABLE IF EXISTS Arrival;
DROP TABLE IF EXISTS Traffic_Source;
DROP TABLE IF EXISTS Traffic_Provider_Property;
DROP TABLE IF EXISTS Traffic_Provider;



-- Arrival Tracking Function
CREATE TABLE `Traffic_Provider` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`traffic_provider_id`  bigint(20) NOT NULL,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`traffic_provider_id`  bigint(20) NOT NULL,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
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
  id bigint(20) NOT NULL AUTO_INCREMENT,
  arrival_id bigint(20) NOT NULL,
  name varchar(127) NOT NULL,
  value varchar(1023) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (arrival_id) REFERENCES Arrival(id),
  unique key(arrival_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Web_Page` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`arrival_id`  bigint(20) NULL DEFAULT NULL ,
`web_page_id`  bigint(20) NOT NULL ,
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

CREATE TABLE `Arrival_Tracking_Parameters` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
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

CREATE TABLE `Lead_Type` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
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

CREATE TABLE `Lead_Request` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`raw_request` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`request_status`  enum('received', 'saved', 'processing', 'processed') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`arrival_id`  bigint(20) NOT NULL ,
`lead_type_id`  bigint(20) NOT NULL ,
`token`  varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`arrival_id`) REFERENCES `Arrival` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (`lead_type_id`) REFERENCES `Lead_Type` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `arrival_id` (`arrival_id`) USING BTREE ,
INDEX `lead_type_id` (`lead_type_id`) USING BTREE,
INDEX `token` (`token`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Auto_Form` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`lead_request_id`  bigint(20) NOT NULL ,
`user_id`  bigint(20) NULL DEFAULT NULL ,
`coverage`  enum('_STATE_MINIMUM','_BASIC_COVERAGE','_STANDARD_COVERAGE','_SUPERIOR_COVERAGE') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`current_insured`  tinyint(1) NULL DEFAULT 1 ,
`current_carrier`enum('_21ST_CENTURY_INSURANCE','_AAA_AUTO_CLUB','_AIU_INSURANCE','_ALLIED','_ALLSTATE','_AMERICAN_ALLIANCE_INSURANCE','_AMERICAN_AUTOMOBILE_INSURANCE','_AMERICAN_CASUALTY','_AMERICAN_DEPOSIT_INSURANCE','_AMERICAN_DIRECT_BUSINESS_INSURANCE','_AMERICAN_EMPIRE_INSURANCE','_AMERICAN_FAMILY','_AMERICAN_FINANCIAL','_AMERICAN_HOME_ASSURANCE','_AMERICAN_INSURANCE','_AMERICAN_INTERNATIONAL_INS','_AMERICAN_INTERNATIONAL_PACIFIC','_AMERICAN_INTERNATIONAL_SOUTH','_AMERICAN_MANUFACTURERS','_AMERICAN_MAYFLOWER_INSURANCE','_AMERICAN_MOTORISTS_INSURANCE','_AMERICAN_NATIONAL_INSURANCE','_AMERICAN_PREMIER_INSURANCE','_AMERICAN_PROTECTION_INSURANCE','_AMERICAN_SERVICE_INSURANCE','_AMERICAN_SKYLINE_INSURANCE_COMPANY','_AMERICAN_SPIRIT_INSURANCE','_AMERICAN_STANDARD_INSURANCE_OH','_AMERICAN_STANDARD_INSURANCE_WI','_AMICA','_ARBELLA','_ASSOCIATED_INDEMNITY','_ATLANTA_CASUALTY','_ATLANTIC_INDEMNITY','_AUTO_CLUB_INSURANCE_COMPANY','_AUTO_OWNERS','_BLUE_CROSS_AND_BLUE_SHIELD','_CAL_FARM_INSURANCE','_CALIFORNIA_STATE_AUTOMOBILE_ASSOCIATION','_CHUBB','_CITIZENS','_CLARENDON_AMERICAN_INSURANCE','_CLARENDON_NATIONAL_INSURANCE','_CNA','_COLONIAL_INSURANCE','_CONTINENTAL_CASUALTY','_CONTINENTAL_DIVIDE_INSURANCE','_CONTINENTAL_INSURANCE','_COTTON_STATES_INSURANCE','_COUNTRY_INSURANCE_FINANCIAL_SERVICES','_DAIRYLAND_COUNTY_MUTUAL_CO_OF_TX','_DAIRYLAND_INSURANCE','_EASTWOOD_INSURANCE','_ELECTRIC_INSURANCE','_ERIE','_ESURANCE','_FARM_BUREAU','_FARMERS','_FIRE_AND_CASUALTY_INSURANCE_CO_OF_CT','_FIREMANS_FUND','_GEICO','_GMAC_INSURANCE','_GOLDEN_RULE_INSURANCE','_GOVERNMENT_EMPLOYEES_INSURANCE','_GRANGE','_GUARANTY_NATIONAL_INSURANCE','_HANOVER_LLOYDS_INSURANCE_COMPANY','_HEALTH_PLUS_OF_AMERICA','_IFA_AUTO_INSURANCE','_IGF_INSURANCE','_INFINITY_INSURANCE','_INFINITY_NATIONAL_INSURANCE','_INFINITY_SELECT_INSURANCE','_INTEGON','_KAISER_PERMANENTE','_KEMPER_LLOYDS_INSURANCE','_LANDMARK_AMERICAN_INSURANCE','_LEADER_NATIONAL_INSURANCE','_LEADER_PREFERRED_INSURANCE','_LEADER_SPECIALTY_INSURANCE','_LIBERTY_INSURANCE_CORP','_LIBERTY_MUTUAL','_LIBERTY_NORTHWEST_INSURANCE','_LUMBERMENS_MUTUAL','_MASS_MUTUAL','_MERCURY','_METLIFE','_METROPOLITAN_INSURANCE_CO','_MID_CENTURY_INSURANCE','_MID_CONTINENT_CASUALTY','_MIDDLESEX_INSURANCE','_MIDWEST_NATIONAL_LIFE','_MSI_INSURANCE','_MUTUAL_OF_NEW_YORK','_MUTUAL_OF_OMAHA','_NATIONAL_BEN_FRANKLIN_INSURANCE','_NATIONAL_CASUALTY','_NATIONAL_CONTINENTAL_INSURANCE','_NATIONAL_FIRE_INSURANCE_COMPANY_OF_HARTFORD','_NATIONAL_HEALTH_INSURANCE','_NATIONAL_INDEMNITY','_NATIONAL_UNION_FIRE_INSURANCE_OF_LA','_NATIONAL_UNION_FIRE_INSURANCE_OF_PA','_NATIONWIDE','_NEW_YORK_LIFE','_NORTHWESTERN_MUTUAL_LIFE','_NORTHWESTERN_PACIFIC_INDEMNITY','_OMNI_INDEMNITY','_OMNI_INSURANCE','_ORION_INSURANCE','_PACIFIC_INDEMNITY','_PACIFIC_INSURANCE','_PAFCO_GENERAL_INSURANCE','_PATRIOT_GENERAL_INSURANCE','_PEAK_PROPERTY_AND_CASUALTY_INSURANCE','_PEMCO_INSURANCE','_PROGRESSIVE','_PROGRESSIVE_AUTO_PRO','_PRUDENTIAL','_RELIANCE_INSURANCE','_RELIANCE_NATIONAL_INDEMNITY','_RELIANCE_NATIONAL_INSURANCE','_REPUBLIC_INDEMNITY','_RESPONSE_INSURANCE','_SAFECO','_SAFEWAY_INSURANCE','_SAFEWAY_INSURANCE_CO_OF_AL','_SAFEWAY_INSURANCE_CO_OF_GA','_SAFEWAY_INSURANCE_CO_OF_LA','_SECURITY_INSURANCE_CO_OF_HARTFORD','_SECURITY_NATIONAL_INSURANCE_CO_OF_FL','_SENTINEL_INSURANCE','_SENTRY_INSURANCE_A_MUTUAL_COMPANY','_SENTRY_INSURANCE_GROUP','_SHELTER_INSURANCE_CO','_ST_PAUL','_ST_PAUL_FIRE_AND_MARINE','_ST_PAUL_INSURANCE','_STANDARD_FIRE_INSURANCE_COMPANY','_STATE_AND_COUNTY_MUTUAL_FIRE_INSURANCE','_STATE_FARM','_STATE_FARM_MUTUAL_AUTO','_STATE_NATIONAL_INSURANCE','_SUPERIOR_AMERICAN_INSURANCE','_SUPERIOR_GUARANTY_INSURANCE','_SUPERIOR_INSURANCE','_THE_AHBE_GROUP','_THE_GENERAL','_THE_HARTFORD','_TICO_INSURANCE','_TIG_COUNTRYWIDE_INSURANCE','_TRAVELERS','_TRAVELERS_INDEMNITY','_TRI_STATE_CONSUMER_INSURANCE','_TWIN_CITY_FIRE_INSURANCE','_UNICARE','_UNITED_PACIFIC_INSURANCE','_UNITED_SECURITY','_UNITED_SERVICES_AUTOMOBILE_ASSOCIATION','_UNITRIN_DIRECT','_USAA','_USF_AND_G','_VIKING_COUNTY_MUTUAL_INSURANCE','_VIKING_INSURANCE_CO_OF_WI','_WINDSOR_INSURANCE','_WOODLANDS_FINANCIAL_GROUP') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`current_exp`  date NULL DEFAULT NULL ,
`years_insured`  enum('_LESS_THAN_1_YEAR','_1_2_YEARS','_2_3_YEARS','_3_4_YEARS','_4_5_YEARS','_5_YEARS_OR_MORE') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`current_liability`  enum('_20K_50K','_30K_60K','_50K_100K','_100K_300K','_250K_500K','_500K_500K') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`zip`  varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`street`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`city`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`state`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`email`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`phone`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`years_lived`  enum('_LESS_THAN_1_YEAR','_1_2_YEARS','_2_3_YEARS','_3_4_YEARS','_4_5_YEARS','_5_YEARS_OR_MORE') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`lead_request_id`) REFERENCES `Lead_Request` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `Auto_Form_ibfk_1` (`lead_request_id`) USING BTREE ,
INDEX `zipcode` (`zip`) USING BTREE ,
INDEX `state` (`state`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Claim` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`auto_form_id`  bigint(20) NOT NULL ,
`type`  enum('_FIRE_HAIL_OR_WATER_DAMAGE','_VANDALISM','_VEHICLE_HIT_ANIMAL','_VEHICLE_STOLEN','_WINDSHIELD_DAMAGE','_OTHER') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`date`  date NULL DEFAULT NULL ,
`paid`  double(20,2) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`auto_form_id`) REFERENCES `Auto_Form` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `Claim_ibfk_1` (`auto_form_id`) USING BTREE ,
INDEX `claim_type` (`type`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Violation` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`auto_form_id`  bigint(20) NOT NULL ,
`type`  enum('_CHILD_SEAT','_FAILURE_TO_STOP','_ILLEGAL_U_TURN','_SEAT_BELT','_OTHER_MINOR','_OTHER_YIELD','_DRUNK_DRIVING_INJURY','_DRUNK_DRIVING_NO_INJURY','_SUSPENSION','_DRIVING_WHILE_SUSPENDED','_RECKLESS_DRIVING_INJURY','_RECKLESS_DRIVING_NO_INJURY','_HIT_AND_RUN_INJURY','_HIT_AND_RUN_NO_INJURY','_SPEED_CONTEST_EXHIBITION','_SPEEDING_OVER_100','_CHARGEABLE_ACCIDENT_NO_INJURY','_CHARGEABLE_ACCIDENT_INJURY','_NON_CHARGEABLE_ACCIDENT') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`date`  date NULL DEFAULT NULL ,
`paid`  double(20,2) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`auto_form_id`) REFERENCES `Auto_Form` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `Violation_ibfk_1` (`auto_form_id`) USING BTREE ,
INDEX `violation_type` (`type`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Vehicle` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`auto_form_id`  bigint(20) NOT NULL ,
`year`  year NULL DEFAULT NULL ,
`make`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`model`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ownership`  enum('_OWNED','_FINANCED','_LEASED') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`use`  enum('_COMMUTE','_PLEASURE','_BUSINESS') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mileage`  enum('_5000_MILES','_7500_MILES','_10000_MILES','_12500_MILES','_15000_MILES','_18000_MILES','_25000_MILES','_50000_MILES','_MORE_THAN_50000_MILES') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`parking`  enum('_PRIVATE_GARAGE','_GARAGE','_CARPORT','_DRIVEWAY','_PARKING_LOT','_STREET') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`auto_form_id`) REFERENCES `Auto_Form` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `Vehicle_ibfk_1` (`auto_form_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Driver` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`auto_form_id`  bigint(20) NOT NULL ,
`relationship`  enum('_SELF','_SPOUSE','_PARENT','_SIBLING','_CHILD','_GRANDPARENT','_GRANDCHILD','_OTHER') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`first_name`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`last_name`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`dob`  date NULL DEFAULT NULL ,
`gender`  enum('_MALE','_FEMALE') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`marital`  enum('_SINGLE','_MARRIED','_DIVORCED','_WIDOWED') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`occupation`  enum('_ADMINISTRATIVE_CLERICAL','_ARCHITECT','_BUSINESS_OWNER','_CERTIFIED_PUBLIC_ACCOUNTANT','_CLERGY','_CONSTRUCTION_TRADES','_DENTIST','_DISABLED','_ENGINEER','_HOMEMAKER','_LAWYER','_MANAGER_SUPERVISOR','_MILITARY_OFFICER','_MILITARY_ENLISTED','_MINOR_NA','_OTHER_NON_TECHNICAL','_OTHER_TECHNICAL','_PHYSICIAN','_PROFESSIONAL_SALARIED','_PROFESSOR','_RETAIL','_RETIRED','_SALES_INSIDE','_SALES_OUTSIDE','_SCHOOL_TEACHER','_SCIENTIST','_SELF_EMPLOYED','_SKILLED_SEMI_SKILLED','_STUDENT','_UNEMPLOYED') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`education`  enum('_SOME_NO_HIGH_SCHOOL','_HIGH_SCHOOL_DIPLOMA','_GED','_SOME_COLLEGE','_ASSOCIATE_DEGREE','_BACHELORS_DEGREE','_MASTERS_DEGREE','_DOCTORATE_DEGREE','_OTHER_PROFESSIONAL_DEGREE','_OTHER_NON_PROF_DEGREE','_TRADE_VOCATIONAL_SCHOOL') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`residence`  enum('_OWN','_RENT','_OTHER') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`credit`  enum('_EXCELLENT','_GOOD','_AVERAGE','_POOR','_UNSURE') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`age_lic`  int(3) NULL DEFAULT NULL ,
`license_suspended`  tinyint(1) NULL DEFAULT 0 ,
`sr22`  tinyint(1) NULL DEFAULT 0 ,
PRIMARY KEY (`id`),
FOREIGN KEY (`auto_form_id`) REFERENCES `Auto_Form` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `Driver_ibfk_1` (`auto_form_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Buyer` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`buyer_id`  bigint(20) NOT NULL,
`lead_type_id`  bigint(20) NOT NULL,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`buyer_account_id`  bigint(20) NOT NULL,
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

CREATE TABLE `Click_Impression_Request` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`arrival_id`  bigint(20) NOT NULL DEFAULT 0 ,
`location`  enum('landing','after_form','exit_page') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`lead_type_id`  bigint(20) NOT NULL,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`click_impression_request_id`  bigint(20) NOT NULL,
`buyer_account_id`  bigint(20) NOT NULL,
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

CREATE TABLE `Click_Ad` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`click_impression_id`  bigint(20) NOT NULL,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`click_impression_id`  bigint(20) NOT NULL,
`click_ad_id`  bigint(20) DEFAULT NULL,
`amount`  DECIMAL(9,3) DEFAULT NULL,
`adjusted_amount`  DECIMAL(9,3) DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`click_impression_id`) REFERENCES `Click_Impression` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (`click_ad_id`) REFERENCES `Click_Ad` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
key(created_ts),
key(updated_ts)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Lead_Sell` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`lead_request_id`  bigint(20) NOT NULL,
`buyer_account_id`  bigint(20) NOT NULL,
`sell_state`  enum('ERROR','SOLD', 'REJECTED', 'EXCEPTION') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
`amount`  DECIMAL(9,3) DEFAULT NULL ,
`adjusted_amount`  DECIMAL(9,3) DEFAULT NULL ,
`comment`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`post_url`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`post_entity`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`lead_request_id`) REFERENCES `Lead_Request` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (`buyer_account_id`) REFERENCES `Buyer_Account` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
key(created_ts),
key(updated_ts),
unique key(lead_request_id, buyer_account_id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `AB_Test_Group` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`web_page_id`  bigint(20) NOT NULL,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`ab_test_group_id`  bigint(20) NOT NULL,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`ab_test_id`  bigint(20) NOT NULL,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`arrival_id`  bigint(20) NOT NULL ,
`ab_test_option_id`  bigint(20) NOT NULL ,
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

CREATE TABLE Redirect(
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`redirect_id` bigint(20) NOT NULL,
`action_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`action_position`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (redirect_id) REFERENCES Redirect(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Carrier_List` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `tag` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`) USING BTREE,
  KEY `tag` (`tag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

CREATE TABLE `Keyword_Carrier_Map` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`ad_group_keyword_id`  bigint(20) NOT NULL ,
`carrier_list_id`  bigint(20) NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`carrier_list_id`) REFERENCES `Carrier_List` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
INDEX `ad_group_keyword_id` (`ad_group_keyword_id`) USING BTREE ,
INDEX `Keyword_Carrier_Map_ibfk_1` (`carrier_list_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

-- Email Function
CREATE TABLE Email_Record_Unique(
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
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

CREATE TABLE Email_Record(
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`email_address`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`email_domain`  varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`status`  enum('active','closed') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`arrival_id`  bigint(20) NULL DEFAULT NULL ,
`user_id`  bigint(20) NULL DEFAULT NULL ,
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
id bigint(20) NOT NULL AUTO_INCREMENT,
email_record_id bigint(20) NOT NULL,
name varchar(127) NOT NULL,
value varchar(1023) DEFAULT NULL,
PRIMARY KEY (id),
FOREIGN KEY (email_record_id) REFERENCES Email_Record(id),
unique key(email_record_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Email_Record_Property_Field` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT,
`email_record_property_field`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Email_Program(
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
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
id bigint(20) NOT NULL AUTO_INCREMENT,
email_program_id bigint(20) NOT NULL,
name varchar(127) NOT NULL,
value varchar(1023) DEFAULT NULL,
PRIMARY KEY (id),
FOREIGN KEY (email_program_id) REFERENCES Email_Program(id),
unique key(email_program_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Email_Role(
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
name  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
email_program_id bigint(20) NOT NULL,
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
id bigint(20) NOT NULL AUTO_INCREMENT,
email_role_id bigint(20) NOT NULL,
name varchar(127) NOT NULL,
value varchar(1023) DEFAULT NULL,
PRIMARY KEY (id),
FOREIGN KEY (email_role_id) REFERENCES Email_Role(id),
unique key(email_role_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Email_Record_Role_Map(
id bigint(20) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_record_id bigint(20) NOT NULL,
email_role_id bigint(20) NOT NULL,
status enum ('active', 'paused', 'closed') NOT NULL,
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
FOREIGN KEY (email_record_id) REFERENCES Email_Record(id),
FOREIGN KEY (email_role_id) REFERENCES Email_Role(id),
unique key(email_record_id, email_role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Email_Template(
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_template_id bigint(20) NOT null,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
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

CREATE TABLE Email_Program_Step(
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_program_id bigint(20) NOT NULL,
step_name  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
email_template_id bigint(20) NOT null,
email_from_id bigint(20) NOT null,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`email_program_step_id`  bigint(20) NOT NULL ,
`email_rule_id`  bigint(20) NOT NULL ,
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

CREATE TABLE Email_Send(
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_record_id bigint(20) NOT NULL,
email_role_id bigint(20) NOT NULL,
email_program_step_id bigint(20) NOT NULL,
email_template_id bigint(20) NOT null,
email_from_id bigint(20) NOT null,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_sent_id bigint(20) NOT NULL,
Email_template_link_id bigint(20) NOT NULL,
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

CREATE TABLE `Email_Replacement_Text` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
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