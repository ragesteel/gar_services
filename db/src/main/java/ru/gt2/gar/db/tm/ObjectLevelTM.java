// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.ObjectLevel;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.1099389+03:00"
)
public class ObjectLevelTM extends AbstractTableMapping<ObjectLevel, Integer> {
    public ObjectLevelTM() {
        super(ObjectLevel::level);
    }

    @Override
    public void write(ObjectLevel source, PreparedStatement ps) throws SQLException {
        ps.setInt(1, source.level());
        ps.setString(2, source.name());
        ps.setString(3, source.shortName());
        ps.setObject(4, source.updateDate());
        ps.setObject(5, source.startDate());
        ps.setObject(6, source.endDate());
        ps.setBoolean(7, source.isActive());
    }

    @Override
    public ObjectLevel read(ResultSet rs) throws SQLException {
        int level = rs.getInt(1);
        String name = rs.getString(2);
        String shortName = rs.getString(3);
        LocalDate updateDate = rs.getObject(4, LocalDate.class);
        LocalDate startDate = rs.getObject(5, LocalDate.class);
        LocalDate endDate = rs.getObject(6, LocalDate.class);
        boolean isActive = rs.getBoolean(7);
        return new ObjectLevel(level, name, shortName, updateDate, startDate, endDate, isActive);
    }
}
