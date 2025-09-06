package ru.gt2.gar.parse.domain;

/**
 * Все разновидности объектов для файлов, по крайней мере для которых имеется описание.
 */
public enum GarType {
    ADDR_OBJ,
    ADDR_OBJ_DIVISION,
    ADDR_OBJ_TYPES,
    ADM_HIERARCHY,
    APARTMENT_TYPES,
    APARTMENTS,
    CARPLACES,
    CHANGE_HISTORY,
    HOUSE_TYPES,
    HOUSES,
    MUN_HIERARCHY,
    NORMATIVE_DOCS,
    NORMATIVE_DOCS_KINDS,
    NORMATIVE_DOCS_TYPES,
    OBJECT_LEVELS,
    OPERATION_TYPES,
    // PARAM,
    PARAM_TYPES,
    REESTR_OBJECTS,
    ROOM_TYPES,
    ROOMS,
    STEADS,
    // Описаны в AS_PARAM_*
    ADDR_OBJ_PARAMS,
    HOUSES_PARAMS,
    APARTMENTS_PARAMS,
    ROOMS_PARAMS,
    STEADS_PARAMS,
    CARPLACES_PARAMS,

    // APARTMENT_TYPES, HOUSE_TYPES, OPERATION_TYPES тоже очень похожи, разница только в ограничиениях длины полей
    // PARAM_TYPES — тот-же набор, но вместо shortName поле называется code.

    // Недокументированные, но имеющиеся в gar_xml 2025-08-29
    ADDHOUSE_TYPES
}
