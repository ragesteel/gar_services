package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.UUID;

@Description("Уникальные идентификаторы адресных объектов для ссылок")
@FiasRef(AddressObject.class)
@Entity
@XmlType(propOrder = {"aoGuid"})
public class AddressObjectGuid implements Serializable {
    @Description("Уникальный идентификатор адресного объекта (улицы, города, населенного пункта и т.п.)")
    @Id
    @Column(nullable = false)
    @NotNull
    private UUID aoGuid;

}
