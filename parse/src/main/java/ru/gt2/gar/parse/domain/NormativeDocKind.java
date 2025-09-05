package ru.gt2.gar.parse.domain;

/**
 * Вид нормативного документа.
 * Основа — AS_NORMATIVE_DOCS_KINDS_2_251_09_04_01_01.xsd
 *
 * @param id   Идентификатор вида нормативного документа
 * @param name Наименование; Длина: от 1 до 500 символов
 */
public record NormativeDocKind(
        int id,
        String name) {
}


