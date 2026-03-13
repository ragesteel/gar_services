// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.Stead;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.1379413+03:00"
)
public class SteadTM extends AbstractTableMapping<Stead, Long> {
    public SteadTM() {
        super(Stead::id);
    }

    @Override
    public void write(Stead source, PreparedStatement ps) throws SQLException {
        ps.setLong(1, source.id());
        ps.setLong(2, source.objectId());
        ps.setObject(3, source.objectGuid());
        ps.setLong(4, source.changeId());
        ps.setString(5, source.number());
        ps.setInt(6, source.operTypeId());
        ps.setObject(7, source.prevId());
        ps.setObject(8, source.nextId());
        ps.setObject(9, source.updateDate());
        ps.setObject(10, source.startDate());
        ps.setObject(11, source.endDate());
        ps.setBoolean(12, source.isActual());
        ps.setBoolean(13, source.isActive());
    }

    @Override
    public Stead read(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        long objectId = rs.getLong(2);
        UUID objectGuid = rs.getObject(3, UUID.class);
        long changeId = rs.getLong(4);
        String number = rs.getString(5);
        int operTypeId = rs.getInt(6);
        Long prevId = rs.getObject(7, Long.class);
        Long nextId = rs.getObject(8, Long.class);
        LocalDate updateDate = rs.getObject(9, LocalDate.class);
        LocalDate startDate = rs.getObject(10, LocalDate.class);
        LocalDate endDate = rs.getObject(11, LocalDate.class);
        boolean isActual = rs.getBoolean(12);
        boolean isActive = rs.getBoolean(13);
        return new Stead(id, objectId, objectGuid, changeId, number, operTypeId, prevId, nextId, updateDate, startDate, endDate, isActual, isActive);
    }
}
