package ru.gt2.rusref.openjpa;

import java.util.UUID;

import org.apache.openjpa.jdbc.identifier.DBIdentifier;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.JavaSQLTypes;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.strats.AbstractValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.sql.DBDictionary;

public class UUIDValueHandler extends AbstractValueHandler {
    @Override
    public boolean isVersionable(ValueMapping vm) {
        return true;
    }

    @Override
    public Column[] map(ValueMapping vm, String name, ColumnIO io,
                        boolean adapt) {
        DBDictionary dict = vm.getMappingRepository().getDBDictionary();
        DBIdentifier colName = DBIdentifier.newColumn(name, dict != null ? dict.delimitAll() : false);
        return map(vm, colName, io, adapt);
    }

    public Column[] map(ValueMapping vm, DBIdentifier name, ColumnIO io,
                        boolean adapt) {
        Column col = new Column();
        col.setIdentifier(name);
        col.setJavaType(JavaSQLTypes.BYTES);
        col.setSize(16);
        return new Column[]{ col };
    }

    @Override
    public Object toDataStoreValue(ValueMapping vm, Object val,
                                   JDBCStore store) {
        if (val == null) {
            return null;
        }
        UUID uuid = (UUID) val;
        return asByteArray(uuid);
    }

    @Override
    public Object toObjectValue(ValueMapping vm, Object val) {
        if (val == null) {
            return null;
        }
        byte[] uuidBytes = (byte[]) val;
        // FIXME Вообще-то тут нужен другой!ы
        return UUID.nameUUIDFromBytes(uuidBytes);
    }

    // http://stackoverflow.com/questions/772802/storing-uuid-as-base64-string
    // http://www.thebinaryidiot.com/archives/2011/06/25/jpa-and-uuid-primary-keys/
    public static byte[] asByteArray(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) (msb >>> 8 * (7 - i));
        }
        for (int i = 8; i < 16; i++) {
            buffer[i] = (byte) (lsb >>> 8 * (7 - i));
        }

        return buffer;
    }

}