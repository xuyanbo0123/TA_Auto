drop table if EXISTS Redirect_Action;
drop table if EXISTS Redirect;

CREATE TABLE Redirect(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
`action`  varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
token varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
destination_url varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`id`),
key(created_ts),
key(updated_ts)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE Redirect_Action(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`redirect_id` int(11) NOT NULL,
`action_ts`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
PRIMARY KEY (`id`),
FOREIGN KEY (redirect_id) REFERENCES Redirect(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;