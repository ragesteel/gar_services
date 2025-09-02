package ru.gt2.rusref.ddl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import ru.gt2.rusref.fias.Fias;
import ru.gt2.rusref.fias.FiasRef;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Представление внешнего ключа для генерации создания таблицы.
 */
public class ForeignKeyList implements Iterable<ForeignKeyList.ForeignKey> {

    private final List<ForeignKey> foreignKeyList = Lists.newArrayList();

    private final Map<String, ForeignKey> foreignKeyByUniqueContraint = Maps.newHashMap();

    public static class ForeignKey {
        @Getter
        private Fias targetTable;
        private String targetUniqueConstraint;
        private Map<String, String> sourceByForeign = Maps.newHashMap();

        public ForeignKey(String targetUniqueConstraint, Fias targetTable, String sourceField, String foreignField) {
            this.targetUniqueConstraint = targetUniqueConstraint;
            this.targetTable = targetTable;
            sourceByForeign.put(foreignField, sourceField);
        }

        public ForeignKey(Fias targetTable, String sourceField) {
            this(null, targetTable, sourceField, targetTable.idField.getName());
        }

        public void addFields(String sourceField, String foreignField) {
            sourceByForeign.put(foreignField, sourceField);
        }

        public Map<String, String> getOrderTargetBySource() {
            if (Strings.isNullOrEmpty(targetUniqueConstraint)) {
                Preconditions.checkArgument(1 == sourceByForeign.size());
                return sourceByForeign;
            }
            Map<String, String> result = Maps.newLinkedHashMap();
            Class<?> targetClass = targetTable.item;
            Table table = targetClass.getAnnotation(Table.class);
            Preconditions.checkNotNull(table, "Annotation @Table is not present in class {0}", targetClass);
            String[] fieldOrder = null;
            for (UniqueConstraint uniqueConstraint : table.uniqueConstraints()) {
                if (targetUniqueConstraint.equals(uniqueConstraint.name())) {
                    fieldOrder = uniqueConstraint.columnNames();
                    break;
                }
            }
            Preconditions.checkNotNull(fieldOrder,
                    "UniqueConstraint with name {0} not found in @Table of class {1}", targetUniqueConstraint, targetClass);
            for (String foreignField : fieldOrder) {
                String sourceField = sourceByForeign.get(foreignField);
                Preconditions.checkNotNull(sourceField,
                        "Field {0} of UniqueConstraint is not found in class referenceing {1}", foreignField, targetClass);
                result.put(foreignField, sourceField);
            }
            return result;
        }
    }

    public void addToList(Field field) {
        FiasRef fiasRef = field.getAnnotation(FiasRef.class);
        if (null == fiasRef) {
            return;
        }

        Fias targetTable = Fias.FROM_ITEM_CLASS.get(fiasRef.value());
        Preconditions.checkNotNull(targetTable, "Unable to identify target class for reference field: {0}", field);
        String sourceField = field.getName();
        String targetFieldName = fiasRef.fieldName();
        if (Strings.isNullOrEmpty(targetFieldName)) {
            foreignKeyList.add(new ForeignKey(targetTable, sourceField));
        } else {
            String uniqueConstraint = fiasRef.constraintName();
            ForeignKey foreignKey = foreignKeyByUniqueContraint.get(uniqueConstraint);
            if (null == foreignKey) {
                foreignKey = new ForeignKey(uniqueConstraint, targetTable, sourceField, targetFieldName);
                foreignKeyList.add(foreignKey);
                foreignKeyByUniqueContraint.put(uniqueConstraint, foreignKey);
            } else {
                foreignKey.addFields(sourceField, targetFieldName);
            }
        }
    }

    public boolean isEmpty() {
        return foreignKeyList.isEmpty();
    }

    public int size() {
        return foreignKeyList.size();
    }

    @Override
    public Iterator<ForeignKey> iterator() {
        return foreignKeyList.iterator();
    }

}
