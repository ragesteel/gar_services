package ru.gt2.rusref.fias;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlType;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Все файлы ФИАС.
 */
public enum Fias {
    STRSTAT(StructureStatuses.class, StructureStatus.class, "14"),
    ACTSTAT(ActualStatuses.class, ActualStatus.class, "08"),
    INTVSTAT(IntervalStatuses.class, IntervalStatus.class, "11"),
    ESTSTAT(EstateStatuses.class, EstateStatus.class, "13"),
    CENTERST(CenterStatuses.class, CenterStatus.class, "10"),
    OPERSTAT(OperationStatuses.class, OperationStatus.class, "09"),
    HSTSTAT(HouseStateStatuses.class, HouseStateStatus.class, "12"),
    CURENTST(CurrentStatuses.class, CurrentStatus.class, "07"),
    SOCRBASE(AddressObjectTypes.class, AddressObjectType.class, "06"),
    LANDMARK(Landmarks.class, Landmark.class, "04"),
    HOUSEINT(HouseIntervals.class, HouseInterval.class, "03"),
    NORMDOC(NormativeDocumentes.class, NormativeDocument.class, "05"),
    ADDROBJ(AddressObjects.class, AddressObject.class, "01"),
    HOUSE(Houses.class, House.class, "02");

    /** Класс обёртки. */
    public final Class<?> wrapper;
    /** Внутренний класс. */
    public final Class<?> item;
    /** Поля внутреннего класса. */
    public final ImmutableList<Field> itemFields;
    /** Поля, которые являются ссылками на другие. */
    public final ImmutableList<Field> referenceFields;
    /** Поле идентификатора внутреннего класса. */
    public final Field idField;
    /** Название файла со схемой. */
    public final String schemePrefix;

    private static final Predicate<Field> FIAS_REF = new Predicate<Field>() {
        @Override
        public boolean apply(@Nullable Field field) {
            return (null != field.getAnnotation(FiasRef.class));
        }
    };

    private Fias(Class<?> wrapper, Class<?> item, String schemePart) {
        this.wrapper = wrapper;
        this.item = item;
        this.schemePrefix = "AS_" + name() + "_2_250_" + schemePart + "_04_01_";
        this.itemFields = getAllFields(item);
        this.idField = getId(itemFields);
        this.referenceFields = getReferences(itemFields);
    }

    private static ImmutableList<Field> getAllFields(Class<?> item) {
        String[] propOrder = getPropOrder(item);

        Map<String, Field> fieldByName = Maps.newHashMap();
        while (!Object.class.equals(item)) {
            for (Field field : item.getDeclaredFields()) {
                fieldByName.put(field.getName(), field);
            }
            item = item.getSuperclass();
        }
        
        if (0 == propOrder.length) {
            return ImmutableList.copyOf(fieldByName.values());
        }
        // Упорядочить поля по XmlType.propOrder, если есть.

        List<Field> fields = Lists.newArrayListWithCapacity(propOrder.length);
        for (String name : propOrder) {
            Field field = fieldByName.get(name);
            if (null == field) {
                throw new IllegalArgumentException("Field: " + name + " not found");
            }
            fields.add(field);
        }
        return ImmutableList.copyOf(fields);
    }

    private static String[] getPropOrder(Class<?> item) {
        XmlType xmlType = item.getAnnotation(XmlType.class);
        String[] propOrder = null;
        if (null != xmlType) {
            propOrder = xmlType.propOrder();
        }
        return Objects.firstNonNull(propOrder, new String[0]);
    }
    
    private static Field getId(Collection<Field> fields) {
        Field result = null;
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            if (null == id) {
                continue;
            }
            if (null != result) {
                throw new IllegalArgumentException("Duplication of id " + field);
            }
            result = field;
        }
        if (null == result) {
            throw new IllegalArgumentException("Id field is not defined!");
        }
        return result;
    }
    
    private static ImmutableList<Field> getReferences(Collection<Field> fields) {
        return ImmutableList.copyOf(Collections2.filter(fields, FIAS_REF));
    }
}
