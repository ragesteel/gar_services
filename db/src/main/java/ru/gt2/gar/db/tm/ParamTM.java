// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.Param;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.1419412+03:00"
)
public class ParamTM extends AbstractTableMapping<Param, Long> {
    public ParamTM() {
        super(Param::id);
    }

    @Override
    public void write(Param source, PreparedStatement ps) throws SQLException {
        ps.setLong(1, source.id());
        ps.setLong(2, source.objectId());
        ps.setObject(3, source.changeId());
        ps.setLong(4, source.changeIdEnd());
        ps.setInt(5, source.typeId());
        ps.setString(6, source.value());
        ps.setObject(7, source.updateDate());
        ps.setObject(8, source.startDate());
        ps.setObject(9, source.endDate());
    }

    @Override
    public Param read(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        long objectId = rs.getLong(2);
        Long changeId = rs.getObject(3, Long.class);
        long changeIdEnd = rs.getLong(4);
        int typeId = rs.getInt(5);
        String value = rs.getString(6);
        LocalDate updateDate = rs.getObject(7, LocalDate.class);
        LocalDate startDate = rs.getObject(8, LocalDate.class);
        LocalDate endDate = rs.getObject(9, LocalDate.class);
        return new Param(id, objectId, changeId, changeIdEnd, typeId, value, updateDate, startDate, endDate);
    }
}
