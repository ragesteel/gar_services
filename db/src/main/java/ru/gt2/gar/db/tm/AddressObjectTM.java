// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.AddressObject;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.02394+03:00"
)
public class AddressObjectTM extends AbstractTableMapping<AddressObject, Long> {
    public AddressObjectTM() {
        super(AddressObject::id);
    }

    @Override
    public void write(AddressObject source, PreparedStatement ps) throws SQLException {
        ps.setLong(1, source.id());
        ps.setLong(2, source.objectId());
        ps.setObject(3, source.objectGuid());
        ps.setLong(4, source.changeId());
        ps.setString(5, source.name());
        ps.setString(6, source.typeName());
        ps.setString(7, source.level());
        ps.setInt(8, source.operTypeId());
        ps.setObject(9, source.prevId());
        ps.setObject(10, source.nextId());
        ps.setObject(11, source.updateDate());
        ps.setObject(12, source.startDate());
        ps.setObject(13, source.endDate());
        ps.setBoolean(14, source.isActual());
        ps.setBoolean(15, source.isActive());
    }

    @Override
    public AddressObject read(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        long objectId = rs.getLong(2);
        UUID objectGuid = rs.getObject(3, UUID.class);
        long changeId = rs.getLong(4);
        String name = rs.getString(5);
        String typeName = rs.getString(6);
        String level = rs.getString(7);
        int operTypeId = rs.getInt(8);
        Long prevId = rs.getObject(9, Long.class);
        Long nextId = rs.getObject(10, Long.class);
        LocalDate updateDate = rs.getObject(11, LocalDate.class);
        LocalDate startDate = rs.getObject(12, LocalDate.class);
        LocalDate endDate = rs.getObject(13, LocalDate.class);
        boolean isActual = rs.getBoolean(14);
        boolean isActive = rs.getBoolean(15);
        return new AddressObject(id, objectId, objectGuid, changeId, name, typeName, level, operTypeId, prevId, nextId, updateDate, startDate, endDate, isActual, isActive);
    }
}
