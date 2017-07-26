DROP TABLE IF EXISTS Violation;
DROP TABLE IF EXISTS Claim;
DROP TABLE IF EXISTS Incident;
DROP TABLE IF EXISTS Driver;
DROP TABLE IF EXISTS Vehicle;
DROP TABLE IF EXISTS Auto_Form;

CREATE TABLE `Auto_Form` (
  `id`  bigint(20) NOT NULL AUTO_INCREMENT ,
  `created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  `lead_request_id`  bigint(20) NOT NULL ,
  `user_id`  bigint(20) NULL DEFAULT NULL ,
  `is_currently_insured`  tinyint(1) NULL DEFAULT 1 ,
  `current_company` enum('_21ST_CENTURY_INSURANCE','_AAA_AUTO_CLUB','_AIU_INSURANCE','_ALLIED','_ALLSTATE','_AMERICAN_ALLIANCE_INSURANCE','_AMERICAN_AUTOMOBILE_INSURANCE','_AMERICAN_CASUALTY','_AMERICAN_DEPOSIT_INSURANCE','_AMERICAN_DIRECT_BUSINESS_INSURANCE','_AMERICAN_EMPIRE_INSURANCE','_AMERICAN_FAMILY','_AMERICAN_FINANCIAL','_AMERICAN_HOME_ASSURANCE','_AMERICAN_INSURANCE','_AMERICAN_INTERNATIONAL_INS','_AMERICAN_INTERNATIONAL_PACIFIC','_AMERICAN_INTERNATIONAL_SOUTH','_AMERICAN_MANUFACTURERS','_AMERICAN_MAYFLOWER_INSURANCE','_AMERICAN_MOTORISTS_INSURANCE','_AMERICAN_NATIONAL_INSURANCE','_AMERICAN_PREMIER_INSURANCE','_AMERICAN_PROTECTION_INSURANCE','_AMERICAN_SERVICE_INSURANCE','_AMERICAN_SKYLINE_INSURANCE_COMPANY','_AMERICAN_SPIRIT_INSURANCE','_AMERICAN_STANDARD_INSURANCE_OH','_AMERICAN_STANDARD_INSURANCE_WI','_AMICA','_ARBELLA','_ASSOCIATED_INDEMNITY','_ATLANTA_CASUALTY','_ATLANTIC_INDEMNITY','_AUTO_CLUB_INSURANCE_COMPANY','_AUTO_OWNERS','_BLUE_CROSS_AND_BLUE_SHIELD','_CAL_FARM_INSURANCE','_CALIFORNIA_STATE_AUTOMOBILE_ASSOCIATION','_CHUBB','_CITIZENS','_CLARENDON_AMERICAN_INSURANCE','_CLARENDON_NATIONAL_INSURANCE','_CNA','_COLONIAL_INSURANCE','_CONTINENTAL_CASUALTY','_CONTINENTAL_DIVIDE_INSURANCE','_CONTINENTAL_INSURANCE','_COTTON_STATES_INSURANCE','_COUNTRY_INSURANCE_FINANCIAL_SERVICES','_DAIRYLAND_COUNTY_MUTUAL_CO_OF_TX','_DAIRYLAND_INSURANCE','_EASTWOOD_INSURANCE','_ELECTRIC_INSURANCE','_ERIE','_ESURANCE','_FARM_BUREAU','_FARMERS','_FIRE_AND_CASUALTY_INSURANCE_CO_OF_CT','_FIREMANS_FUND','_GEICO','_GMAC_INSURANCE','_GOLDEN_RULE_INSURANCE','_GOVERNMENT_EMPLOYEES_INSURANCE','_GRANGE','_GUARANTY_NATIONAL_INSURANCE','_HANOVER_LLOYDS_INSURANCE_COMPANY','_HEALTH_PLUS_OF_AMERICA','_IFA_AUTO_INSURANCE','_IGF_INSURANCE','_INFINITY_INSURANCE','_INFINITY_NATIONAL_INSURANCE','_INFINITY_SELECT_INSURANCE','_INTEGON','_KAISER_PERMANENTE','_KEMPER_LLOYDS_INSURANCE','_LANDMARK_AMERICAN_INSURANCE','_LEADER_NATIONAL_INSURANCE','_LEADER_PREFERRED_INSURANCE','_LEADER_SPECIALTY_INSURANCE','_LIBERTY_INSURANCE_CORP','_LIBERTY_MUTUAL','_LIBERTY_NORTHWEST_INSURANCE','_LUMBERMENS_MUTUAL','_MASS_MUTUAL','_MERCURY','_METLIFE','_METROPOLITAN_INSURANCE_CO','_MID_CENTURY_INSURANCE','_MID_CONTINENT_CASUALTY','_MIDDLESEX_INSURANCE','_MIDWEST_NATIONAL_LIFE','_MSI_INSURANCE','_MUTUAL_OF_NEW_YORK','_MUTUAL_OF_OMAHA','_NATIONAL_BEN_FRANKLIN_INSURANCE','_NATIONAL_CASUALTY','_NATIONAL_CONTINENTAL_INSURANCE','_NATIONAL_FIRE_INSURANCE_COMPANY_OF_HARTFORD','_NATIONAL_HEALTH_INSURANCE','_NATIONAL_INDEMNITY','_NATIONAL_UNION_FIRE_INSURANCE_OF_LA','_NATIONAL_UNION_FIRE_INSURANCE_OF_PA','_NATIONWIDE','_NEW_YORK_LIFE','_NORTHWESTERN_MUTUAL_LIFE','_NORTHWESTERN_PACIFIC_INDEMNITY','_OMNI_INDEMNITY','_OMNI_INSURANCE','_ORION_INSURANCE','_PACIFIC_INDEMNITY','_PACIFIC_INSURANCE','_PAFCO_GENERAL_INSURANCE','_PATRIOT_GENERAL_INSURANCE','_PEAK_PROPERTY_AND_CASUALTY_INSURANCE','_PEMCO_INSURANCE','_PROGRESSIVE','_PROGRESSIVE_AUTO_PRO','_PRUDENTIAL','_RELIANCE_INSURANCE','_RELIANCE_NATIONAL_INDEMNITY','_RELIANCE_NATIONAL_INSURANCE','_REPUBLIC_INDEMNITY','_RESPONSE_INSURANCE','_SAFECO','_SAFEWAY_INSURANCE','_SAFEWAY_INSURANCE_CO_OF_AL','_SAFEWAY_INSURANCE_CO_OF_GA','_SAFEWAY_INSURANCE_CO_OF_LA','_SECURITY_INSURANCE_CO_OF_HARTFORD','_SECURITY_NATIONAL_INSURANCE_CO_OF_FL','_SENTINEL_INSURANCE','_SENTRY_INSURANCE_A_MUTUAL_COMPANY','_SENTRY_INSURANCE_GROUP','_SHELTER_INSURANCE_CO','_ST_PAUL','_ST_PAUL_FIRE_AND_MARINE','_ST_PAUL_INSURANCE','_STANDARD_FIRE_INSURANCE_COMPANY','_STATE_AND_COUNTY_MUTUAL_FIRE_INSURANCE','_STATE_FARM','_STATE_FARM_MUTUAL_AUTO','_STATE_NATIONAL_INSURANCE','_SUPERIOR_AMERICAN_INSURANCE','_SUPERIOR_GUARANTY_INSURANCE','_SUPERIOR_INSURANCE','_THE_AHBE_GROUP','_THE_GENERAL','_THE_HARTFORD','_TICO_INSURANCE','_TIG_COUNTRYWIDE_INSURANCE','_TRAVELERS','_TRAVELERS_INDEMNITY','_TRI_STATE_CONSUMER_INSURANCE','_TWIN_CITY_FIRE_INSURANCE','_UNICARE','_UNITED_PACIFIC_INSURANCE','_UNITED_SECURITY','_UNITED_SERVICES_AUTOMOBILE_ASSOCIATION','_UNITRIN_DIRECT','_USAA','_USF_AND_G','_VIKING_COUNTY_MUTUAL_INSURANCE','_VIKING_INSURANCE_CO_OF_WI','_WINDSOR_INSURANCE','_WOODLANDS_FINANCIAL_GROUP','_COMPANY_NOT_LISTED') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `continuous_coverage`  enum('_LESS_THAN_6_MONTHS','_6_MONTHS','_1_YEAR','_2_YEARS','_3_YEARS','_3_TO_5_YEARS','_5_TO_10_YEARS','_OVER_10_YEARS') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `years_with_company`  enum('_LESS_THAN_6_MONTHS','_6_MONTHS','_1_YEAR','_2_YEARS','_3_YEARS','_OVER_3_YEARS') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `expire_time`  enum('_NOT_SURE','_A_FEW_DAYS','_2_WEEKS','_1_MONTH','_2_MONTHS','_3_MONTHS','_4_TO_6_MONTHS','_MORE_THAN_6_MONTHS') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `coverage_type`  enum('_SUPERIOR_PROTECTION','_STANDARD_PROTECTION','_STATE_MINIMUM') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `email`  varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `phone`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `street`  varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `apt` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `zip`  varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `city`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `state`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `years_lived`  enum('_LESS_THAN_1_YEAR','_1_YEAR','_2_YEARS','_3_TO_5_YEARS','_5_TO_10_YEARS','_OVER_10_YEARS') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `is_owned`  tinyint(1) NULL DEFAULT 1 ,
  `have_garage`  tinyint(1) NULL DEFAULT 1 ,
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

CREATE TABLE `Vehicle` (
  `id`  bigint(20) NOT NULL AUTO_INCREMENT ,
  `created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  `auto_form_id`  bigint(20) NOT NULL ,
  `year`  year NULL DEFAULT NULL ,
  `make`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `model`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `trim`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `is_alarm_track`  tinyint(1) NULL DEFAULT 0 ,
  `is_commute`  tinyint(1) NULL DEFAULT 1 ,
  `commute_distance`  enum('_LESS_THAN_5_MILES','_5_MILES','_10_MILES','_15_MILES','_20_MILES','_30_MILES','_OVER_30_MILES') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `is_leased`  tinyint(1) NULL DEFAULT 0 ,
  `yearly_mileage`  enum('_5000_MILES','_7500_MILES','_10000_MILES','_12500_MILES','_20000_MILES','_25000_MILES','_30000_MILES','_40000_MILES','_50000_MILES') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `deductible_coll`  enum('_100','_250','_500','_1000','_NO_COVERAGE') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `deductible_comp`  enum('_100','_250','_500','_1000','_NO_COVERAGE') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
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
  `relationship`  enum('_SELF','_SPOUSE','_CHILD','_OTHER') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `first_name`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `last_name`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `gender`  enum('_MALE','_FEMALE') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `is_married`  tinyint(1) NULL DEFAULT 0 ,
  `birthday`  date NULL DEFAULT NULL ,
  `age_lic`  int(3) NULL DEFAULT NULL ,
  `education`  enum('_INCOMPLETE','_HIGH_SCHOOL','_SOME_COLLEGE','_ASSOCIATE_DEGREE','_BACHELORS_DEGREE','_MASTERS_DEGREE','_PHD') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `credit`  enum('_EXCELLENT','_GOOD','_AVERAGE','_BELOW_AVERAGE','_POOR') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `occupation`  enum('_ADMINISTRATIVE_CLERICAL','_ARCHITECT','_BUSINESS_OWNER','_CERTIFIED_PUBLIC_ACCOUNTANT','_CLERGY','_CONSTRUCTION_TRADES','_CONSULTANT','_DENTIST','_DISABLED','_ENGINEER','_FINANCIAL_SERVICES','_HEALTH_CARE','_HOMEMAKER','_HUMAN_RELATIONS','_LAWYER','_MARKETING','_MANAGER_SUPERVISOR','_MILITARY_ENLISTED','_MINOR_NOT_APPLICABLE','_OTHER_NOT_LISTED','_OTHER_NON_TECHNICAL','_OTHER_TECHNICAL','_PHYSICIAN','_PROFESSIONAL_SALARIED','_PROFESSOR','_RETAIL','_RETIRED','_SALES_INSIDE','_SALES_OUTSIDE','_SCHOOL_TEACHER','_SCIENTIST','_SELF_EMPLOYED','_SKILLED_SEMI_SKILLED','_STUDENT','_TRANSPORTATION_OR_LOGISTICS','_UNEMPLOYED') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `is_lic_active`  tinyint(1) NULL DEFAULT 0 ,
  `is_sr22_required`  tinyint(1) NULL DEFAULT 0 ,
  `primary_vehicle_id`  bigint(20) NOT NULL ,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`auto_form_id`) REFERENCES `Auto_Form` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  FOREIGN KEY (`primary_vehicle_id`) REFERENCES `Vehicle` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  INDEX `Driver_ibfk_1` (`auto_form_id`) USING BTREE,
  INDEX `Driver_ibfk_2` (`primary_vehicle_id`) USING BTREE
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=1
  ROW_FORMAT=COMPACT
