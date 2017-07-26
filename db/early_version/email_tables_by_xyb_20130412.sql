drop table if EXISTS Email_Send_Link_Tracking;
drop table if EXISTS Email_Send;
drop table if EXISTS Email_Record_Role_Map;
drop table if EXISTS Email_Role_Property;
drop table if EXISTS Email_Role;
drop table if EXISTS Email_Program_Step;
drop table if EXISTS Email_Program_Property;
drop table if EXISTS Email_Program;
drop table if EXISTS Email_Record_Property;
drop table if EXISTS Email_Record;
drop table if EXISTS Email_From;
drop table if EXISTS Email_Template_Link;
drop table if EXISTS Email_Template;

CREATE TABLE Email_Template(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
subject varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
content text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
status enum('active', 'paused', 'closed') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
unique key(name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Email_Template_Link(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_template_id int(11) NOT null,
link_name  varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
link_text varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
link_url varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
unique key(email_template_id, link_name),
FOREIGN KEY (email_template_id) REFERENCES Email_Template(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Email_From(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
from_address  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
from_text varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
unique key(name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Email_Record(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,
`email_address`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`email_domain`  varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`status`  enum('active','closed') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`arrival_id`  int(11) NULL DEFAULT NULL ,
`user_id`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`arrival_id`) REFERENCES `Arrival` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `created_ts` (`created_ts`) USING BTREE ,
INDEX `updated_ts` (`updated_ts`) USING BTREE ,
INDEX `email_domain` (`email_domain`) USING BTREE ,
INDEX `arrival_id` (`arrival_id`) USING BTREE ,
INDEX `email_address` (`email_address`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=44
ROW_FORMAT=COMPACT
;

CREATE TABLE Email_Record_Property(
id int(11) NOT NULL AUTO_INCREMENT,
email_record_id int(11) NOT NULL,
name varchar(127) NOT NULL,
value varchar(1023) DEFAULT NULL,
PRIMARY KEY (id),
FOREIGN KEY (email_record_id) REFERENCES Email_Record(id),
unique key(email_record_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Email_Program(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
name  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
unique key(name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Email_Program_Property(
id int(11) NOT NULL AUTO_INCREMENT,
email_program_id int(11) NOT NULL,
name varchar(127) NOT NULL,
value varchar(1023) DEFAULT NULL,
PRIMARY KEY (id),
FOREIGN KEY (email_program_id) REFERENCES Email_Program(id),
unique key(email_program_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Email_Program_Step(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_program_id int(11) NOT NULL,
step_name  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
email_template_id int(11) NOT null,
email_from_id int(11) NOT null,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
unique key(email_program_id, step_name),
FOREIGN KEY (email_program_id) REFERENCES Email_Program(id),
FOREIGN KEY (email_template_id) REFERENCES Email_Template(id),
FOREIGN KEY (email_from_id) REFERENCES Email_From(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE Email_Role(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
name  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
email_program_id int(11) NOT NULL,
comments varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
FOREIGN KEY (email_program_id) REFERENCES Email_Program(id),
unique key(name)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE Email_Role_Property(
id int(11) NOT NULL AUTO_INCREMENT,
email_role_id int(11) NOT NULL,
name varchar(127) NOT NULL,
value varchar(1023) DEFAULT NULL,
PRIMARY KEY (id),
FOREIGN KEY (email_role_id) REFERENCES Email_Role(id),
unique key(email_role_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Email_Record_Role_Map(
id int(11) NOT NULL AUTO_INCREMENT,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_record_id int(11) NOT NULL,
email_role_id int(11) NOT NULL,
status enum ('active', 'paused', 'closed') NOT NULL,
PRIMARY KEY (id),
key(created_ts),
key(updated_ts),
FOREIGN KEY (email_record_id) REFERENCES Email_Record(id),
FOREIGN KEY (email_role_id) REFERENCES Email_Role(id),
unique key(email_record_id, email_role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Email_Send(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_record_id int(11) NOT NULL,
email_role_id int(11) NOT NULL,
email_program_step_id int(11) NOT NULL,
email_template_id int(11) NOT null,
email_from_id int(11) NOT null,
queue_ts  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
sent_ts  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
opened_ts  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
FOREIGN KEY (email_record_id) REFERENCES Email_Record(id),
FOREIGN KEY (email_role_id) REFERENCES Email_Role(id),
FOREIGN KEY (email_program_step_id) REFERENCES Email_Program_Step(id),
FOREIGN KEY (email_template_id) REFERENCES Email_Template(id),
FOREIGN KEY (email_from_id) REFERENCES Email_From(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE Email_Send_Link_Tracking(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
email_sent_id int(11) NOT NULL,
Email_template_link_id int(11) NOT NULL,
clicked_ts  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts),
key(clicked_ts),
FOREIGN KEY (email_sent_id) REFERENCES Email_Send(id),
FOREIGN KEY (Email_template_link_id) REFERENCES Email_Template_Link(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;