drop table if EXISTS Email_Program_Step_Rule_Map;
drop table if EXISTS Email_Rule;


CREATE TABLE Email_Rule(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`name`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`value` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Email_Program_Step_Rule_Map(
id int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_program_step_id int(11) NOT NULL,
email_rule_id int(11) NOT NULL,
status enum ('active', 'paused', 'closed') NOT NULL,
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
FOREIGN KEY (email_program_step_id) REFERENCES Email_Program_Step(id),
FOREIGN KEY (email_rule_id) REFERENCES Email_Rule(id),
unique key(email_program_step_id, email_rule_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;