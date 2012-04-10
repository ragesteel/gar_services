package ru.gt2.rusref.ddl;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ru.gt2.rusref.Description;
import ru.gt2.rusref.FieldType;
import ru.gt2.rusref.Joiners;
import ru.gt2.rusref.fias.Fias;
import ru.gt2.rusref.fias.FiasRef;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Создание MySQL-таблицы.
 *
 * Конечно, если это будет развиваться, нужно будет извлечь базовый класс и всякое такое.
 */
public class MySqlTable {
    private final Fias fias;
    private final List<String> lines = Lists.newArrayList();
    private final List<String> primaryKeysFields = Lists.newArrayList();
    private final Map<List<String>, Fias> foreignKeyReferences = Maps.newLinkedHashMap();

    private Field field;
    private FieldType fieldType;
    private Column column;
    private boolean appendComma;

    public static final Function<String, String> QUOTE_INDENTIFIER = new Function<String, String>() {
        @Override
        public String apply(@Nullable String identifier) {
            if (null == identifier) {
                return null;
            }
            return '`' + identifier + '`';
        }
    };

    public MySqlTable(Fias fias) {
        this.fias = fias;
    }

    public void generate() {
        startTable();
        for (Field field : fias.itemFields) {
            createField(field);
        }
        if (primaryKeysFields.isEmpty()) {
            throw new RuntimeException("Primary key field(s) not found");
        }

        appendComma = !foreignKeyReferences.isEmpty();
        createPrimaryKey();

        createForeignKeys();

        endTable();

        for (String line : lines) {
            System.out.println(line);
        }
    }

    public void startTable() {
        lines.add("CREATE TABLE " + QUOTE_INDENTIFIER.apply(getTableName()) + " (");
    }

    public void endTable() {
        StringBuilder line = new StringBuilder(") ENGINE = InnoDB,");
        boolean hasComment = appendComment(line, fias.item);
        if (!hasComment) {
            line.deleteCharAt(line.length() - 1);
        }
        line.append(";");
        lines.add(line.toString());
    }

    public void createField(Field field) {
        this.field = field;
        fieldType = FieldType.FROM_TYPE.get(field.getType());
        column = field.getAnnotation(Column.class);

        StringBuilder line = new StringBuilder("  ");
        String fieldName = field.getName();
        line.append(QUOTE_INDENTIFIER.apply(fieldName));
        line.append(' ');
        line.append(getType());
        if (!column.nullable()) {
            line.append(" NOT NULL");
        }

        appendComment(line, field);
        line.append(",");

        lines.add(line.toString());

        Id id = field.getAnnotation(Id.class);
        if (null != id) {
            primaryKeysFields.add(fieldName);
        }

        FiasRef fiasRef = field.getAnnotation(FiasRef.class);
        if (null != fiasRef) {
            Fias target = Fias.FROM_ITEM_CLASS.get(fiasRef.value());
            if (null == target) {
                throw new RuntimeException("Unable to identify target class for reference field: " + field);
            }
            foreignKeyReferences.put(
                    Collections.singletonList(fieldName),
                    target);
        }
    }

    public void createPrimaryKey() {
        StringBuilder line = new StringBuilder("  PRIMARY KEY (");
        line.append(quouteAndJoinIdentifiers(primaryKeysFields));
        line.append(')');
        if (appendComma) {
            line.append(',');
        }
        lines.add(line.toString());
    }

    public void createForeignKeys() {
        int commasToAppend = foreignKeyReferences.size() - 1;
        for (Map.Entry<List<String>, Fias> foreignKeyReference : foreignKeyReferences.entrySet()) {
            // FIXME Имена для индексов
            StringBuilder line = new StringBuilder("  FOREIGN KEY (");
            String joinedForeignKeyFields = quouteAndJoinIdentifiers(foreignKeyReference.getKey());
            line.append(joinedForeignKeyFields);
            line.append(") REFERENCES ");
            Fias reference = foreignKeyReference.getValue();
            line.append(QUOTE_INDENTIFIER.apply(getTableName(reference)));
            line.append(" (");
            line.append(QUOTE_INDENTIFIER.apply(reference.idField.getName()));
            line.append(")");
            if (commasToAppend > 0) {
                line.append(',');
            }
            lines.add(line.toString());
            commasToAppend--;
        }
    }

    private String getType() {
        if (null == fieldType) {
            throw new RuntimeException("FieldType not found for field: " + field);
        }
        switch (fieldType) {
            case INTEGER:
                int scale = column.scale();
                if (10 != scale) {
                    throw new RuntimeException("Unhandled scale: " + scale);
                }
                return "INT(" + scale + ")";
            case DATE:
                return "DATETIME";
            case STRING:
                Size size = field.getAnnotation(Size.class);
                if (Integer.MAX_VALUE == size.max()) {
                    return "TEXT";
                }
                return "VARCHAR(" + column.length() + ")";
            case GUID:
                return "BINARY(16)";
            default:
                throw new RuntimeException("Unhandled FieldType: " + fieldType);
        }
    }

    private boolean appendComment(StringBuilder line, AnnotatedElement element) {
        String comment = getDescription(element);
        if (Strings.isNullOrEmpty(comment)) {
            return false;
        }
        line.append(" COMMENT ");
        line.append(quoteLiteral(comment));
        return true;
    }

    private String getTableName() {
        return getTableName(fias);
    }

    private static String getTableName(Fias fias) {
        return fias.item.getSimpleName();
    }

    private static String getDescription(AnnotatedElement element) {
        Description description = element.getAnnotation(Description.class);
        return (null == description) ? null : description.value();
    }

    protected String quoteLiteral(String literal) {
        // Да, простая и наиваная релизация.
        return '"' + literal + '"';
    }

    protected String quouteAndJoinIdentifiers(List<String> identifiers) {
        return Joiners.COMMA_SEPARATED.join(
                Lists.transform(identifiers, QUOTE_INDENTIFIER));
    }
}
