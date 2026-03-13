// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.OperationType;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.1139412+03:00"
)
public class OperationTypeTM extends AbstractTableMapping<OperationType, Integer> {
    public OperationTypeTM() {
        super(OperationType::id);
    }

    @Override
    public void write(OperationType source, PreparedStatement ps) throws SQLException {
        ps.setInt(1, source.id());
        ps.setString(2, source.name());
        ps.setString(3, source.shortName());
        ps.setString(4, source.desc());
        ps.setObject(5, source.updateDate());
        ps.setObject(6, source.startDate());
        ps.setObject(7, source.endDate());
        ps.setBoolean(8, source.isActive());
    }

    @Override
    public OperationType read(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        String shortName = rs.getString(3);
        String desc = rs.getString(4);
        LocalDate updateDate = rs.getObject(5, LocalDate.class);
        LocalDate startDate = rs.getObject(6, LocalDate.class);
        LocalDate endDate = rs.getObject(7, LocalDate.class);
        boolean isActive = rs.getBoolean(8);
        return new OperationType(id, name, shortName, desc, updateDate, startDate, endDate, isActive);
    }
}
