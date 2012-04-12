package ru.gt2.rusref.ddl;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;
import ru.gt2.rusref.Description;
import ru.gt2.rusref.FieldType;
import ru.gt2.rusref.Joiners;
import ru.gt2.rusref.fias.Fias;
import ru.gt2.rusref.fias.FiasRef;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
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
    private final ImmutableList<UniqueConstraint> uniqueKeys;
    private final List<String> lines = Lists.newArrayList();
    private final List<String> primaryKeysFields = Lists.newArrayList();
    private final ForeignKeyList foreignKeyList = new ForeignKeyList();
    private final String tableName;
    private final String quotedTableName;

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
        Table table = fias.item.getAnnotation(Table.class);
        if (null == table) {
            uniqueKeys = ImmutableList.of();
        } else {
            uniqueKeys = ImmutableList.copyOf(table.uniqueConstraints());
        }
        tableName = getTableName(fias);
        quotedTableName = getQuotedTableName(fias);
    }

    public void createTable() {
        beginCreateTable();
        for (Field field : fias.itemFields) {
            setField(field);
            createField();
        }

        Preconditions.checkArgument(!primaryKeysFields.isEmpty(), "Primary key field(s) not found");

        appendComma = !uniqueKeys.isEmpty() || !foreignKeyList.isEmpty();
        createPrimaryKey();

        appendComma = !foreignKeyList.isEmpty();
        createUniqueKeys();

        createForeignKeys();

        endCreateTable();

        printLines();
    }

    public void loadData() {
        loadDataCommand();

        loadDataFields();

        commit();

        printLines();
    }

    public void insertSelect() {
        lines.add("INSERT INTO " + quotedTableName);
        FiasRef fiasRef = fias.item.getAnnotation(FiasRef.class);
        Preconditions.checkNotNull(fiasRef, "@FiasRef not found on class: {0}", fias.item);
        Fias target = getTargetFias(fiasRef);
        Preconditions.checkNotNull(target, "Unable to identify target class for reference: {0}", fias);

        String targetFieldName = fiasRef.fieldName();
        if (Strings.isNullOrEmpty(targetFieldName)) {
            targetFieldName = fias.idField.getName();
        }
        lines.add("  SELECT DISTINCT " + QUOTE_IDENTIFIER.apply(targetFieldName)
                + " FROM " + getQuotedTableName(target) + ";");

        commit();

        printLines();
    }

    protected void beginCreateTable() {
        lines.add("CREATE TABLE " + quotedTableName + " (");
    }

    protected void endCreateTable() {
        StringBuilder line = new StringBuilder(") ENGINE = InnoDB,");
        boolean hasComment = appendComment(line, fias.item);
        if (!hasComment) {
            deleteLastChar(line);
        }
        line.append(";");
        lines.add(line.toString());
    }

    protected void createField() {
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

        foreignKeyList.addToList(field);
    }

    protected void createPrimaryKey() {
        StringBuilder line = new StringBuilder("  PRIMARY KEY (");
        line.append(quoteAndJoinIdentifiers(primaryKeysFields));
        line.append(')');
        if (appendComma) {
            line.append(',');
        }
        lines.add(line.toString());
    }

    private void createUniqueKeys() {
        int commasToAppend = uniqueKeys.size() - 1;
        if (appendComma) {
            commasToAppend++;
        }
        for (UniqueConstraint uniqueKey : uniqueKeys) {
            // FIXME Имена для индексов
            StringBuilder line = new StringBuilder("  UNIQUE KEY (");
            String joinedForeignKeyFields = quoteAndJoinIdentifiers(
                    Arrays.asList(uniqueKey.columnNames()));
            line.append(joinedForeignKeyFields);
            line.append(")");
            if (commasToAppend > 0) {
                line.append(',');
            }
            lines.add(line.toString());
            commasToAppend--;
        }
    }

    protected void createForeignKeys() {
        int commasToAppend = foreignKeyList.size() - 1;
        for (ForeignKeyList.ForeignKey foreignKey : foreignKeyList) {
            // FIXME Имена для индексов
            StringBuilder line = new StringBuilder("  FOREIGN KEY (");

            Map<String, String> orderTargetBySource = foreignKey.getOrderTargetBySource();
            Iterable<String> sourceFields = orderTargetBySource.values();
            Iterable<String> targetFields = orderTargetBySource.keySet();

            line.append(quoteAndJoinIdentifiers(sourceFields));
            line.append(") REFERENCES ");
            Fias reference = foreignKey.getTargetTable();
            line.append(getQuotedTableName(reference));
            line.append(" (");
            line.append(quoteAndJoinIdentifiers(targetFields));
            line.append(")");
            if (commasToAppend > 0) {
                line.append(',');
            }
            lines.add(line.toString());
            commasToAppend--;
        }
    }

    protected void loadDataCommand() {
        lines.add("LOAD DATA LOCAL INFILE '" + tableName + ".csv'");
        lines.add("  INTO TABLE " + quotedTableName);
        lines.add("  FIELDS");
        lines.add("    TERMINATED BY '\\t'");
        lines.add("    OPTIONALLY ENCLOSED BY '\"'");
        lines.add("    ESCAPED BY '\\\\'");
    }

    protected void loadDataFields() {
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

    protected String getType() {
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

    protected boolean appendComment(StringBuilder line, AnnotatedElement element) {
        String comment = getDescription(element);
        if (Strings.isNullOrEmpty(comment)) {
            return false;
        }
        line.append(" COMMENT ");
        line.append(quoteLiteral(comment));
        return true;
    }

    protected void commit() {
        lines.add("COMMIT;");
    }

    protected void setField(Field field) {
        this.field = field;
        fieldName = field.getName();
        fieldType = FieldType.FROM_TYPE.get(field.getType());
        Preconditions.checkNotNull(fieldType, "FieldType not found for field: {0}", field);
        column = field.getAnnotation(Column.class);
        Preconditions.checkNotNull(column, "Column annotation is not set");
    }

    protected static Fias getTargetFias(FiasRef fiasRef) {
        Fias target = Fias.FROM_ITEM_CLASS.get(fiasRef.value());
        return target;
    }

    protected static String getTableName(Fias fias) {
        return fias.item.getSimpleName();
    }

    protected static String getQuotedTableName(Fias fias) {
        return QUOTE_IDENTIFIER.apply(getTableName(fias));
    }

    protected static String getDescription(AnnotatedElement element) {
        Description description = element.getAnnotation(Description.class);
        return (null == description) ? null : description.value();
    }

    protected String quoteLiteral(String literal) {
        // Да, простая и наиваная релизация.
        return '"' + literal + '"';
    }

    protected String quoteAndJoinIdentifiers(Iterable<String> identifiers) {
        return Joiners.COMMA_SEPARATED.join(
                Iterables.transform(identifiers, QUOTE_IDENTIFIER));
    }

    protected void printLines() {
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println();
    }

    protected static void deleteLastChar(StringBuilder line) {
        line.deleteCharAt(line.length() - 1);
    }

    protected static void deleteTwoLastChars(StringBuilder line) {
        int length = line.length();
        line.delete(length - 2, length);
    }

}
