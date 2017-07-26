DROP TABLE IF EXISTS Arrival;

CREATE TABLE Arrival(
  id int(11) NOT NULL AUTO_INCREMENT,
  created_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_ts timestamp NOT NULL DEFAULT 0,
  ip_address varchar(127) NOT NULL,
  user_agent varchar(1023) NOT NULL,
  referer varchar(1023) DEFAULT NULL,
  device varchar(127) DEFAULT NULL,
  sid int(8) NOT NULL,
  subid varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  key(created_ts),
  key(updated_ts),
  key(sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS Arrival_Property;

CREATE TABLE Arrival_Property(
  id int(11) NOT NULL AUTO_INCREMENT,
  arrival_id int(11) NOT NULL,
  name varchar(127) NOT NULL,
  value varchar(1023) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (arrival_id) REFERENCES Arrival(id),
  unique key(arrival_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
