package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Description("Уникальные идентификаторы адресных объектов для ссылок")
@FiasRef(value = AddressObject.class, fieldName = "aoGuid")
public class AddressObjectGuid {
    @Description("Уникальный идентификатор адресного объекта (улицы, города, населенного пункта и т.п.)")
    @Id
    @Column(nullable = false)
    @NotNull
    private UUID aoGuid;

}
