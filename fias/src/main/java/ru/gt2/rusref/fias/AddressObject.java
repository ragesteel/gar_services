package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.UUID;

@Description("Классификатор адресообразующих элементов")
@XmlType(propOrder = {"aoGuid", "formalName", "regionCode", "autoCode", "areaCode", "cityCode", "ctarCode", "placeCode",
    "streetCode", "extrCode", "sextCode", "offName", "postalCode", "ifnsFl", "terrIfnsFl", "ifnsUl", "terrIfnsUl",
    "okato", "oktmo", "updateDate", "shortName", "aoLevel", "parentGuid", "aoId", "prevId", "nextId", "code",
    "plainCode", "actStatus", "centStatus", "operStatus", "currStatus", "startDate", "endDate", "normDoc"})
public class AddressObject extends AbstractAddressObject {
    /**
     * Вместо Object сделали AddressObject, т.к. в Java один Object уже есть, чтобы не было путаницы.
     * Да и положено так, чтобы внутри AddressObjects были именно AddressObject'ы, а не просто Object'ы.
     */

    @Description("Глобальный уникальный идентификатор адресного объекта")
    @Id
    @NotNull
    @XmlAttribute(name = "AOGUID", required = true)
    private UUID aoGuid;

    @Description("Формализованное наименование")
    @NotNull
    @Size(min = 1, max = 120)
    @XmlAttribute(name = "FORMALNAME", required = true)
    private String formalName;

    @Description("Код региона")
    @NotNull
    @Size(min = 2, max = 2)
    @XmlAttribute(name = "REGIONCODE", required = true)
    private String regionCode;

    @Description("Код автономии")
    @NotNull
    @Size(min = 1, max = 1)
    @XmlAttribute(name = "AUTOCODE", required = true)
    private String autoCode;

    @Description("Код района")
    @NotNull
    @Size(min = 3, max = 3)
    @XmlAttribute(name = "AREACODE", required = true)
    private String areaCode;

    @Description("Код города")
    @NotNull
    @Size(min = 3, max = 3)
    @XmlAttribute(name = "CITYCODE", required = true)
    private String cityCode;

    @Description("Код внутригородского района")
    @NotNull
    @Size(min = 3, max = 3)
    @XmlAttribute(name = "CTARCODE", required = true)
    private String ctarCode;

    @Description("Код насленного пункта")
    @NotNull
    @Size(min = 3, max = 3)
    @XmlAttribute(name = "PLACECODE", required = true)
    private String placeCode;

    @Description("Код улицы")
    @Size(min = 4, max = 4)
    @XmlAttribute(name = "STREETCODE")
    private String streetCode;

    @Description("Код дополнительного адресообразующего элемента")
    @NotNull
    @Size(min = 4, max = 4)
    @XmlAttribute(name = "EXTRCODE", required = true)
    private String extrCode;

    @Description("Код подчиненного дополнительного адресообразующего элемента")
    @NotNull
    @Size(min = 3, max = 3)
    @XmlAttribute(name = "SEXTCODE", required = true)
    private String sextCode;

    @Description("Официальное наименование")
    @Size(min = 1, max = 120)
    @XmlAttribute(name = "OFFNAME")
    private String offName;

    @Description("Краткое наименование типа объекта")
    @NotNull
    @Size(min = 1, max = 10)
    @XmlAttribute(name = "SHORTNAME", required = true)
    private String shortName;

    @Description("Уровень адресного объекта")
    // FIXME Тут должна быть ссылка на AddressObjectType.level, но он не является ключём.
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "AOLEVEL", required = true)
    private Integer aoLevel;

    @Description("Идентификатор объекта родительского объекта")
    @FiasRef(AddressObject.class)
    @XmlAttribute(name = "PARENTGUID")
    private UUID parentGuid;

    @Description("Уникальный идентификатор записи. Ключевое поле")
    @NotNull
    @XmlAttribute(name = "AOID", required = true)
    private UUID aoId;

    @Description("Идентификатор записи связывания с предыдушей исторической записью")
    @FiasRef(AddressObject.class)
    @XmlAttribute(name = "PREVID")
    private UUID prevId;

    @Description("Идентификатор записи  связывания с последующей исторической записью")
    @FiasRef(AddressObject.class)
    @XmlAttribute(name = "NEXTID")
    private UUID nextId;

    @Description("Код адресного объекта одной строкой с признаком актуальности из КЛАДР 4.0")
    @Size(min = 17, max = 17)
    @XmlAttribute(name = "CODE")
    private String code;

    @Description("Код адресного объекта из КЛАДР 4.0 одной строкой без признака актуальности (последних двух цифр)")
    @Size(min = 15, max = 15)
    @XmlAttribute(name = "PLAINCODE")
    private String plainCode;

    @Description("Статус актуальности адресного объекта ФИАС")
    @FiasRef(ActualStatus.class)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "ACTSTATUS", required = true)
    private Integer actStatus;

    @Description("Статус центра")
    @FiasRef(CenterStatus.class)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "CENTSTATUS", required = true)
    private Integer centStatus;

    @Description("Статус действия над записью – причина появления записи")
    @FiasRef(OperationStatus.class)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "OPERSTATUS", required = true)
    private Integer operStatus;

    @Description("Статус актуальности КЛАДР 4 (последние две цифры в коде)")
    @FiasRef(CurrentStatus.class)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "CURRSTATUS", required = true)
    private Integer currStatus;
}
