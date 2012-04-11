-- mysql --user=root --show-warnings --local fias
-- StructureStatus.csv ActualStatus.csv IntervalStatus.csv EstateStatus.csv CenterStatus.csv OperationStatus.csv
-- HouseStateStatus.csv CurrentStatus.csv AddressObjectType.csv NormativeDocument.csv AddressObject.csv Landmark.csv
-- HouseInterval.csv House.csv

-- http://dev.mysql.com/doc/refman/5.5/en/optimizing-innodb-bulk-data-loading.html
SET autocommit = 0, foreign_key_checks = 0;
-- ALTER TABLE `AddressObject` DISABLE KEYS;

LOAD DATA LOCAL INFILE 'AddressObject.csv' INTO TABLE `AddressObject`
    FIELDS
        TERMINATED BY '\t'
        OPTIONALLY ENCLOSED BY '"'
        ESCAPED BY '\\'
  (@aoGuid, `formalName`, `regionCode`, `autoCode`, `areaCode`, `cityCode`, `ctarCode`, `placeCode`, `streetCode`,
    `extrCode`, `sextCode`, `offName`, `postalCode`, `ifnsFl`, `terrIfnsFl`, `ifnsUl`, `terrIfnsUl`, `okato`, `oktmo`,
    `updateDate`, `shortName`, `aoLevel`, @parentGuid, @aoId, @prevId, @nextId, `code`, `plainCode`, `actStatus`,
    `centStatus`, `operStatus`, `currStatus`, `startDate`, `endDate`, @normDoc)
  SET
    `aoGuid` = UNHEX(@aoGuid), `parentGuid` = UNHEX(@parentGuid), `aoId` = UNHEX(@aoId),
    `prevId` = UNHEX(@prevId), `nextId` = UNHEX(@nextId), `normDoc` = UNHEX(@normDoc)
;

-- ALTER TABLE `AddressObject` ENABLE KEYS;
COMMIT;
SET autocommit = 1, foreign_key_checks = 1;

-- Query OK, 1091625 rows affected, 32 warnings (1 hour 29 min 1.15 sec)
-- Records: 1305737  Deleted: 0  Skipped: 214112  Warnings: 32

-- 32x Warning (Code 1263): Column set to default value; NULL supplied to NOT NULL column 'shortName' at row 1227907

--- FIXME Добавить NULL в shortName!
--- FIXME Почему skipped?
--- FIXME Добавить экранирование переводов строки

SET autocommit = 0, foreign_key_checks = 0;
-- ALTER TABLE `AddressObject` DISABLE KEYS;

DELETE FROM `NormativeDocument`;
COMMIT;

LOAD DATA LOCAL INFILE 'NormativeDocument.csv'
    INTO TABLE `NormativeDocument`
    FIELDS
        TERMINATED BY '\t'
        OPTIONALLY ENCLOSED BY '"'
        ESCAPED BY '\\'
    (@normDoc, `docName`, `docDate`, `docNum`, `docType`, `docImgId`)
    SET
        `normDoc` = UNHEX(@normDoc)
;
-- Query OK, 4657801 rows affected (2 hours 56 min 14.48 sec)
-- Records: 4657801  Deleted: 0  Skipped: 0  Warnings: 0

-- ALTER TABLE `AddressObject` ENABLE KEYS;
COMMIT;
SET autocommit = 1, foreign_key_checks = 1;
-- Опять одного не хватает… Может там где-то символ перевода строки.