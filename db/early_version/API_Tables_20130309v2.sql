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

CREATE TABLE Traffic_Budget(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
traffic_provider_id int(11) not null,
provider_supplied_id int(11) DEFAULT null,
status enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
unique key(name, traffic_provider_id),
FOREIGN KEY (traffic_provider_id) REFERENCES Traffic_Provider(id)
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
provider_status enum('pending_upload', 'pending', 'approved', 'disapproved', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(uploaded_ts),
key(ad_group_id, keyword_id),
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
provider_status enum('pending_upload', 'pending', 'approved', 'disapproved', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
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



