-- add "avg_position", "quality_score" field into "Ad_Group_Keyword_Daily_Spending" by xyb
-- add "avg_position", "quality_score" field into "Ad_Group_Keyword_Hourly_Spending" by xyb
-- add "avg_position", "quality_score" field into "Ad_Group_Keyword_Daily_Spending_Rollup" by xyb

drop table if EXISTS Ad_Group_Keyword_Daily_Spending_Rollup;
drop table if EXISTS Ad_Group_Keyword_Daily_Spending;
drop table if EXISTS Ad_Group_Keyword_Hourly_Spending;
drop table if EXISTS Ad_Group_Keyword_Daily_Revenue_Rollup;
drop table if EXISTS Ad_Group_Keyword_Daily_Revenue;
drop table if EXISTS Ad_Group_Keyword_Hourly_Revenue;

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