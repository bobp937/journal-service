CREATE TABLE journal (
  id int NOT NULL AUTO_INCREMENT,
  tripid int NOT NULL,
  journalDate timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  contents varchar(10000) DEFAULT NULL,
  PRIMARY KEY (id)
);
