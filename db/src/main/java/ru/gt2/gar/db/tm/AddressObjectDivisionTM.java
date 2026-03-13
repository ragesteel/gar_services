// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.AddressObjectDivision;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.0439446+03:00"
)
public class AddressObjectDivisionTM extends AbstractTableMapping<AddressObjectDivision, Long> {
    public AddressObjectDivisionTM() {
        super(AddressObjectDivision::id);
    }

    @Override
    public void write(AddressObjectDivision source, PreparedStatement ps) throws SQLException {
        ps.setLong(1, source.id());
        ps.setLong(2, source.parentId());
        ps.setLong(3, source.childId());
        ps.setLong(4, source.changeId());
    }

    @Override
    public AddressObjectDivision read(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        long parentId = rs.getLong(2);
        long childId = rs.getLong(3);
        long changeId = rs.getLong(4);
        return new AddressObjectDivision(id, parentId, childId, changeId);
    }
}
