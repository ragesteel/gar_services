CREATE DATABASE IF NOT EXISTS fias
    COLLATE 'utf8_unicode_ci';

CREATE TABLE `StrStat` (
  `strStatId` INT(10) NOT NULL COMMENT "Признак строения",
  `name` VARCHAR(20) COMMENT "Наименование",
  `shortName` VARCHAR(20) COMMENT "Краткое наименование",
  PRIMARY KEY (`strStatId`)
) COMMENT 'Признак строения';


-- mvn exec:java -Dexec.mainClass="org.apache.openjpa.jdbc.meta.MappingTool" -Dexec.args="-readSchame=false"
-- mvn exec:java -Dexec.mainClass="org.apache.openjpa.jdbc.meta.MappingTool" -Dexec.args="-schemaAction build -sql create.sql -readSchema false"
-- http://openjpa.apache.org/builds/2.2.0/apache-openjpa/docs/ref_guide_mapping.html#ref_guide_mapping_mappingtool