;

CREATE TABLE `Incident` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `updated_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  `auto_form_id`  bigint(20) NOT NULL ,
  `incident_type` enum('_TICKET','_CLAIM','_ACCIDENT','_DUI','_SUSPENSION') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `driver_id` bigint(20) NOT NULL ,
  `estimated_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  `description`  enum('_TICKET_SPEEDING_LESS_THAN_10_MPH_OVER','_TICKET_SPEEDING_MORE_THAN_10_MPH_OVER','_TICKET_SPEEDING_MORE_THAN_20_MPH_OVER','_TICKET_ALCOHOL_RELATED_DRUG_POSSESSION','_TICKET_ALCOHOL_RELATED_MINOR_IN_POSSESSION','_TICKET_ALCOHOL_RELATED_OPEN_CONTAINER','_TICKET_ALCOHOL_RELATED_DUI_DWAI','_TICKET_CARELESS_DRIVING','_TICKET_CARPOOL_LANE_VIOLATION','_TICKET_CHILD_NOT_IN_CAR_SEAT','_TICKET_DEFECTIVE_EQUIPMENT','_TICKET_DEFECTIVE_VEHICLE_REDUCED_VIOLATION','_TICKET_DRIVING_TOO_FAST_FOR_CONDITIONS','_TICKET_DRIVING_WITHOUT_A_LICENSE','_TICKET_EXCESSIVE_NOISE','_TICKET_EXHIBITION_DRIVING','_TICKET_EXPIRED_DRIVERS_LICENSE','_TICKET_EXPIRED_EMISSIONS','_TICKET_EXPIRED_REGISTRATION','_TICKET_FAILURE_TO_OBEY_TRAFFIC_SIGNAL','_TICKET_FAILURE_TO_SIGNAL','_TICKET_FAILURE_TO_STOP','_TICKET_FAILURE_TO_YIELD','_TICKET_FOLLOWING_TOO_CLOSE','_TICKET_ILLEGAL_LANE_CHANGE','_TICKET_ILLEGAL_PASSING','_TICKET_ILLEGAL_TURN','_TICKET_ILLEGAL_TURN_ON_RED','_TICKET_ILLEGAL_U_TURN','_TICKET_INATTENTIVE_DRIVING','_TICKET_NO_INSURANCE','_TICKET_NO_SEATBELT','_TICKET_OTHER_UNLISTED_MOVING_VIOLATION','_TICKET_PASSING_A_SCHOOL_BUS','_TICKET_PASSING_IN_A_NO_PASSING_ZONE','_TICKET_PASSING_ON_SHOULDER','_TICKET_RAN_A_RED_LIGHT','_TICKET_RAN_A_STOP_SIGN','_TICKET_RECKLESS_DRIVING','_TICKET_RECKLESS_ENDANGERMENT','_TICKET_WRONG_WAY_ON_A_ONE_WAY', '_CLAIM_ACT_OF_NATURE','_CLAIM_CAR_FIRE','_CLAIM_FLOOD_DAMAGE','_CLAIM_HAIL_DAMAGE','_CLAIM_HIT_AN_ANIMAL','_CLAIM_THEFT_OF_STEREO','_CLAIM_THEFT_OF_VEHICLE','_CLAIM_TOWING_SERVICE','_CLAIM_VANDALISM','_CLAIM_WINDSHIELD_REPLACEMENT','_CLAIM_OTHER', '_ACCIDENT_COLLIDED_WITH_ANOTHER_CAR','_ACCIDENT_HIT_WHILE_STOPPED','_ACCIDENT_HIT_BY_ANOTHER_PERSON','_ACCIDENT_REAR_ENDED_BY_ANOTHER_PERSON','_ACCIDENT_SINGLE_CAR_ACCIDENT','_ACCIDENT_OTHER','_DUI','_SUSPENSION') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `damage` enum('_NO_DAMAGE','_PROPERTY','_PEOPLE','_BOTH') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `amount_paid`  enum('_NOT_SURE','_500','_500_TO_1000','_1000_TO_2000','_2000_TO_5000','_10000','_20000','_30000','_OVER_30000') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `is_at_fault`  tinyint(1) NULL DEFAULT 1 ,
  `happened_state` enum('_AL','_AK','_AZ','_AR','_CA','_CO','_CT','_DE','_DC','_FL','_GA','_HI','_ID','_IL','_IN','_IA','_KS','_KY','_LA','_ME','_MD','_MA','_MI','_MN','_MS','_MO','_MT','_NE','_NV','_NH','_NJ','_NM','_NY','_NC','_ND','_OH','_OK','_OR','_PA','_RI','_SC','_SD','_TN','_TX','_UT','_VT','_VA','_WA','_WV','_WI','_WY') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`driver_id`) REFERENCES `Driver` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  INDEX `Incident_ibfk_1` (`driver_id`) USING BTREE
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=1
  ROW_FORMAT=COMPACT
;