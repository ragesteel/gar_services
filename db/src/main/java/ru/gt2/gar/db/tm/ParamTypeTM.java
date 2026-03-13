// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.ParamType;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.119129+03:00"
)
public class ParamTypeTM extends AbstractTableMapping<ParamType, Integer> {
    public ParamTypeTM() {
        super(ParamType::id);
    }

    @Override
    public void write(ParamType source, PreparedStatement ps) throws SQLException {
        ps.setInt(1, source.id());
        ps.setString(2, source.name());
        ps.setString(3, source.code());
        ps.setString(4, source.desc());
        ps.setObject(5, source.updateDate());
        ps.setObject(6, source.startDate());
        ps.setObject(7, source.endDate());
        ps.setBoolean(8, source.isActive());
    }

    @Override
    public ParamType read(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        String code = rs.getString(3);
        String desc = rs.getString(4);
        LocalDate updateDate = rs.getObject(5, LocalDate.class);
        LocalDate startDate = rs.getObject(6, LocalDate.class);
        LocalDate endDate = rs.getObject(7, LocalDate.class);
        boolean isActive = rs.getBoolean(8);
        return new ParamType(id, name, code, desc, updateDate, startDate, endDate, isActive);
    }
}
