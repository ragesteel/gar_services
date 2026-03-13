// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.Room;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.1339408+03:00"
)
public class RoomTM extends AbstractTableMapping<Room, Long> {
    public RoomTM() {
        super(Room::id);
    }

    @Override
    public void write(Room source, PreparedStatement ps) throws SQLException {
        ps.setLong(1, source.id());
        ps.setLong(2, source.objectId());
        ps.setObject(3, source.objectGuid());
        ps.setLong(4, source.changeId());
        ps.setString(5, source.number());
        ps.setInt(6, source.roomType());
        ps.setInt(7, source.operTypeId());
        ps.setObject(8, source.prevId());
        ps.setObject(9, source.nextId());
        ps.setObject(10, source.updateDate());
        ps.setObject(11, source.startDate());
        ps.setObject(12, source.endDate());
        ps.setBoolean(13, source.isActual());
        ps.setBoolean(14, source.isActive());
    }

    @Override
    public Room read(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        long objectId = rs.getLong(2);
        UUID objectGuid = rs.getObject(3, UUID.class);
        long changeId = rs.getLong(4);
        String number = rs.getString(5);
        int roomType = rs.getInt(6);
        int operTypeId = rs.getInt(7);
        Long prevId = rs.getObject(8, Long.class);
        Long nextId = rs.getObject(9, Long.class);
        LocalDate updateDate = rs.getObject(10, LocalDate.class);
        LocalDate startDate = rs.getObject(11, LocalDate.class);
        LocalDate endDate = rs.getObject(12, LocalDate.class);
        boolean isActual = rs.getBoolean(13);
        boolean isActive = rs.getBoolean(14);
        return new Room(id, objectId, objectGuid, changeId, number, roomType, operTypeId, prevId, nextId, updateDate, startDate, endDate, isActual, isActive);
    }
}
