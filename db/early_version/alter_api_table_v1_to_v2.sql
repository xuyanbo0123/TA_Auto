alter table Traffic_Campaign
add column `uploaded_ts`  timestamp NOT NULL DEFAULT 0 after `status`,
add INDEX (uploaded_ts);

alter table Ad_Group
modify provider_supplied_id bigint(20),
add column `status` enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL after `name`,
add column `uploaded_ts`  timestamp NOT NULL DEFAULT 0 after provider_supplied_id,
add INDEX (uploaded_ts);

alter table Ad_Group_Bid
modify provider_supplied_id bigint(20);


alter table Ad_Group_Keyword
modify provider_supplied_id bigint(20);

alter table Ad_Group_Keyword_Bid
modify provider_supplied_id bigint(20);

alter table Ad_Group_Ad
modify provider_supplied_id bigint(20),
modify `status` enum('pending_upload', 'pending', 'approved', 'disapproved','paused', 'closed');