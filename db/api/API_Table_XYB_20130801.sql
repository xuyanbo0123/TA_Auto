-- Change Log:

-- XYB: 20130713
-- XYB: For Table: Campaign_Budget, Traffic_Campaign, Traffic_Campaign_Geo_Target, Ad_Group, Ad_Group_Keyword, Ad_Group_Ad
-- XYB: ADD:
--      `is_uploaded`  tinyint(1) NOT NULL ,
--      INDEX `is_uploaded` (`is_uploaded`) USING BTREE ,
-- XYB: DELETE
--      `pending_upload` in provider_status

-- XYB: 20130714
-- XYB: Ad_Group_Keyword_Bid Table: Delete `uploaded_ts` field
-- XYB: Redesign Traffic_Campaign Table
-- XYB: Delete Traffic_Campaign_Budget_Map Table
-- XYB: Ad_Group Table: Delete other unique key except `provider_supplied_id`

-- XYB: 20130715
-- XYB: Ad_Group Table: Change `uploaded_ts` from NULL to NOT NULL

-- XYB: 20130728
-- XYB: Change all id size from int(11) to bigint(20)

-- XYB: 20130802
-- XYB: Change field size for Table: Text_Ad
-- XYB: Add an unique_key for Table: Text_Ad



-- XYB: Total Count: 23
-- Emergency Function
DROP TABLE IF EXISTS Emergency_Campaign_Record;
DROP TABLE IF EXISTS Servlet_Activity_Record;
DROP TABLE IF EXISTS System_Emergency_Status;

-- Report Function
drop table if EXISTS Ad_Group_Keyword_Daily_Spending_Rollup;
drop table if EXISTS Ad_Group_Keyword_Daily_Spending;
drop table if EXISTS Ad_Group_Keyword_Hourly_Spending;
drop table if EXISTS Ad_Group_Keyword_Daily_Revenue_Rollup;
drop table if EXISTS Ad_Group_Keyword_Daily_Revenue;
drop table if EXISTS Ad_Group_Keyword_Hourly_Revenue;
DROP TABLE IF EXISTS Ad_Group_Keyword_Performance;
DROP TABLE IF EXISTS Ad_Group_Ad_Performance;

-- API Function
DROP TABLE IF EXISTS Ad_Group_Ad;
DROP TABLE IF EXISTS Text_Ad;
DROP TABLE IF EXISTS Ad_Group_Keyword_Bid;
DROP TABLE IF EXISTS Ad_Group_Keyword;
DROP TABLE IF EXISTS Keyword;
DROP TABLE IF EXISTS Ad_Group_Property;
DROP TABLE IF EXISTS Ad_Group;
DROP TABLE IF EXISTS Traffic_Campaign_Schedule;
DROP TABLE IF EXISTS Traffic_Campaign_Geo_Target;
DROP TABLE IF EXISTS Traffic_Campaign_Property;
DROP TABLE IF EXISTS Traffic_Campaign;
DROP TABLE IF EXISTS Campaign_Budget;



-- API Function
CREATE TABLE `Campaign_Budget` (
  `id`  bigint(20) NOT NULL AUTO_INCREMENT ,
  `created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  `traffic_provider_id`  bigint(20) NOT NULL ,
  `provider_supplied_id`  bigint(20) NULL DEFAULT NULL ,
  `name`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
  `local_status`  enum('active','deleted','unknown') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
  `provider_status`  enum('active','deleted','unknown') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `period`  enum('daily') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
  `delivery_method`  enum('standard','accelerated') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
  `amount`  decimal(10,2) NOT NULL ,
  `uploaded_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  `is_uploaded`  tinyint(1) NOT NULL ,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`traffic_provider_id`) REFERENCES `Traffic_Provider` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  UNIQUE INDEX `provider_supplied_id` (`provider_supplied_id`) USING BTREE ,
  INDEX `Campaign_Budget_ibfk_1` (`traffic_provider_id`) USING BTREE ,
  INDEX `is_uploaded` (`is_uploaded`) USING BTREE
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=1
  ROW_FORMAT=COMPACT
;

