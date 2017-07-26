alter table Traffic_Campaign change `status` `local_status` enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;
alter table Traffic_Campaign add column `provider_status` enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL after local_status;

alter table Ad_Group change `status` `local_status` enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;
alter table Ad_Group add column `provider_status` enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL after local_status;

alter table Ad_Group_Keyword add column `local_status` enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;
alter table Ad_Group_Keyword change `status` `provider_status` enum('pending_upload', 'pending', 'approved', 'disapproved', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL after local_status;

alter table Ad_Group_Ad add column `local_status` enum('active', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;
alter table Ad_Group_Ad change `status` `provider_status` enum('pending_upload', 'pending', 'approved', 'disapproved', 'paused', 'deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL after local_status;


