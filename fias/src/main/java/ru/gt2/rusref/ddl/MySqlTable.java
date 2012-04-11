package ru.gt2.rusref.ddl;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
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
 * FIXME Добавить метод для записи LOAD DATA команды с учётом того, что для GUID нужно делать UNHEX.
 *
 * Конечно, если это будет развиваться, нужно будет извлечь базовый класс и всякое такое.
 */
public class MySqlTable {
    private final Fias fias;
    private final List<String> lines = Lists.newArrayList();
    private final List<String> primaryKeysFields = Lists.newArrayList();
    private final Map<List<String>, Fias> foreignKeyReferences = Maps.newLinkedHashMap();
    private final String tableName;

    private Field field;
    private String fieldName;
    private FieldType fieldType;
    private Column column;
    private boolean appendComma;

    public static final Function<String, String> QUOTE_IDENTIFIER = new Function<String, String>() {
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
        tableName = getTableName(fias);
    }

    public void createTable() {
        beginCreateTable();
        for (Field field : fias.itemFields) {
            setField(field);
            createField();
        }

        Preconditions.checkArgument(!primaryKeysFields.isEmpty(), "Primary key field(s) not found");

        appendComma = !foreignKeyReferences.isEmpty();
        createPrimaryKey();

        createForeignKeys();

        endCreateTable();

        printLines();
    }

    public void loadData() {
        loadDataCommand();

        loadDataFields();

        lines.add("COMMIT;");

        printLines();
    }

    public void beginCreateTable() {
        lines.add("CREATE TABLE " + QUOTE_IDENTIFIER.apply(tableName) + " (");
    }

    public void endCreateTable() {
        StringBuilder line = new StringBuilder(") ENGINE = InnoDB,");
        boolean hasComment = appendComment(line, fias.item);
        if (!hasComment) {
            deleteLastChar(line);
        }
        line.append(";");
        lines.add(line.toString());
    }

    public void createField() {
        StringBuilder line = new StringBuilder("  ");
        line.append(QUOTE_IDENTIFIER.apply(fieldName));
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
            Preconditions.checkNotNull(target, "Unable to identify target class for reference field: {0}", field);
            foreignKeyReferences.put(
                    Collections.singletonList(fieldName),
                    target);
        }
    }

    public void createPrimaryKey() {
        StringBuilder line = new StringBuilder("  PRIMARY KEY (");
        line.append(quoteAndJoinIdentifiers(primaryKeysFields));
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
            String joinedForeignKeyFields = quoteAndJoinIdentifiers(foreignKeyReference.getKey());
            line.append(joinedForeignKeyFields);
            line.append(") REFERENCES ");
            Fias reference = foreignKeyReference.getValue();
            line.append(QUOTE_IDENTIFIER.apply(getTableName(reference)));
            line.append(" (");
            line.append(QUOTE_IDENTIFIER.apply(reference.idField.getName()));
            line.append(")");
            if (commasToAppend > 0) {
                line.append(',');
            }
            lines.add(line.toString());
            commasToAppend--;
        }
    }

    private void loadDataCommand() {
        lines.add("LOAD DATA LOCAL INFILE '" + tableName + ".csv'");
        lines.add("  INTO TABLE " + QUOTE_IDENTIFIER.apply(tableName));
        lines.add("  FIELDS");
        lines.add("    TERMINATED BY '\\t'");
        lines.add("    OPTIONALLY ENCLOSED BY '\"'");
        lines.add("    ESCAPED BY '\\\\'");
    }

    private void loadDataFields() {
        boolean hasUnhex = false;
        StringBuilder fieldLine = new StringBuilder("  (");
        StringBuilder unhexLine = new StringBuilder("    ");
        for (Field field : fias.itemFields) {
            setField(field);
            String quotedFieldName = QUOTE_IDENTIFIER.apply(fieldName);
            if (FieldType.GUID.equals(fieldType)) {
                hasUnhex = true;
                fieldLine.append("@").append(fieldName);
                unhexLine.append(quotedFieldName).append(" = UNHEX(@").append(fieldName).append("), ");
            } else {
                fieldLine.append(quotedFieldName);
            }
            fieldLine.append(", ");
        }
        deleteTwoLastChars(fieldLine);
        fieldLine.append(")");
        lines.add(fieldLine.toString());

        if (hasUnhex) {
            lines.add("  SET");
            deleteTwoLastChars(unhexLine);
            lines.add(unhexLine.toString());
        }

        lines.add(";");
    }

    private String getType() {
        switch (fieldType) {
            case INTEGER:
                int scale = column.scale();
                Preconditions.checkArgument(10 == scale, "Unhandled scale: {0}", scale);
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
                Preconditions.checkArgument(false, "Unhandled FieldType: {0}", fieldType);
                return null;
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

    private void setField(Field field) {
        this.field = field;
        fieldName = field.getName();
        fieldType = FieldType.FROM_TYPE.get(field.getType());
        Preconditions.checkNotNull(fieldType, "FieldType not found for field: {0}", field);
        column = field.getAnnotation(Column.class);
        Preconditions.checkNotNull(column, "Column annotation is not set");
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

    protected String quoteAndJoinIdentifiers(List<String> identifiers) {
        return Joiners.COMMA_SEPARATED.join(
                Lists.transform(identifiers, QUOTE_IDENTIFIER));
    }

    private void printLines() {
        for (String line : lines) {
            System.out.println(line);
        }
    }

    private static void deleteLastChar(StringBuilder line) {
        line.deleteCharAt(line.length() - 1);
    }

    private static void deleteTwoLastChars(StringBuilder line) {
        int length = line.length();
        line.delete(length - 2, length);
    }

}
