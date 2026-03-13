// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.ReestrObject;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.1249406+03:00"
)
public class ReestrObjectTM extends AbstractTableMapping<ReestrObject, Long> {
    public ReestrObjectTM() {
        super(ReestrObject::objectId);
    }

    @Override
    public void write(ReestrObject source, PreparedStatement ps) throws SQLException {
        ps.setLong(1, source.objectId());
        ps.setObject(2, source.objectGuid());
        ps.setLong(3, source.changeId());
        ps.setBoolean(4, source.isActive());
        ps.setInt(5, source.levelId());
        ps.setObject(6, source.createDate());
        ps.setObject(7, source.updateDate());
    }

    @Override
    public ReestrObject read(ResultSet rs) throws SQLException {
        long objectId = rs.getLong(1);
        UUID objectGuid = rs.getObject(2, UUID.class);
        long changeId = rs.getLong(3);
        boolean isActive = rs.getBoolean(4);
        int levelId = rs.getInt(5);
        LocalDate createDate = rs.getObject(6, LocalDate.class);
        LocalDate updateDate = rs.getObject(7, LocalDate.class);
        return new ReestrObject(objectId, objectGuid, changeId, isActive, levelId, createDate, updateDate);
    }
}
