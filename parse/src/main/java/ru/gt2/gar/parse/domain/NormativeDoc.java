package ru.gt2.gar.parse.domain;

import java.time.LocalDate;

/**
 * Нормативный документ.
 * Основа — AS_NORMATIVE_DOCS_2_251_11_04_01_01.xsd

 * @param id         Идентификатор нормативного документа
 * @param name       Наименование документа; Длина: от 1 до 8000 символов
 * @param date       Дата документа
 * @param number     Номер документа; Длина: от 1 до 150 символов (в word-описании длина до 20 символов)
 * @param type       Тип документа; {@link NormativeDocType}
 * @param kind       Вид документа; {@link NormativeDocKind}
 * @param updateDate Дата обновления
 * @param orgName    Наименование органа, создавшего нормативный документ; Опционально; Длина: от 0 до 255 символов
 * @param regNum     Номер государственной регистрации; Опционально; Длина: от 0 до 100 символов
 * @param regDate    Дата государственной регистрации; Опционально
 * @param accDate    Дата вступления в силу нормативного документа; Опционально
 * @param comment    Комментарий; Опционально; Длина: от 0 до 8000 символов
 */
public record NormativeDoc(
        long id,
        String name,
        LocalDate date,
        String number,
        int type,
        int kind,
        LocalDate updateDate,
        String orgName,
        String regNum,
        LocalDate regDate,
        LocalDate accDate,
        String comment) {
}
