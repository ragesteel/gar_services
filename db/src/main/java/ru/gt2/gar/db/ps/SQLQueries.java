// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (SQLQueriesGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.ps;

import com.google.common.collect.ImmutableMap;
import ru.gt2.gar.domain.GarType;

import javax.annotation.processing.Generated;

@Generated(
        value = "ru.gt2.gar.db.sql.SQLQueriesGenerator",
        date = "2026-03-13T18:47:19.4894534+03:00"
)
public class SQLQueries {
    private static final ImmutableMap<GarType, GeneratedSQL> MAP = ImmutableMap.<GarType, GeneratedSQL>builder()
                .put(GarType.ADDR_OBJ, new GeneratedSQL("BIGINT", """
                        SELECT id, object_id, object_guid, change_id, name, type_name, level, oper_type_id, prev_id, next_id, update_date, start_date, end_date, is_actual, is_active FROM addr_obj WHERE id = ANY(?)""", """
                        INSERT INTO addr_obj (id, object_id, object_guid, change_id, name, type_name, level, oper_type_id, prev_id, next_id, update_date, start_date, end_date, is_actual, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.ADDR_OBJ_DIVISION, new GeneratedSQL("BIGINT", """
                        SELECT id, parent_id, child_id, change_id FROM addr_obj_division WHERE id = ANY(?)""", """
                        INSERT INTO addr_obj_division (id, parent_id, child_id, change_id) VALUES (?, ?, ?, ?)"""))
                .put(GarType.ADDR_OBJ_TYPES, new GeneratedSQL("INT", """
                        SELECT id, level, short_name, name, description, update_date, start_date, end_date, is_active FROM addr_obj_types WHERE id = ANY(?)""", """
                        INSERT INTO addr_obj_types (id, level, short_name, name, description, update_date, start_date, end_date, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.ADM_HIERARCHY, new GeneratedSQL("BIGINT", """
                        SELECT id, object_id, parent_obj_id, change_id, region_code, area_code, city_code, place_code, plan_code, street_code, prev_id, next_id, update_date, start_date, end_date, is_active, path FROM adm_hierarchy WHERE id = ANY(?)""", """
                        INSERT INTO adm_hierarchy (id, object_id, parent_obj_id, change_id, region_code, area_code, city_code, place_code, plan_code, street_code, prev_id, next_id, update_date, start_date, end_date, is_active, path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.APARTMENT_TYPES, new GeneratedSQL("INT", """
                        SELECT id, name, short_name, description, update_date, start_date, end_date, is_active FROM apartment_types WHERE id = ANY(?)""", """
                        INSERT INTO apartment_types (id, name, short_name, description, update_date, start_date, end_date, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.APARTMENTS, new GeneratedSQL("BIGINT", """
                        SELECT id, object_id, object_guid, change_id, number, apart_type, oper_type_id, prev_id, next_id, update_date, start_date, end_date, is_actual, is_active FROM apartments WHERE id = ANY(?)""", """
                        INSERT INTO apartments (id, object_id, object_guid, change_id, number, apart_type, oper_type_id, prev_id, next_id, update_date, start_date, end_date, is_actual, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.CARPLACES, new GeneratedSQL("BIGINT", """
                        SELECT id, object_id, object_guid, change_id, number, oper_type_id, prev_id, next_id, update_date, start_date, end_date, is_actual, is_active FROM carplaces WHERE id = ANY(?)""", """
                        INSERT INTO carplaces (id, object_id, object_guid, change_id, number, oper_type_id, prev_id, next_id, update_date, start_date, end_date, is_actual, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.CHANGE_HISTORY, new GeneratedSQL("BIGINT", """
                        SELECT change_id, object_i_d, adr_object_id, oper_type_id, n_doc_id, change_date FROM change_history WHERE change_id = ANY(?)""", """
                        INSERT INTO change_history (change_id, object_i_d, adr_object_id, oper_type_id, n_doc_id, change_date) VALUES (?, ?, ?, ?, ?, ?)"""))
                .put(GarType.HOUSE_TYPES, new GeneratedSQL("INT", """
                        SELECT id, name, short_name, description, update_date, start_date, end_date, is_active FROM house_types WHERE id = ANY(?)""", """
                        INSERT INTO house_types (id, name, short_name, description, update_date, start_date, end_date, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.HOUSES, new GeneratedSQL("BIGINT", """
                        SELECT id, object_id, object_guid, change_id, house_num, add_num1, add_num2, house_type, add_type1, add_type2, oper_type_id, prev_id, next_id, update_date, start_date, end_date, is_actual, is_active FROM houses WHERE id = ANY(?)""", """
                        INSERT INTO houses (id, object_id, object_guid, change_id, house_num, add_num1, add_num2, house_type, add_type1, add_type2, oper_type_id, prev_id, next_id, update_date, start_date, end_date, is_actual, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.MUN_HIERARCHY, new GeneratedSQL("BIGINT", """
                        SELECT id, object_id, parent_obj_id, change_id, oktmo, prev_id, next_id, update_date, start_date, end_date, is_active, path FROM mun_hierarchy WHERE id = ANY(?)""", """
                        INSERT INTO mun_hierarchy (id, object_id, parent_obj_id, change_id, oktmo, prev_id, next_id, update_date, start_date, end_date, is_active, path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.NORMATIVE_DOCS, new GeneratedSQL("BIGINT", """
                        SELECT id, name, date, number, type, kind, update_date, org_name, reg_num, reg_date, acc_date, comment FROM normative_docs WHERE id = ANY(?)""", """
                        INSERT INTO normative_docs (id, name, date, number, type, kind, update_date, org_name, reg_num, reg_date, acc_date, comment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.NORMATIVE_DOCS_KINDS, new GeneratedSQL("INT", """
                        SELECT id, name FROM normative_docs_kinds WHERE id = ANY(?)""", """
                        INSERT INTO normative_docs_kinds (id, name) VALUES (?, ?)"""))
                .put(GarType.NORMATIVE_DOCS_TYPES, new GeneratedSQL("INT", """
                        SELECT id, name, start_date, end_date FROM normative_docs_types WHERE id = ANY(?)""", """
                        INSERT INTO normative_docs_types (id, name, start_date, end_date) VALUES (?, ?, ?, ?)"""))
                .put(GarType.OBJECT_LEVELS, new GeneratedSQL("INT", """
                        SELECT level, name, short_name, update_date, start_date, end_date, is_active FROM object_levels WHERE level = ANY(?)""", """
                        INSERT INTO object_levels (level, name, short_name, update_date, start_date, end_date, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.OPERATION_TYPES, new GeneratedSQL("INT", """
                        SELECT id, name, short_name, description, update_date, start_date, end_date, is_active FROM operation_types WHERE id = ANY(?)""", """
                        INSERT INTO operation_types (id, name, short_name, description, update_date, start_date, end_date, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.PARAM_TYPES, new GeneratedSQL("INT", """
                        SELECT id, name, code, description, update_date, start_date, end_date, is_active FROM param_types WHERE id = ANY(?)""", """
                        INSERT INTO param_types (id, name, code, description, update_date, start_date, end_date, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.REESTR_OBJECTS, new GeneratedSQL("BIGINT", """
                        SELECT object_id, object_guid, change_id, is_active, level_id, create_date, update_date FROM reestr_objects WHERE object_id = ANY(?)""", """
                        INSERT INTO reestr_objects (object_id, object_guid, change_id, is_active, level_id, create_date, update_date) VALUES (?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.ROOM_TYPES, new GeneratedSQL("INT", """
                        SELECT id, name, short_name, description, update_date, start_date, end_date, is_active FROM room_types WHERE id = ANY(?)""", """
                        INSERT INTO room_types (id, name, short_name, description, update_date, start_date, end_date, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.ROOMS, new GeneratedSQL("BIGINT", """
                        SELECT id, object_id, object_guid, change_id, number, room_type, oper_type_id, prev_id, next_id, update_date, start_date, end_date, is_actual, is_active FROM rooms WHERE id = ANY(?)""", """
                        INSERT INTO rooms (id, object_id, object_guid, change_id, number, room_type, oper_type_id, prev_id, next_id, update_date, start_date, end_date, is_actual, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.STEADS, new GeneratedSQL("BIGINT", """
                        SELECT id, object_id, object_guid, change_id, number, oper_type_id, prev_id, next_id, update_date, start_date, end_date, is_actual, is_active FROM steads WHERE id = ANY(?)""", """
                        INSERT INTO steads (id, object_id, object_guid, change_id, number, oper_type_id, prev_id, next_id, update_date, start_date, end_date, is_actual, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.ADDR_OBJ_PARAMS, new GeneratedSQL("BIGINT", """
                        SELECT id, object_id, change_id, change_id_end, type_id, param_value, update_date, start_date, end_date FROM addr_obj_params WHERE id = ANY(?)""", """
                        INSERT INTO addr_obj_params (id, object_id, change_id, change_id_end, type_id, param_value, update_date, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.HOUSES_PARAMS, new GeneratedSQL("BIGINT", """
                        SELECT id, object_id, change_id, change_id_end, type_id, param_value, update_date, start_date, end_date FROM houses_params WHERE id = ANY(?)""", """
                        INSERT INTO houses_params (id, object_id, change_id, change_id_end, type_id, param_value, update_date, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.APARTMENTS_PARAMS, new GeneratedSQL("BIGINT", """
                        SELECT id, object_id, change_id, change_id_end, type_id, param_value, update_date, start_date, end_date FROM apartments_params WHERE id = ANY(?)""", """
                        INSERT INTO apartments_params (id, object_id, change_id, change_id_end, type_id, param_value, update_date, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.ROOMS_PARAMS, new GeneratedSQL("BIGINT", """
                        SELECT id, object_id, change_id, change_id_end, type_id, param_value, update_date, start_date, end_date FROM rooms_params WHERE id = ANY(?)""", """
                        INSERT INTO rooms_params (id, object_id, change_id, change_id_end, type_id, param_value, update_date, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.STEADS_PARAMS, new GeneratedSQL("BIGINT", """
                        SELECT id, object_id, change_id, change_id_end, type_id, param_value, update_date, start_date, end_date FROM steads_params WHERE id = ANY(?)""", """
                        INSERT INTO steads_params (id, object_id, change_id, change_id_end, type_id, param_value, update_date, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.CARPLACES_PARAMS, new GeneratedSQL("BIGINT", """
                        SELECT id, object_id, change_id, change_id_end, type_id, param_value, update_date, start_date, end_date FROM carplaces_params WHERE id = ANY(?)""", """
                        INSERT INTO carplaces_params (id, object_id, change_id, change_id_end, type_id, param_value, update_date, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"""))
                .put(GarType.ADDHOUSE_TYPES, new GeneratedSQL("INT", """
                        SELECT id, name, short_name, description, update_date, start_date, end_date, is_active FROM addhouse_types WHERE id = ANY(?)""", """
                        INSERT INTO addhouse_types (id, name, short_name, description, update_date, start_date, end_date, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"""))
        .build();;

    public static GeneratedSQL get(GarType garType) {
        return MAP.get(garType);
    }
}
