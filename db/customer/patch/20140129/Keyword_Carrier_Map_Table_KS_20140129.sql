ALTER TABLE `Keyword_Carrier_Map`
ADD COLUMN `enabled`  tinyint NOT NULL DEFAULT 1 AFTER `carrier_list_id`;