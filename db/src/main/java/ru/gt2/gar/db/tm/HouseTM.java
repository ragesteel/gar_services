// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import ru.gt2.gar.domain.House;

import javax.annotation.processing.Generated;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.0919428+03:00"
)
public class HouseTM extends AbstractTableMapping<House, Long> {
    public HouseTM() {
        super(House::id);
    }

    @Override
    public void write(House source, PreparedStatement ps) throws SQLException {
        ps.setLong(1, source.id());
        ps.setLong(2, source.objectId());
        ps.setObject(3, source.objectGuid());
        ps.setLong(4, source.changeId());
        ps.setString(5, source.houseNum());
        ps.setString(6, source.addNum1());
        ps.setString(7, source.addNum2());
        ps.setObject(8, source.houseType());
        ps.setObject(9, source.addType1());
        ps.setObject(10, source.addType2());
        ps.setInt(11, source.operTypeId());
        ps.setObject(12, source.prevId());
        ps.setObject(13, source.nextId());
        ps.setObject(14, source.updateDate());
        ps.setObject(15, source.startDate());
        ps.setObject(16, source.endDate());
        ps.setBoolean(17, source.isActual());
        ps.setBoolean(18, source.isActive());
    }

    @Override
    public House read(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        long objectId = rs.getLong(2);
        UUID objectGuid = rs.getObject(3, UUID.class);
        long changeId = rs.getLong(4);
        String houseNum = rs.getString(5);
        String addNum1 = rs.getString(6);
        String addNum2 = rs.getString(7);
        Integer houseType = rs.getObject(8, Integer.class);
        Integer addType1 = rs.getObject(9, Integer.class);
        Integer addType2 = rs.getObject(10, Integer.class);
        int operTypeId = rs.getInt(11);
        Long prevId = rs.getObject(12, Long.class);
        Long nextId = rs.getObject(13, Long.class);
        LocalDate updateDate = rs.getObject(14, LocalDate.class);
        LocalDate startDate = rs.getObject(15, LocalDate.class);
        LocalDate endDate = rs.getObject(16, LocalDate.class);
        boolean isActual = rs.getBoolean(17);
        boolean isActive = rs.getBoolean(18);
        return new House(id, objectId, objectGuid, changeId, houseNum, addNum1, addNum2, houseType, addType1, addType2, operTypeId, prevId, nextId, updateDate, startDate, endDate, isActual, isActive);
    }
}
