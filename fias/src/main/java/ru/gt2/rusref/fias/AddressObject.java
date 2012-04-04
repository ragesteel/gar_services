package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.UUID;

@Description("Классификатор адресообразующих элементов")
@Entity
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
    @Column(nullable = false)
    @NotNull
    @XmlAttribute(name = "AOGUID", required = true)
    private UUID aoGuid;

    @Description("Формализованное наименование")
    @Column(nullable = false, length = 120)
    @NotNull
    @Size(min = 1, max = 120)
    @XmlAttribute(name = "FORMALNAME", required = true)
    private String formalName;

    @Description("Код региона")
    @Column(nullable = false, length = 2)
    @NotNull
    @Size(min = 2, max = 2)
    @XmlAttribute(name = "REGIONCODE", required = true)
    private String regionCode;

    @Description("Код автономии")
    @Column(nullable = false, length = 1)
    @NotNull
    @Size(min = 1, max = 1)
    @XmlAttribute(name = "AUTOCODE", required = true)
    private String autoCode;

    @Description("Код района")
    @Column(nullable = false, length = 3)
    @NotNull
    @Size(min = 3, max = 3)
    @XmlAttribute(name = "AREACODE", required = true)
    private String areaCode;

    @Description("Код города")
    @Column(nullable = false, length = 3)
    @NotNull
    @Size(min = 3, max = 3)
    @XmlAttribute(name = "CITYCODE", required = true)
    private String cityCode;

    @Description("Код внутригородского района")
    @Column(nullable = false, length = 3)
    @NotNull
    @Size(min = 3, max = 3)
    @XmlAttribute(name = "CTARCODE", required = true)
    private String ctarCode;

    @Description("Код насленного пункта")
    @Column(nullable = false, length = 3)
    @NotNull
    @Size(min = 3, max = 3)
    @XmlAttribute(name = "PLACECODE", required = true)
    private String placeCode;

    @Description("Код улицы")
    @Column(length = 4)
    @Size(min = 4, max = 4)
    @XmlAttribute(name = "STREETCODE")
    private String streetCode;

    @Description("Код дополнительного адресообразующего элемента")
    @Column(nullable = false, length = 4)
    @NotNull
    @Size(min = 4, max = 4)
    @XmlAttribute(name = "EXTRCODE", required = true)
    private String extrCode;

    @Description("Код подчиненного дополнительного адресообразующего элемента")
    @Column(nullable = false, length = 3)
    @NotNull
    @Size(min = 3, max = 3)
    @XmlAttribute(name = "SEXTCODE", required = true)
    private String sextCode;

    @Description("Официальное наименование")
    @Column(length = 120)
    @Size(min = 1, max = 120)
    @XmlAttribute(name = "OFFNAME")
    private String offName;

    @Description("Краткое наименование типа объекта")
    @Column(nullable = false, length = 10)
    @NotNull
    @Size(min = 1, max = 10)
    @XmlAttribute(name = "SHORTNAME", required = true)
    private String shortName;

    @Description("Уровень адресного объекта")
    // FIXME Тут должна быть ссылка на AddressObjectType.level, но он не является ключём.
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "AOLEVEL", required = true)
    private Integer aoLevel;

    @Description("Идентификатор объекта родительского объекта")
    @FiasRef(AddressObject.class)
    @Column
    @XmlAttribute(name = "PARENTGUID")
    private UUID parentGuid;

    @Description("Уникальный идентификатор записи. Ключевое поле")
    @Column(nullable = false)
    @NotNull
    @XmlAttribute(name = "AOID", required = true)
    private UUID aoId;

    @Description("Идентификатор записи связывания с предыдушей исторической записью")
    @FiasRef(AddressObject.class)
    @Column
    @XmlAttribute(name = "PREVID")
    private UUID prevId;

    @Description("Идентификатор записи  связывания с последующей исторической записью")
    @FiasRef(AddressObject.class)
    @Column
    @XmlAttribute(name = "NEXTID")
    private UUID nextId;

    @Description("Код адресного объекта одной строкой с признаком актуальности из КЛАДР 4.0")
    @Column(length = 17)
    @Size(min = 17, max = 17)
    @XmlAttribute(name = "CODE")
    private String code;

    @Description("Код адресного объекта из КЛАДР 4.0 одной строкой без признака актуальности (последних двух цифр)")
    @Column(length = 15)
    @Size(min = 15, max = 15)
    @XmlAttribute(name = "PLAINCODE")
    private String plainCode;

    @Description("Статус актуальности адресного объекта ФИАС")
    @FiasRef(ActualStatus.class)
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "ACTSTATUS", required = true)
    private Integer actStatus;

    @Description("Статус центра")
    @FiasRef(CenterStatus.class)
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "CENTSTATUS", required = true)
    private Integer centStatus;

    @Description("Статус действия над записью – причина появления записи")
    @FiasRef(OperationStatus.class)
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "OPERSTATUS", required = true)
    private Integer operStatus;

    @Description("Статус актуальности КЛАДР 4 (последние две цифры в коде)")
    @FiasRef(CurrentStatus.class)
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "CURRSTATUS", required = true)
    private Integer currStatus;
}
