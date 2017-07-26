ALTER TABLE `Ad_Group_Keyword_Daily_Spending`
    DROP COLUMN `arrival_count`,
    ADD COLUMN `visits` int(11) NULL DEFAULT NULL AFTER `quality_score`,
    ADD COLUMN `bounces` int(11) NULL DEFAULT NULL AFTER `visits`,
    ADD COLUMN `goal1` int(11) NULL DEFAULT NULL AFTER `bounces`,
    ADD COLUMN `goal2` int(11) NULL DEFAULT NULL AFTER `goal1`;