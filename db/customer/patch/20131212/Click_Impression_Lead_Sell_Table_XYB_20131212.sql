ALTER TABLE `Click_Impression`
DROP COLUMN `comment`,
DROP COLUMN `post_url`,
DROP COLUMN `post_entity`;

ALTER TABLE `Lead_Sell`
DROP COLUMN `comment`,
DROP COLUMN `post_url`,
DROP COLUMN `post_entity`;

DROP TABLE IF EXISTS Click_Impression_Appendix;
DROP TABLE IF EXISTS Lead_Sell_Appendix;

CREATE TABLE `Click_Impression_Appendix` (
  `id`  bigint(20) NOT NULL AUTO_INCREMENT ,
  `comment`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
  `post_url`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
  `post_entity`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
  PRIMARY KEY (`id`)
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=1
  ROW_FORMAT=COMPACT
;

CREATE TABLE `Lead_Sell_Appendix` (
  `id`  bigint(20) NOT NULL AUTO_INCREMENT ,
  `comment`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
  `post_url`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
  `post_entity`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
  PRIMARY KEY (`id`)
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=1
  ROW_FORMAT=COMPACT
;

INSERT INTO Lead_Sell_Appendix (id, comment, post_url, post_entity)
  SELECT Lead_Sell.id, Lead_Sell.comment, Lead_Sell.post_url, Lead_Sell.post_entity FROM Lead_Sell;

INSERT INTO Click_Impression_Appendix (id, comment, post_url, post_entity)
  SELECT Click_Impression.id, Click_Impression.comment, Click_Impression.post_url, Click_Impression.post_entity FROM Click_Impression;