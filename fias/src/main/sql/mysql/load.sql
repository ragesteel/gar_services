-- mysql --user=root --show-warnings --local fias
-- StructureStatus.csv ActualStatus.csv IntervalStatus.csv EstateStatus.csv CenterStatus.csv OperationStatus.csv
-- HouseStateStatus.csv CurrentStatus.csv AddressObjectType.csv NormativeDocument.csv AddressObject.csv Landmark.csv
-- HouseInterval.csv House.csv

-- http://dev.mysql.com/doc/refman/5.5/en/optimizing-innodb-bulk-data-loading.html
SET autocommit = 0, foreign_key_checks = 0;

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
COMMIT;

LOAD DATA LOCAL INFILE 'Landmark.csv'
  INTO TABLE `Landmark`
  FIELDS
    TERMINATED BY '\t'
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY '\\'
  (`location`, `postalCode`, `ifnsFl`, `terrIfnsFl`, `ifnsUl`, `terrIfnsUl`, `okato`, `oktmo`, `updateDate`, @landId, @landGuid, @aoGuid, `startDate`, `endDate`, @normDoc)
  SET
    `landId` = UNHEX(@landId), `landGuid` = UNHEX(@landGuid), `aoGuid` = UNHEX(@aoGuid), `normDoc` = UNHEX(@normDoc)
;
COMMIT;

LOAD DATA LOCAL INFILE 'AddressObjectType.csv'
  INTO TABLE `AddressObjectType`
  FIELDS
    TERMINATED BY '\t'
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY '\\'
  (`level`, `scName`, `socrName`, `kodTSt`)
;
COMMIT;

LOAD DATA LOCAL INFILE 'CurrentStatus.csv'
  INTO TABLE `CurrentStatus`
  FIELDS
    TERMINATED BY '\t'
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY '\\'
  (`curentStId`, `name`)
;
COMMIT;

LOAD DATA LOCAL INFILE 'ActualStatus.csv'
  INTO TABLE `ActualStatus`
  FIELDS
    TERMINATED BY '\t'
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY '\\'
  (`actStatId`, `name`)
;
COMMIT;

LOAD DATA LOCAL INFILE 'OperationStatus.csv'
  INTO TABLE `OperationStatus`
  FIELDS
    TERMINATED BY '\t'
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY '\\'
  (`operStatId`, `name`)
;
COMMIT;

LOAD DATA LOCAL INFILE 'CenterStatus.csv'
  INTO TABLE `CenterStatus`
  FIELDS
    TERMINATED BY '\t'
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY '\\'
  (`centerStId`, `name`)
;
COMMIT;

LOAD DATA LOCAL INFILE 'AddressObject.csv'
  INTO TABLE `AddressObject`
  FIELDS
    TERMINATED BY '\t'
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY '\\'
  (@aoGuid, `formalName`, `regionCode`, `autoCode`, `areaCode`, `cityCode`, `ctarCode`, `placeCode`, `streetCode`, `extrCode`, `sextCode`, `offName`, `postalCode`, `ifnsFl`, `terrIfnsFl`, `ifnsUl`, `terrIfnsUl`, `okato`, `oktmo`, `updateDate`, `shortName`, `aoLevel`, @parentGuid, @aoId, @prevId, @nextId, `code`, `plainCode`, `actStatus`, `centStatus`, `operStatus`, `currStatus`, `startDate`, `endDate`, @normDoc)
  SET
    `aoGuid` = UNHEX(@aoGuid), `parentGuid` = UNHEX(@parentGuid), `aoId` = UNHEX(@aoId), `prevId` = UNHEX(@prevId), `nextId` = UNHEX(@nextId), `normDoc` = UNHEX(@normDoc)
;
COMMIT;

INSERT INTO `AddressObjectGuid`
  SELECT DISTINCT `aoGuid` FROM `AddressObject`;
COMMIT;

LOAD DATA LOCAL INFILE 'IntervalStatus.csv'
  INTO TABLE `IntervalStatus`
  FIELDS
    TERMINATED BY '\t'
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY '\\'
  (`intStatId`, `name`)
;
COMMIT;

LOAD DATA LOCAL INFILE 'HouseInterval.csv'
  INTO TABLE `HouseInterval`
  FIELDS
    TERMINATED BY '\t'
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY '\\'
  (`postalCode`, `ifnsFl`, `terrIfnsFl`, `ifnsUl`, `terrIfnsUl`, `okato`, `oktmo`, `updateDate`, `intStart`, `intEnd`, @houseIntId, @intGuid, @aoGuid, `startDate`, `endDate`, `intStatus`, @normDoc)
  SET
    `houseIntId` = UNHEX(@houseIntId), `intGuid` = UNHEX(@intGuid), `aoGuid` = UNHEX(@aoGuid), `normDoc` = UNHEX(@normDoc)
;
COMMIT;

LOAD DATA LOCAL INFILE 'HouseStateStatus.csv'
  INTO TABLE `HouseStateStatus`
  FIELDS
    TERMINATED BY '\t'
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY '\\'
  (`houseStId`, `name`)
;
COMMIT;

LOAD DATA LOCAL INFILE 'EstateStatus.csv'
  INTO TABLE `EstateStatus`
  FIELDS
    TERMINATED BY '\t'
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY '\\'
  (`estStatId`, `name`, `shortName`)
;
COMMIT;

LOAD DATA LOCAL INFILE 'StructureStatus.csv'
  INTO TABLE `StructureStatus`
  FIELDS
    TERMINATED BY '\t'
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY '\\'
  (`strStatId`, `name`, `shortName`)
;
COMMIT;

LOAD DATA LOCAL INFILE 'House.csv'
  INTO TABLE `House`
  FIELDS
    TERMINATED BY '\t'
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY '\\'
  (`postalCode`, `ifnsFl`, `terrIfnsFl`, `ifnsUl`, `terrIfnsUl`, `okato`, `oktmo`, `updateDate`, `houseNum`, `estStatus`, `buildNum`, `structNum`, `strStatus`, @houseId, @houseGuid, @aoGuid, `startDate`, `endDate`, `statStatus`, @normDoc, `counter`)
  SET
    `houseId` = UNHEX(@houseId), `houseGuid` = UNHEX(@houseGuid), `aoGuid` = UNHEX(@aoGuid), `normDoc` = UNHEX(@normDoc)
;
COMMIT;

SET autocommit = 1, foreign_key_checks = 1;

