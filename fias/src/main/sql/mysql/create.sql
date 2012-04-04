CREATE DATABASE IF NOT EXISTS fias
    COLLATE 'utf8_unicode_ci';

CREATE TABLE `StrStat` (
  `strStatId` INT(10) NOT NULL COMMENT "Признак строения",
  `name` VARCHAR(20) COMMENT "Наименование",
  `shortName` VARCHAR(20) COMMENT "Краткое наименование",
  PRIMARY KEY (`strStatId`)
) COMMENT 'Признак строения';


