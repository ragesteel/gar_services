package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.AddressObjectType;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/// Пример того, как я хочу видеть реализацию класса
/// Везде используем порядковые номера
/// Да, тут из-за использования LocalDate нужен JDBC 4.2+
public class AddressObjectTypeTM extends AbstractTableMapping<AddressObjectType, Integer> {
    public AddressObjectTypeTM() {
        super("INT", """
            SELECT "id", "level", "short_name", "name", "desc", "update_date", "start_date", "end_date", "is_active" FROM addr_obj_types WHERE "id" = ANY(?)""", """
            INSERT INTO addr_obj_types ("id", "level", "short_name", "name", "desc", "update_date", "start_date", "end_date", "is_active") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)""",
            AddressObjectType::id);
    }

    @Override
    public void write(AddressObjectType source, PreparedStatement ps) throws SQLException {
        ps.setInt(1, source.id());
        ps.setInt(2, source.level());
        ps.setString(3, source.shortName());
        ps.setString(4, source.name());
        ps.setString(5, source.desc());
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
}
