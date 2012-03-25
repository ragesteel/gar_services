package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Статус актуальности.
 */
@ToString
public class ActualStatus {
    /** Идентификатор статуса (ключ) */
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "ACTSTATID", required = true)
    private Integer actStatId;

    /** Наименование */
    @NotNull
    @Size(min = 1, max = 100)
    @XmlAttribute(name = "NAME", required = true)
    private String name;
}
