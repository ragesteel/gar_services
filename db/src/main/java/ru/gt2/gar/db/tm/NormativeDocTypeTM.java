// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.NormativeDocType;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.1069392+03:00"
)
public class NormativeDocTypeTM extends AbstractTableMapping<NormativeDocType, Integer> {
    public NormativeDocTypeTM() {
        super(NormativeDocType::id);
    }

    @Override
    public void write(NormativeDocType source, PreparedStatement ps) throws SQLException {
        ps.setInt(1, source.id());
        ps.setString(2, source.name());
        ps.setObject(3, source.startDate());
        ps.setObject(4, source.endDate());
    }

    @Override
    public NormativeDocType read(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        LocalDate startDate = rs.getObject(3, LocalDate.class);
        LocalDate endDate = rs.getObject(4, LocalDate.class);
        return new NormativeDocType(id, name, startDate, endDate);
    }
}
