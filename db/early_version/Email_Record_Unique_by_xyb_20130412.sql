drop table if EXISTS Email_Record_Unique;

CREATE TABLE Email_Record_Unique(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
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