CREATE TABLE Traffic_Campaign (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
sid bigint(20) not null,
provider_supplied_id bigint(20) DEFAULT null,
`local_status` enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`provider_status` enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
`uploaded_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`is_uploaded`  tinyint(1) NOT NULL ,
`campaign_budget_id`  bigint(20) NOT NULL ,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`campaign_budget_id`) REFERENCES `Campaign_Budget` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  FOREIGN KEY (`sid`) REFERENCES `Traffic_Source` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  UNIQUE INDEX `provider_supplied_id` (`provider_supplied_id`) USING BTREE ,
  INDEX `uploaded_ts` (`uploaded_ts`) USING BTREE ,
  INDEX `name` (`name`) USING BTREE ,
  INDEX `sid` (`sid`) USING BTREE ,
  INDEX `is_uploaded` (`is_uploaded`) USING BTREE ,
  INDEX `traffic_campaign_ibfk_2` (`campaign_budget_id`) USING BTREE
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=1
  ROW_FORMAT=COMPACT;

CREATE TABLE Traffic_Campaign_Property(
id bigint(20) NOT NULL AUTO_INCREMENT,
traffic_campaign_id bigint(20) NOT NULL,
name varchar(127) NOT NULL,
value varchar(1023) DEFAULT NULL,
PRIMARY KEY (id),
FOREIGN KEY (traffic_campaign_id) REFERENCES Traffic_Campaign(id),
unique key(traffic_campaign_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Traffic_Campaign_Geo_Target(
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`target_type`  enum('positive','negative') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`traffic_campaign_id`  bigint(20) NOT NULL ,
`criteria_id`  bigint(20) NOT NULL ,
`local_status`  enum('add','remove') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`provider_status`  enum('add','remove') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`targeting_status`  enum('active','obsolete','phasing_out') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`uploaded_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`is_uploaded`  tinyint(1) NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`traffic_campaign_id`) REFERENCES `Traffic_Campaign` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
UNIQUE INDEX `campaign_criteria` (`traffic_campaign_id`, `criteria_id`) USING BTREE ,
INDEX `target_type` (`target_type`) USING BTREE ,
INDEX `traffic_campaign_id` (`traffic_campaign_id`) USING BTREE ,
INDEX `criteria_id` (`criteria_id`) USING BTREE ,
INDEX `is_uploaded` (`is_uploaded`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Traffic_Campaign_Schedule(
`id`  bigint(20) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
traffic_campaign_id bigint(20) not null,
schedule_type varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 'Daily', 'Weekday', 'Weekend', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri','Sat', 'Sun' ...
start_time varchar(127) NOT NULL, -- 08:45
end_time varchar(127) NOT NULL, -- 22:45
provider_supplied_id bigint(20) DEFAULT null,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
traffic_campaign_id bigint(20) not null,
name varchar(127) NOT NULL,
`local_status` enum('enabled', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`provider_status` enum('enabled', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
provider_supplied_id bigint(20) DEFAULT null,
`uploaded_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`is_uploaded`  tinyint(1) NOT NULL ,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(uploaded_ts),
INDEX `is_uploaded` (`is_uploaded`) USING BTREE ,
UNIQUE INDEX `provider_supplied_id` (`provider_supplied_id`) USING BTREE ,
FOREIGN KEY (traffic_campaign_id) REFERENCES Traffic_Campaign(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Ad_Group_Property(
id bigint(20) NOT NULL AUTO_INCREMENT,
ad_group_id bigint(20) NOT NULL,
name varchar(127) NOT NULL,
value varchar(1023) DEFAULT NULL,
PRIMARY KEY (id),
FOREIGN KEY (ad_group_id) REFERENCES Ad_Group(id),
unique key(ad_group_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Keyword(
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
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
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_id bigint(20) not null,
keyword_id bigint(20) not null,
match_type enum('exact', 'broad ', 'phrase') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
provider_supplied_id bigint(20) DEFAULT null,
uploaded_ts timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`local_status` enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`provider_status` enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
`serving_status`  enum('eligible','rarely_served') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`approval_status`  enum('approved','pending_review','under_review','disapproved') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`criterion_use`  enum('biddable','negative') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`bid_type`  enum('cpc') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`bid_amount`  decimal(8,3) NULL DEFAULT NULL ,
`is_uploaded`  tinyint(1) NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`ad_group_id`) REFERENCES `Ad_Group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`keyword_id`) REFERENCES `Keyword` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
UNIQUE INDEX `unique_key` (`ad_group_id`, `provider_supplied_id`) USING BTREE ,
INDEX `uploaded_ts` (`uploaded_ts`) USING BTREE ,
INDEX `is_uploaded` (`is_uploaded`) USING BTREE ,
INDEX `keyword_id` (`keyword_id`) USING BTREE ,
INDEX `ad_group_id_keyword_id` (`ad_group_id`, `keyword_id`) USING BTREE ,
INDEX `ad_group_id` (`ad_group_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Ad_Group_Keyword_Bid(
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id bigint(20) not null,
bid_type enum('cpc') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
bid_amount DECIMAL(8,3) NOT NULL,
`uploaded_ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
FOREIGN KEY (ad_group_keyword_id) REFERENCES Ad_Group_Keyword(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Text_Ad(
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`headline`  varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`description1`  varchar(35) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`description2`  varchar(35) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`displayUrl`  varchar(35) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`actionUrl`  varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`),
UNIQUE INDEX `unique_key` (`headline`, `description1`, `description2`, `displayUrl`, `actionUrl`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Ad_Group_Ad(
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_id bigint(20) not null,
ad_id bigint(20) not null,
provider_supplied_id bigint(20) NULL DEFAULT NULL ,
uploaded_ts timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`local_status` enum('enabled', 'paused', 'disabled') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`provider_status` enum('enabled', 'paused', 'disabled') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`approval_status`  enum('approved','disapproved','family_safe','non_family_safe','porn','unchecked','unknown') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`is_uploaded`  tinyint(1) NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`ad_group_id`) REFERENCES `Ad_Group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`ad_id`) REFERENCES `Text_Ad` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
UNIQUE INDEX `unique_key` (`ad_group_id`, `provider_supplied_id`) USING BTREE ,
INDEX `is_uploaded` (`is_uploaded`) USING BTREE ,
INDEX `ad_id` (`ad_id`) USING BTREE ,
INDEX `ad_group_id_ad_id` (`ad_group_id`, `ad_id`) USING BTREE ,
INDEX `uploaded_ts` (`uploaded_ts`) USING BTREE ,
INDEX `ad_group_id` (`ad_group_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

-- Report Function
CREATE TABLE Ad_Group_Ad_Performance(
id bigint(20) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_ad_id bigint(20) NOT NULL,
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
id bigint(20) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id bigint(20) NOT NULL,
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

CREATE TABLE Ad_Group_Keyword_Hourly_Revenue(
id bigint(20) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id bigint(20) NOT NULL,
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
id bigint(20) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id bigint(20) NOT NULL,
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
id bigint(20) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id bigint(20) NOT NULL,
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
id bigint(20) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id bigint(20) NOT NULL,
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
id bigint(20) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id bigint(20) NOT NULL,
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
id bigint(20) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
ad_group_keyword_id bigint(20) NOT NULL,
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

-- Emergency Function
CREATE TABLE `System_Emergency_Status` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`emergency_status`  tinyint(1) NOT NULL ,
PRIMARY KEY (`id`),
INDEX `emergency_status` (`emergency_status`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `Servlet_Activity_Record` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`servlet_name`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`operator`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`task`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`is_succeed`  tinyint(1) NOT NULL ,
`comment`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `servlet_name` (`servlet_name`) USING BTREE ,
INDEX `operator` (`operator`) USING BTREE ,
INDEX `task` (`task`) USING BTREE ,
INDEX `is_succeed` (`is_succeed`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Emergency_Campaign_Record (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`system_emergency_status_id`  bigint(20) NOT NULL ,
`provider_supplied_id`  bigint(20) NOT NULL ,
`provider_status_before_paused`  enum('active','paused','deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`provider_status`  enum('active','paused','deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`uploaded_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`is_uploaded`  tinyint(1) NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`system_emergency_status_id`) REFERENCES `System_Emergency_Status` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
UNIQUE INDEX `id_sequence` (`system_emergency_status_id`, `provider_supplied_id`) USING BTREE ,
INDEX `uploaded_ts` (`uploaded_ts`) USING BTREE ,
INDEX `is_uploaded` (`is_uploaded`) USING BTREE ,
INDEX `provider_supplied_id` (`provider_supplied_id`) USING BTREE ,
INDEX `system_emergency_status_id` (`system_emergency_status_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;