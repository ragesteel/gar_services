package ru.gt2.gar.db.jrm;

import ru.gt2.gar.domain.AddressObjectType;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;

/// Пример того, как я хочу видеть реализацию класса
/// Везде используем порядковые номера
/// Да, тут из-за использования LocalDate нужен JDBC 4.2+
public class AddressObjectTypeJm implements JdbcMapper<AddressObjectType> {
    private static final PrimaryKeyMeta<AddressObjectType> PRIMARY_KEY_META =
            new PrimaryKeyMeta<>("id", AddressObjectType::id);

    @Override
    public void write(AddressObjectType source, PreparedStatement ps) throws SQLException {
        ps.setInt(1, source.id());
        ps.setInt(2, source.level());
        ps.setString(3, source.shortName());
        ps.setString(4, source.name());
        String desc = source.desc();
        if (null == desc) {
            ps.setNull(5, Types.VARCHAR);
        } else {
            ps.setString(5, desc);
        }
        ps.setObject(6, source.updateDate(), JDBCType.DATE);
        ps.setObject(7, source.startDate(), JDBCType.DATE);
        ps.setObject(8, source.endDate(), JDBCType.DATE);
        ps.setBoolean(9, source.isActive());
    }

    @Override
    public AddressObjectType read(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        int level = rs.getInt(2);
        String shortName = rs.getString(3);
        String name = rs.getString(4);
        String desc = rs.getString(5);
        LocalDate updateDate = rs.getObject(6, LocalDate.class);
        LocalDate startDate = rs.getObject(7, LocalDate.class);
        LocalDate endDate = rs.getObject(8, LocalDate.class);
        boolean isActive = rs.getBoolean(9);
        return new AddressObjectType(id, level, shortName, name, desc, updateDate, startDate, endDate, isActive);
    }

    @Override
    public int columnCount() {
        return 9;
    }

    @Override
    public PrimaryKeyMeta<AddressObjectType> primaryKey() {
        return PRIMARY_KEY_META;
    }
}
